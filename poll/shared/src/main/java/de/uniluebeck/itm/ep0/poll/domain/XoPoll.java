package de.uniluebeck.itm.ep0.poll.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XoPoll implements Xo {

    private static final long serialVersionUID = 972191693077560657L;
    private String uuid;
    private String adminUuid;
    private String id;
    private XoLocalizedString name;
    private List<XoOptionList> optionLists;
    private Date validFrom;
    private Date validTo;
    private Boolean isPublic;

    public XoPoll() {
    }

    public XoPoll(final XoLocalizedString name) {
        this.name = name;
        this.optionLists = new ArrayList<XoOptionList>();
    }

    public XoPoll(final String id, final XoLocalizedString name) {
        this.id = id;
        this.name = name;
        this.optionLists = new ArrayList<XoOptionList>();
    }

    public XoPoll(final XoLocalizedString name,
                  final List<XoOptionList> optionLists) {
        this.name = name;
        this.optionLists = optionLists;
    }

    public XoPoll(final String id, final XoLocalizedString name,
                  final List<XoOptionList> optionLists, Date validFrom, Date validTo,
                  Boolean isPublic) {
        this.id = id;
        this.name = name;
        this.optionLists = optionLists;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.isPublic = isPublic;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
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

    public List<XoOptionList> getOptionLists() {
        return optionLists;
    }

    public void setOptionLists(final List<XoOptionList> optionLists) {
        this.optionLists = optionLists;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public String getUuid() {
        return uuid;
    }

    public String getAdminUuid() {
        return adminUuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setAdminUuid(String adminUuid) {
        this.adminUuid = adminUuid;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    @Override
    public String toString() {
        return "XoPoll{" +
                "uuid='" + uuid + '\'' +
                ", adminUuid='" + adminUuid + '\'' +
                ", id='" + id + '\'' +
                ", name=" + name +
                ", optionLists=" + optionLists.toString() +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                ", isPublic=" + isPublic +
                '}';
    }
}
