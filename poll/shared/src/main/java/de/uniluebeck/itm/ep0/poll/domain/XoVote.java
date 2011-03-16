package de.uniluebeck.itm.ep0.poll.domain;

public class XoVote implements Xo {

    private static final long serialVersionUID = -254930779884972200L;
    private String id;
    private String voter;
    private VoteType type;
    private String optionId;

    public XoVote() {
    }

    public XoVote(final String voter, final VoteType type, final String optionId) {
        this.voter = voter;
        this.type = type;
        this.optionId = optionId;
    }

    public XoVote(final String id, final String voter, final VoteType type, final String optionId) {
        this.id = id;
        this.voter = voter;
        this.type = type;
        this.optionId = optionId;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getVoter() {
        return voter;
    }

    public void setVoter(final String voter) {
        this.voter = voter;
    }

    public VoteType getType() {
        return type;
    }

    public void setType(final VoteType type) {
        this.type = type;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(final String optionId) {
        this.optionId = optionId;
    }

    @Override
    public String toString() {
        return "XoVote{" +
                "id='" + id + '\'' +
                ", voter='" + voter + '\'' +
                ", type=" + type +
                ", optionId='" + optionId + '\'' +
                '}';
    }
}
