package de.uniluebeck.itm.ep0.poll.web.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import de.uniluebeck.itm.ep0.poll.web.XsVote;


/**
 * <p>Java class for voteForOptions complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="voteForOptions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="voteForOptions" type="{www.itm.uniluebeck.de/pollservice}xsVote" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "voteForOptions", propOrder = {
        "voteForOptions"
})
public class VoteForOptions {

    protected XsVote voteForOptions;

    /**
     * Gets the value of the voteForOptions property.
     *
     * @return possible object is
     *         {@link XsVote }
     */
    public XsVote getVoteForOptions() {
        return voteForOptions;
    }

    /**
     * Sets the value of the voteForOptions property.
     *
     * @param value allowed object is
     *              {@link XsVote }
     */
    public void setVoteForOptions(XsVote value) {
        this.voteForOptions = value;
    }

}
