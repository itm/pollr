package de.uniluebeck.itm.ep0.poll.domain;

import de.uniluebeck.itm.ep0.poll.exception.PollException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;

@Entity
@DiscriminatorValue("DATE")
public class BoDateOption extends BoAbstractOption {

    @Transient
    private static final long serialVersionUID = -2030218482407285034L;
    @Temporal(TemporalType.DATE)
    private Date dateOption;

    public BoDateOption() {
    }

    public BoDateOption(final Date date, final BoLocalizedString description) {
        super(description);
        this.dateOption = date;
    }

    public BoDateOption(final XoDateOption xoDateOption, final BoOptionList boOptionList) throws PollException {
        super((null != xoDateOption.getId()) ? Integer.parseInt(xoDateOption.getId()) : null,
                new BoLocalizedString(xoDateOption.getDescription()));
        this.dateOption = xoDateOption.getDateOption();
        setOptionList(boOptionList);
    }

    public Date getDateOption() {
        return dateOption;
    }

    public void setDateOption(final Date dateOption) {
        this.dateOption = dateOption;
    }

    @Override
    public String toString() {
        return "BoDateOption{" +
                "id=" + getId() +
                ", description=" + getDescription() +
                ", optionList=" + getOptionList().getName() +
                "dateOption=" + dateOption +
                '}';
    }

    @Override
    public XoDateOption toXo() {
        XoDateOption xo = null;
        try {
            xo = new XoDateOption(getId().toString(), getDateOption(),
                    getDescription().toXo());
        } catch (final PollException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return xo;
    }
}
