package de.uniluebeck.itm.ep0.poll.web;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAdapter extends XmlAdapter<String, Date> {

    /**
     * The desired format
     */
    private final String pattern = "yyyy-MM-dd'T'HH:mm";

    @Override
    public String marshal(final Date date) throws Exception {
        return new SimpleDateFormat(pattern).format(date);
    }

    @Override
    public Date unmarshal(final String dateString) throws Exception {
        return new SimpleDateFormat(pattern).parse(dateString);
    }

}