package de.uniluebeck.itm.ep0.poll.domain;


public class XoTextOption extends XoOption {

    private static final long serialVersionUID = 5822829864268719130L;

    private XoLocalizedString text;

    public XoTextOption() {
    }

    public XoTextOption(final XoLocalizedString text,
                        final XoLocalizedString description) {
        super(description);
        this.text = text;
    }

    public XoTextOption(final String id, final XoLocalizedString text,
                        final XoLocalizedString description) {
        super(id, description);
        this.text = text;
    }

    public XoLocalizedString getText() {
        return text;
    }

    public void setText(XoLocalizedString text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "XoDateOption{" +
                "id='" + getId() + '\'' +
                ", description=" + getDescription() +
                "text=" + text +
                '}';
    }
}
