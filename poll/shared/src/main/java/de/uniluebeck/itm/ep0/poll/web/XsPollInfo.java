package de.uniluebeck.itm.ep0.poll.web;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PollInfo")
public class XsPollInfo extends Xs {
    private String title;

    public XsPollInfo(String id, String title) {
        setId(id);
        this.title = title;
    }

    public XsPollInfo() {
        super();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement(name = "Title")
    public String getTitle() {
        return title;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "XsPollInfo [title=" + title + ", getId()=" + getId() + "]";
    }

}
