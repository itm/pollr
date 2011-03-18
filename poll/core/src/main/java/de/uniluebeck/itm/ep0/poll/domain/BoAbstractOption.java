package de.uniluebeck.itm.ep0.poll.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity
@Table(name = "poll_option")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "option_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Option")
public abstract class BoAbstractOption implements Bo {

    @Transient
    protected static final Logger LOGGER = LoggerFactory.getLogger(BoAbstractOption.class);
    @Transient
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL)
    private BoLocalizedString description;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "option_list_fk")
    private BoOptionList optionList;

    public BoAbstractOption() {
    }

    public BoAbstractOption(final BoLocalizedString description) {
        this.description = description;
    }

    public BoAbstractOption(final Integer id,
                            final BoLocalizedString description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public BoLocalizedString getDescription() {
        return description;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setDescription(final BoLocalizedString description) {
        this.description = description;
    }

    public BoOptionList getOptionList() {
        return optionList;
    }

    public void setOptionList(final BoOptionList optionList) {
        this.optionList = optionList;
    }
}
