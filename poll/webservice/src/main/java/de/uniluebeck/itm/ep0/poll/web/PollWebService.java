package de.uniluebeck.itm.ep0.poll.web;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace = "www.itm.uniluebeck.de/pollservice", name = "PollService")
public interface PollWebService {

    @WebMethod
    public abstract List<XsPollInfo> getPolls(
            @WebParam(name = "languageCode") String languageCode);

    @WebMethod
    public abstract XsPoll getPoll(@WebParam(name = "pollId") String pollId,
                                   @WebParam(name = "languageCode") String languageCode);

    @WebMethod
    public abstract void voteForOptions(
            @WebParam(name = "voteForOptions") XsVote vote);

}