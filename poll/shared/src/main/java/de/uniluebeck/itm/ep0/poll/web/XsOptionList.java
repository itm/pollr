package de.uniluebeck.itm.ep0.poll.web;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;

public class XsOptionList extends Xs {
    private ArrayList<XsOption> options;
    private String title;

    public XsOptionList() {
        super();
        options = new ArrayList<XsOption>();
    }

    /**
     * @param options the options to set
     */
    public void addOption(XsOption option) {
        options.add(option);
    }

    /**
     * @return the options
     */
    @XmlElement(name = "Option")
    public ArrayList<XsOption> getOptions() {
        return options;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the title
     */
    @XmlElement(name = "Title")
    public String getTitle() {
        return title;
    }
}
