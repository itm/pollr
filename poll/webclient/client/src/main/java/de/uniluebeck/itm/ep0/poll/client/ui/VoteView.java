package de.uniluebeck.itm.ep0.poll.client.ui;

import com.google.gwt.user.client.ui.IsWidget;
import de.uniluebeck.itm.ep0.poll.domain.XoPoll;

public interface VoteView extends BasicView<VoteView.Presenter>, IsWidget {

    void setPollName(String pollName);

    String getPollName();

    String getVoter();

    void addOptionListView(final VoteOptionListView voteOptionListView);

    public interface Presenter extends BasicPresenter<VoteView> {

        void setPoll(XoPoll poll);

        XoPoll getPoll();

        void loadPoll();

        void save();

        /*
         * Navigates back to the {@link ShowPollsPlace} place.
         */
        void goToShowPollsPlace();
    }
}