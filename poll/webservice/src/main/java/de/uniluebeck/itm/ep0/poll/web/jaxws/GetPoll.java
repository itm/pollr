package de.uniluebeck.itm.ep0.poll.web.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getPoll complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="getPoll">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pollId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="languageCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getPoll", propOrder = {
        "pollId",
        "languageCode"
})
public class GetPoll {

    protected String pollId;
    protected String languageCode;

    /**
     * Gets the value of the pollId property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPollId() {
        return pollId;
    }

    /**
     * Sets the value of the pollId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPollId(String value) {
        this.pollId = value;
    }

    /**
     * Gets the value of the languageCode property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Sets the value of the languageCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLanguageCode(String value) {
        this.languageCode = value;
    }

}
