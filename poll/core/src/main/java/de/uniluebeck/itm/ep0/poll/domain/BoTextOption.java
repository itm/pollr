package de.uniluebeck.itm.ep0.poll.domain;

import de.uniluebeck.itm.ep0.poll.exception.PollException;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("TEXT")
public class BoTextOption extends BoAbstractOption {

    @Transient
    private static final long serialVersionUID = -2030218482407285034L;
    @OneToOne(cascade = CascadeType.ALL)
    private BoLocalizedString text;

    public BoTextOption() {
    }

    public BoTextOption(final BoLocalizedString text,
                        final BoLocalizedString description) {
        super(description);
        this.text = text;
    }

    public BoTextOption(final Integer id, final BoLocalizedString text,
                        final BoLocalizedString description) {
        super(id, description);
        this.text = text;
    }

    public BoTextOption(final XoTextOption xoTextOption,
                        final BoOptionList boOptionList) throws PollException {
        super((null != xoTextOption.getId()) ? Integer.parseInt(xoTextOption.getId()) : null,
                new BoLocalizedString(xoTextOption.getDescription()));
        this.text = new BoLocalizedString(xoTextOption.getText());
        setOptionList(boOptionList);
    }

    public BoLocalizedString getText() {
        return text;
    }

    public void setText(final BoLocalizedString text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "BoDateOption{" +
                "id=" + getId() +
                ", description=" + getDescription() +
                ", optionList=" + getOptionList().getName() +
                "text=" + text +
                '}';
    }

    @Override
    public XoOption toXo() {
        XoTextOption xo = null;
        try {
            xo = new XoTextOption(getId().toString(), getText().toXo(), getDescription()
                    .toXo());
        } catch (final PollException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return xo;
    }
}
