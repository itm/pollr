package de.uniluebeck.itm.ep0.poll.client.ui;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;

import de.uniluebeck.itm.ep0.poll.client.PollApp;
import de.uniluebeck.itm.ep0.poll.domain.XoLocalizedString;
import de.uniluebeck.itm.ep0.poll.domain.XoOption;

public interface OptionView extends BasicView<OptionView.Presenter>, IsWidget {

    HasText getNumberLabel();

    HasText getTextLabel();

    HasText getDateLabel();

    HasValue<XoLocalizedString> getTextBox();

    HasValue<java.util.Date> getDateBox();

    HasValue<XoLocalizedString> getDescriptionBox();

    void setNumber(int number);

    void setOptionType(PollApp.OptionType optionType);

    public interface Presenter extends BasicPresenter<OptionView> {

        /**
         * Reads the values from the UI-fields and creates a {@link XoOption}.
         * 
         * @return The {@link XoOption} with UI-field values.
         */
        XoOption getOption();

        void setNumber(int number);

        void setOptionType(PollApp.OptionType optionType);
    }
}
