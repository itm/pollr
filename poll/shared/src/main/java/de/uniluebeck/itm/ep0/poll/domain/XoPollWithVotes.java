package de.uniluebeck.itm.ep0.poll.domain;

import java.util.List;

public class XoPollWithVotes implements Xo {

    private static final long serialVersionUID = 6212418609206830732L;

    private XoPoll poll;

    private List<XoVote> votes;

    @Override
    public String getId() {
        return null;
    }

    public void setPoll(XoPoll poll) {
        this.poll = poll;
    }

    public XoPoll getPoll() {
        return poll;
    }

    public void setVotes(List<XoVote> votes) {
        this.votes = votes;
    }

    public List<XoVote> getVotes() {
        return votes;
    }
}
