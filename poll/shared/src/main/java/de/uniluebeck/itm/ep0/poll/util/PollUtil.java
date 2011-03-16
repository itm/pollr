package de.uniluebeck.itm.ep0.poll.util;

import de.uniluebeck.itm.ep0.poll.domain.XoPoll;

import java.util.Date;

public class PollUtil {

    public static boolean isValid(final XoPoll poll) {
        Date today = new Date();
        return (today.equals(poll.getValidFrom()) || today.after(poll
                .getValidFrom()))
                && (today.equals(poll.getValidTo()) || today.before(poll
                .getValidTo()));
    }

    public static boolean nullOrEmpty(final String string) {
        return null == string || "".equals(string);
    }
}
