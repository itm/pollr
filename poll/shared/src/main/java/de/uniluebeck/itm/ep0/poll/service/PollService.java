package de.uniluebeck.itm.ep0.poll.service;

import de.uniluebeck.itm.ep0.poll.domain.XoOption;
import de.uniluebeck.itm.ep0.poll.domain.XoPoll;
import de.uniluebeck.itm.ep0.poll.domain.XoPollInfo;
import de.uniluebeck.itm.ep0.poll.domain.XoVote;
import de.uniluebeck.itm.ep0.poll.exception.PollException;

import java.util.List;
import java.util.Map;

public interface PollService {

    /**
     * Adds a poll. The transport object is converted to a business object and
     * permanently stored.
     *
     * @param poll The <code>Poll</code> to be added
     * @return The <code>Poll</code> with the IDs set
     * @throws PollException
     */
    XoPoll addPoll(XoPoll poll) throws PollException;

    /**
     * Get all polls. Returns a list of poll transport objects that are
     * retrieved from the permanent storage.
     *
     * @return List of <code>XoPoll</code> objects
     * @throws PollException
     */
    List<XoPoll> getPolls() throws PollException;

    /**
     * Returns the poll specified by the given ID.
     *
     * @param pollId The poll's ID
     * @return The <code>XoPoll</code> object
     * @throws PollException
     */
    XoPoll getPoll(Integer pollId) throws PollException;

    /**
     * Remove a poll with the given ID.
     *
     * @param pollId    The poll's ID
     * @param adminUuid administration uuid authorizing the removal
     * @throws PollException
     */
    void removePoll(Integer pollId, String adminUuid) throws PollException;

    /**
     * Adds a vote. The transport object is converted to a
     * business object and permanently stored. A vote is only added,
     * if the voter did not already vote for this option.
     *
     * @param vote The <code>Vote</code> to be added
     * @return <code>XoVote</code> object with an valid ID.
     * @throws PollException
     */
    XoVote addVote(XoVote vote) throws PollException;

    /**
     * Get all votes. Returns a list of vote transport objects that are
     * retrieved from the permanent storage.
     *
     * @param optionId The option's ID to which the vote is connected
     * @return List of <code>XoVote</code> objects
     * @throws PollException
     */
    List<XoVote> getVotes(Integer optionId) throws PollException;

    /**
     * Returns the most popular option as transport object for the poll defined
     * by the given ID.
     *
     * @param pollId       The poll's ID
     * @param optionListId The option list's ID
     * @return The most popular <code>XoOption</code> for the given poll
     * @throws PollException*
     */
    XoOption getMostPopularOption(Integer pollId, Integer optionListId) throws PollException;

    /**
     * Retrieve a list of {@code XoPollInfo} of polls currently in the system
     *
     * @param onlyValidPublic if true only polls that are valid and public at the current date are returned
     * @return list of pollInfos
     * @throws PollException
     */
    List<XoPollInfo> getPollInfos(boolean onlyValidPublic) throws PollException;

    /**
     * Removes a vote.
     *
     * @param voteId    Id of the vote to be removed.
     * @param adminUuid administration uuid of the poll, authorizing the removal
     * @throws PollException
     */
    void removeVote(Integer voteId, String adminUuid) throws PollException;

    /**
     * Returns the poll specified by the given UUID.
     *
     * @param uuid The poll's UUID
     * @return The <code>XoPoll</code> object
     * @throws PollException
     */
    XoPoll getPoll(String uuid) throws PollException;

    /**
     * Searches for Polls by Name
     *
     * @param name The polls name. Wild card '%' is allowed.
     * @return List of polls matching the specified poll name.
     * @throws PollException
     */
    List<XoPollInfo> findPoll(String name) throws PollException;

    /**
     * @param optionListId
     * @return
     * @throws PollException
     */
    Map<String, List<XoVote>> getVotesForOptionList(Integer optionListId) throws PollException;
}
