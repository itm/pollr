package de.uniluebeck.itm.ep0.poll.web;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "Poll")
public class XsPoll extends Xs {
    private String title;
    private ArrayList<XsOptionList> options;

    /**
     * @return the title
     */
    @XmlElement(name = "Title")
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the options
     */
    @XmlElement(name = "OptionList")
    public ArrayList<XsOptionList> getOptionList() {
        return options;
    }

    public XsPoll() {
        super();
        options = new ArrayList<XsOptionList>();
    }

    public void addOptionList(XsOptionList list) {
        options.add(list);

    }

}
