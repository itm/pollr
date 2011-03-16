package de.uniluebeck.itm.ep0.poll.client.ui;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;

import de.uniluebeck.itm.ep0.poll.client.PollApp;
import de.uniluebeck.itm.ep0.poll.domain.XoLocalizedString;
import de.uniluebeck.itm.ep0.poll.domain.XoOptionList;

public interface OptionListView extends BasicView<OptionListView.Presenter>, IsWidget {

    void setNumber(int number);

    HasText getNumberLabel();

    HasValue<XoLocalizedString> getNameBox();

    HasWidgets getOptionTable();

    int getOptionTableRowCount();

    HasValue<java.lang.Boolean> getTextRadioButton();

    HasValue<java.lang.Boolean> getDateRadioButton();

    public interface Presenter extends BasicPresenter<OptionListView> {

        /**
         * Reads the values from the UI-fields and creates a
         * {@link XoOptionList}.
         *
         * @return The {@link XoOptionList} with UI-field values.
         */
        XoOptionList getOptionList();

        void setNumber(int number);

        PollApp.OptionType getOptionType();

        void setOptionType(PollApp.OptionType optionType);
    }
}
