package de.uniluebeck.itm.ep0.poll.domain;

import de.uniluebeck.itm.ep0.poll.exception.PollException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "option_list")
public class BoOptionList implements Bo {

    @Transient
    private final static Logger LOG = LoggerFactory.getLogger(BoOptionList.class);
    @Transient
    private static final long serialVersionUID = -1880503413753517504L;
    @Id
    @GeneratedValue
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL)
    private BoLocalizedString name;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "option_list_fk")
    private List<BoAbstractOption> options;
    @ManyToOne
    @JoinColumn(name = "poll_fk")
    private BoPoll poll;

    public BoOptionList() {
    }

    public BoOptionList(final BoLocalizedString name) {
        this.name = name;
        options = new ArrayList<BoAbstractOption>();
    }

    public BoOptionList(final XoOptionList xoOptionList) throws PollException {
        this.name = new BoLocalizedString(xoOptionList.getName());
        options = new ArrayList<BoAbstractOption>();
        for (XoOption xo : xoOptionList.getOptions()) {
            if (xo instanceof XoDateOption) {
                options.add(new BoDateOption((XoDateOption) xo, this));
            } else if (xo instanceof XoTextOption) {
                options.add(new BoTextOption((XoTextOption) xo, this));
            }
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public BoLocalizedString getName() {
        return name;
    }

    public void setName(final BoLocalizedString name) {
        this.name = name;
    }

    public List<BoAbstractOption> getOptions() {
        return options;
    }

    public void setOptions(final List<BoAbstractOption> options) {
        this.options = options;
    }

    public BoPoll getPoll() {
        return poll;
    }

    public void setPoll(final BoPoll poll) {
        this.poll = poll;
    }

    @Override
    public String toString() {
        return "BoOptionList{" +
                "id=" + id +
                ", name=" + name +
                ", options=" + options.toString() +
                ", poll=" + poll.getName() +
                '}';
    }

    @Override
    public XoOptionList toXo() {
        final List<XoOption> xoOptions = new ArrayList<XoOption>(
                this.options.size());
        for (BoAbstractOption option : this.options) {
            try {
                if (option instanceof BoDateOption) {
                    xoOptions.add((XoDateOption) option.toXo());
                } else if (option instanceof BoTextOption) {
                    xoOptions.add((XoTextOption) option.toXo());
                }
            } catch (final PollException ex) {
                LOG.error(ex.getMessage(), ex);
            }
        }

        XoOptionList xoOptionList = null;
        try {
            xoOptionList = new XoOptionList(this.id.toString(), this.name.toXo(), xoOptions);
        } catch (final PollException e) {
            LOG.error(e.getMessage());
        }

        return xoOptionList;
    }
}
