package de.uniluebeck.itm.ep0.poll.domain;

import java.util.HashMap;
import java.util.Map;

public class XoLocalizedString implements Xo {

    private static final long serialVersionUID = 4575092116013790761L;

    private static final String LOCALE_EN = "en";

    private String id;

    private Map<String, String> localizedValues = new HashMap<String, String>();

    public XoLocalizedString() {
    }

    public XoLocalizedString(final String value) {
        localizedValues.put(LOCALE_EN, value);
    }

    public XoLocalizedString(final String id, final String language, final String value) {
        localizedValues.put(language, value);
    }

    public XoLocalizedString(final String id, final String value) {
        this.id = id;
        localizedValues.put(LOCALE_EN, value);
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public Map<String, String> getLocalizedValues() {
        return localizedValues;
    }

    public void setLocalizedValues(final Map<String, String> localizedValues) {
        this.localizedValues = localizedValues;
    }

    public String getLocalizedValue(String language) {
        String result;
        if (language.length() == 5 && language.indexOf('_') != -1) {
            language = language.substring(0, 1);
        }
        if (localizedValues.containsKey(language)) {
            result = localizedValues.get(language);
        } else {
            result = localizedValues.get(LOCALE_EN);
        }
        return result;
    }

    @Override
    public String toString() {
        return "XoLocalizedString{" + "id=" + id + "localizedValues=" + localizedValues + '}';
    }
}
