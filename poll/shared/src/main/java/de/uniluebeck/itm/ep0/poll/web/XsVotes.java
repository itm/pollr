package de.uniluebeck.itm.ep0.poll.web;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "Votes")
public class XsVotes extends Xs {
    public XsVotes() {
        super();
    }

    private List<String> voters;

    public void setVoters(List<String> voters) {
        this.voters = voters;
    }

    @XmlElement(name = "Voter")
    public List<String> getVoters() {
        return voters;
    }
}
