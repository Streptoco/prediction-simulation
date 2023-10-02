//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.10.02 at 04:59:51 PM IDT 
//


package engine.worldbuilder.prdobjects;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="PRD-by-user" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;choice maxOccurs="2">
 *           &lt;element ref="{}PRD-by-second"/>
 *           &lt;element ref="{}PRD-by-ticks"/>
 *         &lt;/choice>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "prdByUser",
    "prdBySecondOrPRDByTicks"
})
@XmlRootElement(name = "PRD-termination")
public class PRDTermination {

    @XmlElement(name = "PRD-by-user")
    protected Object prdByUser;
    @XmlElements({
        @XmlElement(name = "PRD-by-second", type = PRDBySecond.class),
        @XmlElement(name = "PRD-by-ticks", type = PRDByTicks.class)
    })
    protected List<Object> prdBySecondOrPRDByTicks;

    /**
     * Gets the value of the prdByUser property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getPRDByUser() {
        return prdByUser;
    }

    /**
     * Sets the value of the prdByUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setPRDByUser(Object value) {
        this.prdByUser = value;
    }

    /**
     * Gets the value of the prdBySecondOrPRDByTicks property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prdBySecondOrPRDByTicks property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPRDBySecondOrPRDByTicks().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PRDBySecond }
     * {@link PRDByTicks }
     * 
     * 
     */
    public List<Object> getPRDBySecondOrPRDByTicks() {
        if (prdBySecondOrPRDByTicks == null) {
            prdBySecondOrPRDByTicks = new ArrayList<Object>();
        }
        return this.prdBySecondOrPRDByTicks;
    }

}
