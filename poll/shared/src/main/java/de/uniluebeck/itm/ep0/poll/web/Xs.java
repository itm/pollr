package de.uniluebeck.itm.ep0.poll.web;

import javax.xml.bind.annotation.XmlElement;

public class Xs {

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement(name = "Id")
    public String getId() {
        return id;
    }
}
