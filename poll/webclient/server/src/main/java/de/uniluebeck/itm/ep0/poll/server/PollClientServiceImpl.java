package de.uniluebeck.itm.ep0.poll.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.uniluebeck.itm.ep0.poll.api.PollClientService;
import de.uniluebeck.itm.ep0.poll.domain.XoPoll;
import de.uniluebeck.itm.ep0.poll.domain.XoPollInfo;

import de.uniluebeck.itm.ep0.poll.domain.XoPollWithVotes;

import de.uniluebeck.itm.ep0.poll.domain.XoVote;

import de.uniluebeck.itm.ep0.poll.exception.PollException;
import de.uniluebeck.itm.ep0.poll.service.PollService;
import de.uniluebeck.itm.ep0.poll.shared.LoadPollsResult;
import de.uniluebeck.itm.ep0.poll.shared.RemotePollException;
import de.uniluebeck.itm.ep0.poll.web.PollWebService;
import de.uniluebeck.itm.ep0.poll.web.PollWebserviceClient;
import de.uniluebeck.itm.ep0.poll.web.XoXsMapper;
import de.uniluebeck.itm.ep0.poll.web.XsPollInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PollClientServiceImpl extends RemoteServiceServlet implements
        PollClientService {

    private static final long serialVersionUID = 1L;

    private final static Logger LOG = LoggerFactory.getLogger(PollClientServiceImpl.class);

    private final static String POLL_SERVICE_NULL = "pollService is NULL!";
    private final static String REMOTE_CALL_FAILED = "Remote call failed: ";
    private final static String COMMUNICATION_ERROR_WITH_APP_CORE = "Could not communicate with application core! Maybe it's not running?";
    private final static String LANGUAGE_FETCH_ERROR = "Error fetching languages!";
    private static final String COMMUNICATION_ERROR_WITH_REMOTE_WS = "Error contacting remote web service: ";

    private final static QName qName = new QName(
            "www.itm.uniluebeck.de/pollservice", "Pollservice");

    /**
     * RMI poll service.
     */
    private PollService rmiPollService;

    public PollClientServiceImpl() throws RemotePollException {
        ApplicationContext ctx;
        try {
            ctx = new ClassPathXmlApplicationContext("gwt-server-context.xml");
            rmiPollService = (PollService) ctx.getBean("pollService");
        } catch (final BeanCreationException ex) {
            LOG.error(COMMUNICATION_ERROR_WITH_APP_CORE, ex);
            throw new RemotePollException(COMMUNICATION_ERROR_WITH_APP_CORE);
        }
    }

    @Override
    public List<XoPoll> getPolls() throws RemotePollException {
        LOG.info("getPolls() called");
        List<XoPoll> polls;
        if (rmiPollService != null) {
            try {
                polls = rmiPollService.getPolls();
            } catch (final PollException ex) {
                LOG.error(REMOTE_CALL_FAILED);
                throw new RemotePollException(REMOTE_CALL_FAILED
                        + ex.getMessage(), ex);
            }
        } else {
            LOG.error(POLL_SERVICE_NULL);
            throw new RemotePollException(POLL_SERVICE_NULL);
        }
        return polls;
    }

    @Override
    public XoPoll getPoll(final Integer id) throws RemotePollException {
        LOG.info("getPoll( " + id + " ) called");
        XoPoll poll;
        if (rmiPollService != null) {
            try {
                poll = rmiPollService.getPoll(id);
            } catch (final PollException ex) {
                LOG.error(REMOTE_CALL_FAILED);
                throw new RemotePollException(REMOTE_CALL_FAILED
                        + ex.getMessage(), ex);
            }
        } else {
            LOG.error(POLL_SERVICE_NULL);
            throw new RemotePollException(POLL_SERVICE_NULL);
        }
        return poll;
    }

    @Override
    public XoPoll getPoll(final String uuid, final String remoteURL,
                          final String remoteLanguageCode) throws RemotePollException {
        LOG.info("getPoll(uuid: " + uuid + " remoteURL: " + remoteURL
                + " remoteLanugageCode: " + remoteLanguageCode + " ) called");
        XoPoll poll;

        if ("".equals(remoteURL)) {

            if (rmiPollService != null) {
                try {
                    poll = rmiPollService.getPoll(uuid);
                } catch (final PollException ex) {
                    LOG.error(REMOTE_CALL_FAILED);
                    throw new RemotePollException(REMOTE_CALL_FAILED
                            + ex.getMessage(), ex);
                }
            } else {
                LOG.error(POLL_SERVICE_NULL);
                throw new RemotePollException(POLL_SERVICE_NULL);
            }
        } else {
            poll = getRemotePoll(uuid, remoteURL, remoteLanguageCode);
        }
        return poll;
    }

    @Override
    public XoPoll addPoll(final XoPoll poll) throws RemotePollException {
        LOG.info("addPoll( " + poll.getName() + " ) called");
        XoPoll persistedPoll;
        if (rmiPollService != null) {
            try {
                persistedPoll = rmiPollService.addPoll(poll);
            } catch (final PollException ex) {
                LOG.error(REMOTE_CALL_FAILED);
                throw new RemotePollException(REMOTE_CALL_FAILED
                        + ex.getMessage(), ex);
            }
        } else {
            LOG.error(POLL_SERVICE_NULL);
            throw new RemotePollException(POLL_SERVICE_NULL);
        }
        return persistedPoll;
    }

    @Override
    public LoadPollsResult getPollInfos(final String url,
                                        final String languageCode) throws RemotePollException {
        LOG.info("getPollInfos() called");
        LoadPollsResult result;
        if (null != url && !"".equals(url)) {
            result = fetchRemotePollInfos(url, languageCode);
            result.setIsRemote(Boolean.TRUE);

        } else {
            List<XoPollInfo> pollInfos;
            if (rmiPollService != null) {
                try {
                    pollInfos = rmiPollService.getPollInfos(Boolean.FALSE);
                } catch (final PollException ex) {
                    LOG.error(REMOTE_CALL_FAILED + ex.getLocalizedMessage());
                    throw new RemotePollException(REMOTE_CALL_FAILED
                            + ex.getMessage(), ex);
                }
            } else {
                LOG.error(POLL_SERVICE_NULL);
                throw new RemotePollException(POLL_SERVICE_NULL);
            }
            result = new LoadPollsResult();
            result.setIsRemote(Boolean.FALSE);
            result.setPollInfos(pollInfos);

        }
        return result;
    }

    @Override
    public List<XoVote> getVotes(Integer optionId) throws RemotePollException {
        LOG.info("getVotes( " + optionId + " ) called");
        List<XoVote> votes;
        if (rmiPollService != null) {
            try {
                votes = rmiPollService.getVotes(optionId);
            } catch (final PollException ex) {
                LOG.error(REMOTE_CALL_FAILED);
                throw new RemotePollException(REMOTE_CALL_FAILED
                        + ex.getMessage(), ex);
            }
        } else {
            LOG.error(POLL_SERVICE_NULL);
            throw new RemotePollException(POLL_SERVICE_NULL);
        }
        return votes;
    }

    @Override
    public Map<String, List<XoVote>> getVotesForOptionList(Integer optionListId)
            throws RemotePollException {
        LOG.info("getVotesForOptionList( " + optionListId + " ) called");
        Map<String, List<XoVote>> votes;
        if (rmiPollService != null) {
            try {
                votes = rmiPollService.getVotesForOptionList(optionListId);
            } catch (final PollException ex) {
                LOG.error(REMOTE_CALL_FAILED);
                throw new RemotePollException(REMOTE_CALL_FAILED
                        + ex.getMessage(), ex);
            }
        } else {
            LOG.error(POLL_SERVICE_NULL);
            throw new RemotePollException(POLL_SERVICE_NULL);
        }
        return votes;
    }

    @Override
    public Map<String, List<XoVote>> getVotesRemote(String url, String pollId)
            throws RemotePollException {
        final PollWebService ws = getPollWebService(url);
        // ws.getPoll(pollId, languageCode)
        // TODO: implement me
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private LoadPollsResult fetchRemotePollInfos(final String url,
                                                 final String languageCode) throws RemotePollException {

        final PollWebService ws = getPollWebService(url);

        Assert.notNull(languageCode, "missing required  pollLanguage");

        final List<XsPollInfo> pollInfos = ws.getPolls(languageCode);
        final LoadPollsResult result = new LoadPollsResult();

        final List<XoPollInfo> xoPollInfos = XoXsMapper
                .xsPollInfos2xoPollInfos(pollInfos, languageCode);
        result.setPollInfos(xoPollInfos);
        result.setIsRemote(Boolean.TRUE);
        result.setRemoteLanguageCode(languageCode);
        result.setRemotePollSystemURL(url);
        return result;
    }

    private PollWebService getPollWebService(final String url)
            throws RemotePollException {
        Assert.notNull(url, "missing required pollServiceURL");
        LOG.info("getPollWebService called with url:" + url);
        PollWebserviceClient pc;
        try {
            pc = new PollWebserviceClient(new URL(url), qName);
        } catch (MalformedURLException e) {
            LOG.error(COMMUNICATION_ERROR_WITH_REMOTE_WS);
            throw new RemotePollException(COMMUNICATION_ERROR_WITH_REMOTE_WS + e.getMessage(), e);
        }

        final PollWebService ws = pc.getPollWebServicePort();
        return ws;
    }

    /**
     * Returns a Map containing localized names of Languages as keys and the
     * corresponding Locale code as value and vice versa. Example: <English, en>
     * <en, English>
     *
     * @return Map<Language, LocalCode>
     */
    @Override
    public Map<String, String> getLanguages() throws RemotePollException {
        final Map<String, String> languages = new HashMap<String, String>();

        final Locale[] locales = Locale.getAvailableLocales();
        for (Locale locale : locales) {
            languages.put(locale.getDisplayLanguage(), locale.getLanguage());
        }

        return languages;
    }

    @Override
    public void vote(final List<XoVote> votes) throws RemotePollException {
        LOG.info("vote( " + votes.toString() + " ) called");
        if (rmiPollService != null) {
            try {
                for (XoVote vote : votes)
                    rmiPollService.addVote(vote);
            } catch (final PollException ex) {
                LOG.error(REMOTE_CALL_FAILED);
                throw new RemotePollException(REMOTE_CALL_FAILED
                        + ex.getMessage(), ex);
            }
        } else {
            LOG.error(POLL_SERVICE_NULL);
            throw new RemotePollException(POLL_SERVICE_NULL);
        }
    }

    @Override
    public void voteRemote(final String url, final XoVote vote)
            throws RemotePollException {
        PollWebserviceClient pc;
        final PollWebService ws = getPollWebService(url);
        // TODO change signature to list, call web service with list of
        // XoVote
        ws.voteForOptions(XoXsMapper.XoVote2XsVote(vote));
    }

    // TODO FMA
    private XoPoll getRemotePoll(String uuid, String url, String languageCode)
            throws RemotePollException {
        PollWebserviceClient pc;
        final PollWebService ws = getPollWebService(url);
        // TODO FMA: add votes
        XoPollWithVotes result = XoXsMapper.xsPoll2xoPoll(
                ws.getPoll(uuid, languageCode), languageCode);
        return result.getPoll();
    }
}
