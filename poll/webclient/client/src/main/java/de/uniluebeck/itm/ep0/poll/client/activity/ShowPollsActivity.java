package de.uniluebeck.itm.ep0.poll.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import de.uniluebeck.itm.ep0.poll.client.place.ShowPollsPlace;
import de.uniluebeck.itm.ep0.poll.client.ui.ShowPollsPresenter;
import de.uniluebeck.itm.ep0.poll.client.ui.ShowPollsView;

public class ShowPollsActivity extends AbstractActivity {

    private ShowPollsView view;
    private ShowPollsPresenter presenter;
    private ShowPollsPlace place;

    @Inject
    public ShowPollsActivity(final ShowPollsView showPollsview, final ShowPollsPresenter showPollsPresenter) {
        view = showPollsview;
        presenter = showPollsPresenter;
    }

    public void start(final AcceptsOneWidget containerWidget, final EventBus eventBus) {
        view.setPresenter(presenter);
        containerWidget.setWidget(view.asWidget());
    }

    public void setPlace(final ShowPollsPlace place) {
        this.place = place;
    }

    /**
     * Ask user before stopping this activity
     */
    @Override
    public String mayStop() {
        return null;
    }
}
