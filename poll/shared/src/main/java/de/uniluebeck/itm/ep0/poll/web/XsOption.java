package de.uniluebeck.itm.ep0.poll.web;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

@XmlRootElement(name = "Option")
public class XsOption extends Xs {
    /**
     *
     */
    public XsOption() {
        super();
    }

    private String value;
    private Date dateTime;
    private XsVotes votes;

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param dateTime the dateTime to set
     */
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * @return the dateTime
     */


    @XmlJavaTypeAdapter(DateAdapter.class)
    public Date getDateTime() {
        return dateTime;
    }

    public void setVotes(XsVotes votes) {
        this.votes = votes;
    }

    @XmlElement(name = "votes")
    public XsVotes getVotes() {
        return votes;
    }

}
