package de.uniluebeck.itm.ep0.poll.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "vote", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "voter", "option_id"})})
public class BoVote implements Bo {

    @Transient
    private static final long serialVersionUID = 2047592375660809030L;
    @Id
    @GeneratedValue
    private Integer id;
    private String voter;
    @Enumerated(EnumType.ORDINAL)
    private VoteType type;
    /**
     * This is the foreign key for the business object <code>AbstractBoOption</code>.
     */
    @Column(name = "option_id")
    private Integer optionId;

    public BoVote() {
    }

    public BoVote(final String voter, final VoteType type, final Integer optionId) {
        this.voter = voter;
        this.type = type;
        this.optionId = optionId;
    }

    public BoVote(final XoVote xoVote) {
        this.id = (null != xoVote.getId()) ? Integer.parseInt(xoVote.getId()) : null;
        this.voter = xoVote.getVoter();
        this.type = xoVote.getType();
        this.optionId = Integer.parseInt(xoVote.getOptionId());
    }

    public Integer getId() {
        return id;
    }

    public String getVoter() {
        return voter;
    }

    public VoteType getType() {
        return type;
    }

    public Integer getOptionId() {
        return optionId;
    }

    public void setPk(final Integer id) {
        this.id = id;
    }

    public void setVoter(final String voter) {
        this.voter = voter;
    }

    public void setType(final VoteType type) {
        this.type = type;
    }

    public void setOptionId(final Integer optionId) {
        this.optionId = optionId;
    }

    @Override
    public XoVote toXo() {
        return new XoVote(this.id.toString(), this.voter, this.type, this.optionId.toString());
    }

    @Override
    public String toString() {
        return "BoVote{" +
                "id=" + id +
                ", voter='" + voter + '\'' +
                ", type=" + type +
                ", optionId=" + optionId +
                '}';
    }
}
