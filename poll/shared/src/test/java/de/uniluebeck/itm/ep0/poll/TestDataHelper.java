package de.uniluebeck.itm.ep0.poll;

import de.uniluebeck.itm.ep0.poll.domain.XoDateOption;
import de.uniluebeck.itm.ep0.poll.domain.XoLocalizedString;
import de.uniluebeck.itm.ep0.poll.domain.XoOption;
import de.uniluebeck.itm.ep0.poll.domain.XoOptionList;
import de.uniluebeck.itm.ep0.poll.domain.XoPoll;
import de.uniluebeck.itm.ep0.poll.domain.XoTextOption;
import de.uniluebeck.itm.ep0.poll.service.PollService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

public class TestDataHelper {

    private final static Logger LOG = LoggerFactory.getLogger(TestDataHelper.class);

    private final static int DAYS_OF_WEEK = 7;

    private final static String LOCALE_DE = "de";

    /**
     * Creates a test poll with the given poll service
     *
     * @param service which is used to store the test poll
     * @return test poll with IDs set
     */
    public static XoPoll createAndAddPoll(final PollService service) {
        try {
            final XoTextOption wine = new XoTextOption(new XoLocalizedString(
                    "Wine"), new XoLocalizedString("red and tasty"));
            wine.getText().getLocalizedValues().put(LOCALE_DE, "Wein");
            wine.getDescription().getLocalizedValues()
                    .put(LOCALE_DE, "rot und lecker");

            final XoTextOption beer = new XoTextOption(new XoLocalizedString(
                    "Beer"), new XoLocalizedString("cold and tasty"));
            beer.getText().getLocalizedValues().put(LOCALE_DE, "Bier");
            beer.getDescription().getLocalizedValues()
                    .put(LOCALE_DE, "kuehl und lecker");

            final XoTextOption whiskey = new XoTextOption(
                    new XoLocalizedString("Whiskey"), new XoLocalizedString(
                            "strong and tasty"));
            whiskey.getText().getLocalizedValues().put(LOCALE_DE, "Whiskey");
            whiskey.getDescription().getLocalizedValues()
                    .put(LOCALE_DE, "stark und lecker");

            final XoOptionList drinks = new XoOptionList(new XoLocalizedString(
                    "Drinks"));
            drinks.getName().getLocalizedValues().put(LOCALE_DE, "Getraenke");
            drinks.getOptions().add(wine);
            drinks.getOptions().add(beer);
            drinks.getOptions().add(whiskey);

            final XoTextOption home = new XoTextOption(new XoLocalizedString(
                    "Home"), new XoLocalizedString("cheap and comfy"));
            home.getText().getLocalizedValues().put(LOCALE_DE, "zu Hause");
            home.getDescription().getLocalizedValues()
                    .put(LOCALE_DE, "guenstig und gemuetlich");

            final XoTextOption bar = new XoTextOption(new XoLocalizedString(
                    "Bar"), new XoLocalizedString("expensive but exclusive"));
            bar.getText().getLocalizedValues().put(LOCALE_DE, "Bar");
            bar.getDescription().getLocalizedValues()
                    .put(LOCALE_DE, "teuer aber exklusiv");

            final XoOptionList location = new XoOptionList(
                    new XoLocalizedString("Location"));
            location.getName().getLocalizedValues().put(LOCALE_DE, "Ort");
            location.getOptions().add(home);
            location.getOptions().add(bar);


            XoDateOption today = new XoDateOption(new Date(), new XoLocalizedString("today"));
            Calendar tomorrowC = Calendar.getInstance();
            tomorrowC.add(Calendar.DATE, 1);
            XoDateOption tomorrow = new XoDateOption(tomorrowC.getTime(), new XoLocalizedString("tomorrow"));

            final XoOptionList when = new XoOptionList(new XoLocalizedString("when"));
            when.getName().getLocalizedValues().put(LOCALE_DE, "Wann");
            when.getOptions().add(today);
            when.getOptions().add(tomorrow);

            final XoPoll poll = new XoPoll(new XoLocalizedString(
                    "Saturday evening"));
            poll.getName().getLocalizedValues().put(LOCALE_DE, "Samstag abend");
            poll.getOptionLists().add(drinks);
            poll.getOptionLists().add(location);
            poll.getOptionLists().add(when);
            poll.setValidFrom(new Date());
            final Calendar nextWeek = Calendar.getInstance();
            nextWeek.add(Calendar.DAY_OF_MONTH, DAYS_OF_WEEK);
            poll.setValidTo(nextWeek.getTime());
            poll.setIsPublic(Boolean.TRUE);

            return service.addPoll(poll);
        } catch (final RemoteException ex) {
            LOG.error(ex.getMessage());
        }
        return null;
    }

    public static String getIdForTextOption(final XoPoll poll,
                                            final String language, final String optionTitle) {
        for (XoOptionList optionList : poll.getOptionLists()) {
            for (XoOption option : optionList.getOptions()) {
                if (option instanceof XoTextOption
                        && optionTitle.equals(((XoTextOption) option).getText()
                        .getLocalizedValue(language))) {
                    return option.getId();
                }
            }

        }
        return null;
    }

    public static Integer getIdForTextOptionInt(final XoPoll poll,
                                                final String language, final String optionTitle) {
        return Integer.parseInt(getIdForTextOption(poll, language, optionTitle));
    }

    public static String getIdForDateOption(final XoPoll poll,
                                            final String language, final String description) {
        String result = null;
        for (XoOptionList optionList : poll.getOptionLists()) {
            for (XoOption option : optionList.getOptions()) {
                if (option instanceof XoDateOption
                        && description.equals(((XoDateOption) option).getDescription()
                        .getLocalizedValue(language))) {
                    result = option.getId();
                    break;
                }
            }
        }
        return result;
    }

    public static Integer getIdForDateOptionInt(final XoPoll poll,
                                                final String language, final String description) {
        return Integer.parseInt(getIdForDateOption(poll, language, description));
    }
}
