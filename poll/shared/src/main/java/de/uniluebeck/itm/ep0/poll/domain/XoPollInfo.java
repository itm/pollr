package de.uniluebeck.itm.ep0.poll.domain;

public class XoPollInfo implements Xo {

    private static final long serialVersionUID = 3290997421380927336L;

    private XoLocalizedString name;

    private String uuid;

    private String id;

    public XoPollInfo() {
    }

    public XoPollInfo(final String id, final String uuid,
                      final XoLocalizedString name) {
        this.setUuid(uuid);
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setName(final XoLocalizedString name) {
        this.name = name;
    }

    public XoLocalizedString getName() {
        return name;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "XoPollInfo{" +
                "name=" + name +
                ", uuid='" + uuid + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
