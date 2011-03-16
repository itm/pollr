package de.uniluebeck.itm.ep0.poll.domain;

import de.uniluebeck.itm.ep0.poll.exception.PollException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "poll")
public class BoPoll implements Bo {

    @Transient
    private final static Logger LOG = LoggerFactory.getLogger(BoPoll.class);
    @Transient
    private static final long serialVersionUID = -980869932728084332L;
    @Id
    @GeneratedValue
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL)
    private BoLocalizedString name;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "poll_fk")
    private List<BoOptionList> optionLists;
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Temporal(TemporalType.DATE)
    private Date validTo;
    private Boolean isPublic;
    private String uuid;
    private String adminUuid;

    public BoPoll() {
    }

    public BoPoll(final BoLocalizedString name) {
        this.name = name;
        this.optionLists = new ArrayList<BoOptionList>();
    }

    public BoPoll(final XoPoll xoPoll) throws PollException {
        this.id = (null != xoPoll.getId()) ? Integer.parseInt(xoPoll.getId()) : null;
        this.validFrom = xoPoll.getValidFrom();
        this.validTo = xoPoll.getValidTo();
        this.isPublic = xoPoll.getIsPublic();
        this.uuid = UUID.randomUUID().toString();
        this.adminUuid = UUID.randomUUID().toString();
        try {
            this.name = new BoLocalizedString(xoPoll.getName());
        } catch (final PollException e) {
            LOG.error(e.getMessage());
        }
        this.optionLists = new ArrayList<BoOptionList>();
        for (XoOptionList xoOptionList : xoPoll.getOptionLists()) {
            this.optionLists.add(new BoOptionList(xoOptionList));
        }
    }

    public Integer getId() {
        return id;
    }

    public BoLocalizedString getName() {
        return name;
    }

    public List<BoOptionList> getOptionLists() {
        return optionLists;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setName(final BoLocalizedString name) {
        this.name = name;
    }

    public void setOptions(final List<BoOptionList> optionLists) {
        this.optionLists = optionLists;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(final Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(final Date validTo) {
        this.validTo = validTo;
    }

    public String getAdminUuid() {
        return adminUuid;
    }

    public void setAdminUuid(final String adminUuid) {
        this.adminUuid = adminUuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public void setOptionLists(final List<BoOptionList> optionLists) {
        this.optionLists = optionLists;
    }

    public void setIsPublic(final Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    @Override
    public String toString() {
        return "BoPoll{" +
                "id=" + id +
                ", name=" + name +
                ", optionLists=" + optionLists +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                ", isPublic=" + isPublic +
                ", uuid='" + uuid + '\'' +
                ", adminUuid='" + adminUuid + '\'' +
                '}';
    }

    @Override
    public XoPoll toXo() {
        final List<XoOptionList> xoOptionLists = new ArrayList<XoOptionList>(
                getOptionLists().size());
        for (BoOptionList ol : optionLists) {
            xoOptionLists.add(ol.toXo());
        }

        final XoLocalizedString xoName = new XoLocalizedString();
        for (String lang : this.name.getLocalizedValues().keySet()) {
            xoName.getLocalizedValues().put(lang, this.name.getLocalizedValues().get(lang));
        }

        final XoPoll result = new XoPoll(this.id.toString(), xoName, xoOptionLists, validFrom, validTo, isPublic);
        result.setUuid(uuid);
        return result;
    }
}
