package de.uniluebeck.itm.ep0.poll.web;

import de.uniluebeck.itm.ep0.poll.domain.VoteType;
import de.uniluebeck.itm.ep0.poll.domain.XoPollInfo;
import de.uniluebeck.itm.ep0.poll.domain.XoVote;
import de.uniluebeck.itm.ep0.poll.service.PollService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

@WebService(targetNamespace = "www.itm.uniluebeck.de/pollservice", serviceName = "Pollservice", endpointInterface = "de.uniluebeck.itm.ep0.poll.web.PollWebService", portName = "PollWebServicePort")
public class PollWebServiceImpl implements PollWebService {

    private final static Logger LOG = LoggerFactory
            .getLogger(PollWebService.class);
    private static final ApplicationContext CTX = new ClassPathXmlApplicationContext(
            "webservice-context.xml");
    private static final PollService SERVICE = (PollService) CTX
            .getBean("pollService");

    @Resource
    private WebServiceContext wsContext;

    public PollWebServiceImpl() {
    }

    /*
    * (non-Javadoc)
    *
    * @see
    * de.uniluebeck.itm.ep0.poll.web.IPollWebService#getPolls(java.lang.String)
    */
    @Override
    public List<XsPollInfo> getPolls(final String languageCode) {
        LOG.info("getPolls(" + languageCode + ") called from IP: " + getClientIp());
        List<XoPollInfo> pollInfos = new ArrayList<XoPollInfo>();
        try {
            pollInfos = SERVICE.getPollInfos(true);
        } catch (final RemoteException ex) {
            LOG.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
        return XoXsMapper.xoPollInfo2xsPollInfo(pollInfos, languageCode);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.uniluebeck.itm.ep0.poll.web.IPollWebService#getPoll(java.lang.String,
     * java.lang.String)
     */
    @Override
    public XsPoll getPoll(final String pollId, final String languageCode) {
        try {
            LOG.info("getPoll(" + pollId + ", " + languageCode + ") called from IP: " + getClientIp());
            XsPoll result = XoXsMapper.xoPoll2xsPoll(SERVICE.getPoll(pollId),
                    languageCode);

            result = addVotes(result);

            return result;
        } catch (final RemoteException e) {
            LOG.error(e.getMessage());
        }
        return null;
    }

    private XsPoll addVotes(final XsPoll result) {

        for (XsOptionList optionList : result.getOptionList()) {
            for (XsOption option : optionList.getOptions()) {
                try {
                    option.setVotes(XoXsMapper.xoVotes2xsVotes(SERVICE
                            .getVotes(Integer.parseInt(option.getId()))));
                } catch (final NumberFormatException e) {
                    LOG.error(e.getMessage());
                } catch (final RemoteException e) {
                    LOG.error(e.getMessage());
                }
            }
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.uniluebeck.itm.ep0.poll.web.IPollWebService#voteForOptions(de.uniluebeck
     * .itm.ep0.poll.web.XsVote)
     */
    @Override
    public void voteForOptions(final XsVote vote) {
        try {
            LOG.info("voteForOptions(" + vote.getVoter() + ") called from IP: " + getClientIp());
            final XoVote v = new XoVote(vote.getVoter(), VoteType.YES, null);
            for (String optionId : vote.getOptionIds()) {
                v.setOptionId(optionId);
                SERVICE.addVote(v);
            }

        } catch (final RemoteException ex) {
            LOG.error(ex.getMessage());
        }
    }

    private String getClientIp() {
        if (null == wsContext) return "<unknown>";
        MessageContext mc = wsContext.getMessageContext();

        com.sun.net.httpserver.HttpExchange httpExchange = (com.sun.net.httpserver.HttpExchange) mc.get("com.sun.xml.internal.ws.http.exchange");
        return (null != httpExchange && null != httpExchange.getRemoteAddress()) ? httpExchange.getRemoteAddress().toString() : "<unknown>";
    }
}
