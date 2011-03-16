package de.uniluebeck.itm.ep0.poll.domain;

import de.uniluebeck.itm.ep0.poll.exception.PollException;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "localized_string")
public class BoLocalizedString implements Bo {

    @Transient
    private static final String LOCALE_EN = "en";
    @Transient
    private static final long serialVersionUID = 2981686458917348230L;
    @Id
    @GeneratedValue
    private Integer id;
    @ElementCollection
    @CollectionTable(name = "localized_values")
    private Map<String, String> localizedValues = new HashMap<String, String>();

    public BoLocalizedString() {
    }

    public BoLocalizedString(final XoLocalizedString xoLocalizedString) throws PollException {
        for (String lang : xoLocalizedString.getLocalizedValues().keySet()) {
            localizedValues.put(lang, xoLocalizedString.getLocalizedValues().get(lang));
        }
    }

    public BoLocalizedString(final String value) {
        localizedValues.put(LOCALE_EN, value);
    }

    public BoLocalizedString(final String language, final String value) {
        localizedValues.put(language, value);
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Map<String, String> getLocalizedValues() {
        return localizedValues;
    }

    public void setLocalizedValues(final Map<String, String> localizedValues) {
        this.localizedValues = localizedValues;
    }

    @Override
    public String toString() {
        return "BoLocalizedString{" +
                "id=" + id +
                ", localizedValues=" + toStringHelper() +
                '}';
    }

    private String toStringHelper() {
        String result = "[";
        for (String localalizedValue : localizedValues.values()) {
            result += localalizedValue + ", ";
        }
        // Cut off last comma
        return result.substring(0, result.length() - 2) + "]";
    }

    @Override
    public XoLocalizedString toXo() throws PollException {
        final XoLocalizedString xo = new XoLocalizedString();
        xo.setId(this.id.toString());
        for (String lang : this.localizedValues.keySet()) {
            xo.getLocalizedValues().put(lang, localizedValues.get(lang));
        }
        return xo;
    }
}
