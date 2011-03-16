
package de.uniluebeck.itm.ep0.poll.web.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import de.uniluebeck.itm.ep0.poll.web.XsPoll;


/**
 * <p>Java class for getPollResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getPollResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{www.itm.uniluebeck.de/pollservice}xsPoll" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getPollResponse", propOrder = {
    "_return"
})
public class GetPollResponse {

    @XmlElement(name = "return")
    protected XsPoll _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link XsPoll }
     *     
     */
    public XsPoll getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link XsPoll }
     *     
     */
    public void setReturn(XsPoll value) {
        this._return = value;
    }

}
