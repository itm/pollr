package de.uniluebeck.itm.ep0.poll.domain;


public class XoOption implements Xo {

    private static final long serialVersionUID = 5822829864268719130L;
    private String id;
    private XoLocalizedString description;

    public XoOption() {
    }

    public XoOption(final String id) {
        this.id = id;
    }

    public XoOption(final XoLocalizedString description) {
        this.description = description;
    }

    public XoOption(final String id, final XoLocalizedString description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public XoLocalizedString getDescription() {
        return description;
    }

    public void setDescription(final XoLocalizedString description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "XoOption{" +
                "id='" + id + '\'' +
                ", description=" + description +
                '}';
    }
}
