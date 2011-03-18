package de.uniluebeck.itm.ep0.poll.service;

import de.uniluebeck.itm.ep0.poll.dao.PollDao;
import de.uniluebeck.itm.ep0.poll.dao.VoteDao;
import de.uniluebeck.itm.ep0.poll.domain.BoAbstractOption;
import de.uniluebeck.itm.ep0.poll.domain.BoOptionList;
import de.uniluebeck.itm.ep0.poll.domain.BoPoll;
import de.uniluebeck.itm.ep0.poll.domain.BoTextOption;
import de.uniluebeck.itm.ep0.poll.domain.BoVote;
import de.uniluebeck.itm.ep0.poll.domain.XoOption;
import de.uniluebeck.itm.ep0.poll.domain.XoPoll;
import de.uniluebeck.itm.ep0.poll.domain.XoPollInfo;
import de.uniluebeck.itm.ep0.poll.domain.XoVote;
import de.uniluebeck.itm.ep0.poll.exception.PollException;
import de.uniluebeck.itm.ep0.poll.util.PollUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class PollServiceImpl implements PollService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PollServiceImpl.class);

    private PollDao pollDao;

    private VoteDao voteDao;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public XoPoll addPoll(final XoPoll xoPoll) throws RemoteException {
        if (xoPoll == null) {
            throw new RemoteException("xoPoll == null");
        }
        LOGGER.info("addPoll( " + xoPoll.getName() + " ) called");
        BoPoll boPoll = null;
        if (xoPoll.getId() == null) {
            // Create a new persistent poll object
            try {
                boPoll = new BoPoll(xoPoll);
                pollDao.add(boPoll);
            } catch (final PollException e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            // Update an existing persistent object
            boPoll = pollDao.findById(Integer.parseInt(xoPoll.getId()));
            if (boPoll != null) {
                pollDao.update(boPoll);
            } else {
                LOGGER.error("Poll with id #" + xoPoll.getId()
                        + " does not exist!");
            }
        }
        XoPoll result = null;
        if (boPoll != null) {
            result = boPoll.toXo();
            result.setAdminUuid(boPoll.getAdminUuid());
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<XoPoll> getPolls() throws RemoteException {
        LOGGER.info("getPolls() called");
        final List<XoPoll> xos = new ArrayList<XoPoll>();
        for (BoPoll bo : pollDao.findAll()) {
            xos.add(bo.toXo());
        }
        return xos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public XoPoll getPoll(final Integer pollId) throws RemoteException {
        if (pollId == null) {
            throw new RemoteException("pollId == null");
        }
        LOGGER.info("getPoll( " + pollId + " ) called");

        return pollDao.findById(pollId).toXo();
    }

    @Override
    @Transactional(readOnly = true)
    public XoPoll getPoll(final String uuid) throws RemoteException {
        if (uuid == null) {
            throw new RemoteException("uuid == null");
        }
        LOGGER.info("getPoll( " + uuid + " ) called");
        BoPoll boPoll = pollDao.findByUuid(uuid);
        if (boPoll == null) {
            throw new RemoteException("Poll with UUID=" + uuid + " not found!");
        }

        return boPoll.toXo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, List<XoVote>> getVotesForOptionList(final Integer optionListId) throws RemoteException {
        if (null == optionListId) {
            throw new RemoteException("optionListId == null");
        }
        LOGGER.info("getVotesForOptionList( " + optionListId + " ) called");

        Map<String, List<XoVote>> result = new HashMap<String, List<XoVote>>();

        final BoOptionList optionList = pollDao
                .findOptionList(optionListId);

        for (BoAbstractOption option : optionList.getOptions()) {
            List<BoVote> votes = voteDao.findByOptionId(option.getId());
            for (BoVote vote : votes) {
                final String voter = vote.getVoter();
                if (result.containsKey(voter)) {
                    final List<XoVote> xoVotes = result.get(voter);
                    xoVotes.add(vote.toXo());
                } else {
                    final List<XoVote> xoVotes = new ArrayList<XoVote>();
                    xoVotes.add(vote.toXo());
                    result.put(voter, xoVotes);
                }
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void removePoll(final Integer pollId, final String adminUuid) throws RemoteException {
        if (pollId == null) {
            throw new RemoteException("pollId == null");
        }
        LOGGER.info("removePoll( " + pollId + " ) called");
        final BoPoll boPoll = pollDao.findById(pollId);
        if (boPoll != null && boPoll.getAdminUuid().equals(adminUuid)) {
            pollDao.remove(boPoll);
        }
    }

    @Override
    @Transactional
    public void removeVote(final Integer voteId, final String adminUuid)
            throws RemoteException {
        if (null == voteId) {
            throw new RemoteException("voteId == null");
        }
        LOGGER.info("removeVote( " + voteId + " ) called");
        final BoVote boVote = voteDao.findById(voteId);
        final BoPoll boPoll = pollDao.findByOptionId(boVote.getOptionId());
        if (boPoll.getAdminUuid().equals(adminUuid)) {
            voteDao.remove(boVote);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<XoVote> getVotes(final Integer optionId) throws RemoteException {
        if (optionId == null) {
            throw new RemoteException("optionId == null");
        }
        LOGGER.info("getVotes( " + optionId + " ) called");
        final List<XoVote> xos = new ArrayList<XoVote>();
        for (BoVote bo : voteDao.findByOptionId(optionId)) {
            xos.add(bo.toXo());
        }
        return xos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public XoVote addVote(final XoVote xoVote) throws RemoteException {
        if (xoVote == null) {
            throw new RemoteException("xoVote == null");
        }
        LOGGER.info("addVote( " + xoVote.toString() + " ) called");

        Integer optionId;
        try {
            optionId = Integer.parseInt(xoVote.getOptionId());
        } catch (NumberFormatException ex) {
            throw new RemoteException("optionId == null", ex);
        }

        final boolean isValid = PollUtil.isValid(pollDao.findByOptionId(
                optionId).toXo());
        if (!isValid) {
            throw new RemoteException("poll is not valid");
        }

        final boolean isUnique = voteDao.findByVoterAndOption(
                xoVote.getVoter(), Integer.parseInt(xoVote.getOptionId()))
                .isEmpty();
        if (!isUnique) {
            LOGGER.info("The voter " + xoVote.getVoter()
                    + " already voted for the option " + xoVote.getOptionId()
                    + "!");
            return null;
        }

        final BoVote boVote = new BoVote(xoVote);
        voteDao.add(boVote);

        return boVote.toXo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public XoOption getMostPopularOption(final Integer pollId,
                                         final Integer optionListId) throws RemoteException, PollException {
        if (pollId == null) {
            throw new RemoteException("pollId == null");
        }
        LOGGER.info("getMostPopularOption( " + pollId + " ) called");
        final BoOptionList boOptionList = pollDao.findOptionList(pollId,
                optionListId);
        BoAbstractOption result = new BoTextOption();
        int votes = 0;
        for (BoAbstractOption boOption : boOptionList.getOptions()) {
            final List<BoVote> boVotesList = voteDao
                    .findByOptionId(boOption.getId());
            if (null != boVotesList) {
                if (votes < boVotesList.size()) {
                    votes = boVotesList.size();
                    result = boOption;
                }
            }
        }
        return (XoOption) result.toXo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<XoPollInfo> getPollInfos(final boolean onlyValidPublic)
            throws RemoteException {
        LOGGER.info("getPollInfos (" + onlyValidPublic + ") called");
        final List<XoPollInfo> result = new ArrayList<XoPollInfo>();
        for (BoPoll bo : pollDao.findAll()) {
            try {
                if (!onlyValidPublic
                        || (PollUtil.isValid(bo.toXo()) && Boolean.TRUE
                        .equals(bo.getIsPublic()))) {
                    result.add(new XoPollInfo(bo.getId().toString(), bo
                            .getUuid(), bo.getName().toXo()));
                }
            } catch (final PollException ex) {
                LOGGER.error(ex.getMessage());
                throw new RemoteException(ex.getMessage(), ex);
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<XoPollInfo> findPoll(final String name) throws RemoteException {
        try {
            List<BoPoll> polls = pollDao.findAll();

            polls = getMatches(polls, name);

            List<XoPollInfo> result = new ArrayList<XoPollInfo>(polls.size());
            for (BoPoll poll : polls) {
                result.add(new XoPollInfo(poll.getId().toString(), poll
                        .getUuid(), poll.getName().toXo()));
            }
            return result;
        } catch (final PollException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new RemoteException(ex.getMessage(), ex);
        }
    }

    private List<BoPoll> getMatches(final List<BoPoll> polls, final String name) {
        List<BoPoll> result = new ArrayList<BoPoll>();
        Pattern p = Pattern.compile(name);

        for (BoPoll poll : polls) {
            for (String localizedName : poll.getName().getLocalizedValues()
                    .values()) {
                if (p.matcher(localizedName).matches()) {
                    if (!result.contains(poll))
                        result.add(poll);
                }
            }
        }
        return result;
    }

    /**
     * Used by Spring to inject the PollDao.
     *
     * @param pollDao The poll dao
     */
    public void setPollDao(final PollDao pollDao) {
        this.pollDao = pollDao;
    }

    /**
     * Used by Spring to inject the VoteDao.
     *
     * @param voteDao The vote dao
     */
    public void setVoteDao(final VoteDao voteDao) {
        this.voteDao = voteDao;
    }
}
