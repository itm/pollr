package de.uniluebeck.itm.ep0.poll.client.ui;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;

import de.uniluebeck.itm.ep0.poll.domain.XoLocalizedString;
import de.uniluebeck.itm.ep0.poll.domain.XoPoll;

public interface AddPollView extends BasicView<AddPollView.Presenter>, IsWidget {

    HasWidgets getMainPanel();

    void addErrorMessage(String msg);

    void removeErrorMessage();

    HasValue<XoLocalizedString> getNameBox();

    HasValue<java.util.Date> getValidFromBox();

    HasValue<java.util.Date> getValidToBox();

    HasValue<java.lang.Boolean> getIsPublicBox();

    HasWidgets getOptionListsTable();

    public interface Presenter extends BasicPresenter<AddPollView> {

        /**
         * Reads the values from the UI-fields and creates a {@link XoPoll}.
         * 
         * @return The {@link XoPoll} with UI-field values.
         */
        XoPoll getPoll();

        /**
         * Add a {@link XoPoll} to the database via the remote service.
         */
        void addPoll();

        /*
         * Navigates back to the {@link ShowPolls} place.
         */
        void goToShowPolls();
    }
}
