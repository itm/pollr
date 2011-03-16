package de.uniluebeck.itm.ep0.poll.domain;

import java.util.ArrayList;
import java.util.List;

public class XoOptionList implements Xo {

    private static final long serialVersionUID = 270800388432513392L;
    private String id;
    private XoLocalizedString name;
    private List<XoOption> options;

    public XoOptionList() {
    }

    public XoOptionList(final XoLocalizedString name) {
        this.name = name;
        this.options = new ArrayList<XoOption>();
    }

    public XoOptionList(final String id, final XoLocalizedString name) {
        this.id = id;
        this.name = name;
        this.options = new ArrayList<XoOption>();
    }

    public XoOptionList(final XoLocalizedString name, final List<XoOption> options) {
        this.name = name;
        this.options = options;
    }

    public XoOptionList(final String id, final XoLocalizedString name, final List<XoOption> options) {
        this.id = id;
        this.name = name;
        this.options = options;
    }

    @Override
    public String getId() {
        return id;
    }

    public XoLocalizedString getName() {
        return name;
    }

    public void setName(final XoLocalizedString name) {
        this.name = name;
    }

    public List<XoOption> getOptions() {
        return options;
    }

    public void setOptions(final List<XoOption> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "XoOptionList{" +
                "id='" + id + '\'' +
                ", name=" + name +
                ", options=" + options.toString() +
                '}';
    }
}
