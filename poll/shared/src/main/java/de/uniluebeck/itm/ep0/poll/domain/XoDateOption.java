package de.uniluebeck.itm.ep0.poll.domain;

import java.util.Date;

public class XoDateOption extends XoOption {

    private static final long serialVersionUID = 5822829864268719130L;

    private Date dateOption;

    public XoDateOption() {
    }

    public XoDateOption(final Date dateOption,
                        final XoLocalizedString description) {
        super(description);
        this.dateOption = dateOption;
    }

    public XoDateOption(final String id, final Date dateOption,
                        final XoLocalizedString description) {
        super(id, description);
        this.dateOption = dateOption;
    }

    public Date getDateOption() {
        return dateOption;
    }

    public void setDateOption(Date dateOption) {
        this.dateOption = dateOption;
    }

    @Override
    public String toString() {
        return "XoDateOption{" +
                "id='" + getId() + '\'' +
                ", description=" + getDescription() +
                "dateOption=" + dateOption +
                '}';
    }
}
