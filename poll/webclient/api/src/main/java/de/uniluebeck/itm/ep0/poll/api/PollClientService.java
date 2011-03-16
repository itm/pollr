package de.uniluebeck.itm.ep0.poll.api;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.uniluebeck.itm.ep0.poll.domain.XoPoll;
import de.uniluebeck.itm.ep0.poll.domain.XoVote;
import de.uniluebeck.itm.ep0.poll.shared.LoadPollsResult;
import de.uniluebeck.itm.ep0.poll.shared.RemotePollException;

import java.util.List;
import java.util.Map;

@RemoteServiceRelativePath("polls")
public interface PollClientService extends RemoteService {

    List<XoPoll> getPolls() throws RemotePollException;

    XoPoll getPoll(Integer id) throws RemotePollException;

    XoPoll getPoll(String uuid, String remoteURL, String remoteLanguageCode) throws RemotePollException;

    XoPoll addPoll(XoPoll poll) throws RemotePollException;

    LoadPollsResult getPollInfos(String url, String languageCode) throws RemotePollException;

    List<XoVote> getVotes(Integer optionId) throws RemotePollException;

    Map<String, List<XoVote>> getVotesForOptionList(Integer optionListId) throws RemotePollException;

    Map<String, List<XoVote>> getVotesRemote(String url, String pollId) throws RemotePollException;

    void vote(List<XoVote> votes) throws RemotePollException;

    void voteRemote(String url, XoVote vote) throws RemotePollException;

    Map<String, String> getLanguages() throws RemotePollException;
}
