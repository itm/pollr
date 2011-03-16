/**
 *
 */
package de.uniluebeck.itm.ep0.poll.web;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "Vote")
public class XsVote extends Xs {

    private String voter;
    private List<String> optionIds = new ArrayList<String>();

    public XsVote() {

    }

    @XmlElement(name = "Voter")
    public String getVoter() {
        return voter;
    }

    public void setVoter(String voter) {
        this.voter = voter;
    }

    public void setOptionsIds(List<String> optionIds) {
        this.optionIds = optionIds;
    }

    // @XmlList()
    @XmlElement(name = "OptionId")
    public List<String> getOptionIds() {
        return optionIds;
    }

}
