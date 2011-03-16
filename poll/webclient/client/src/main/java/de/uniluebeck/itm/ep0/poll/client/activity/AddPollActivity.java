package de.uniluebeck.itm.ep0.poll.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import de.uniluebeck.itm.ep0.poll.client.place.AddPollPlace;
import de.uniluebeck.itm.ep0.poll.client.ui.AddPollPresenter;

public class AddPollActivity extends AbstractActivity {

    private AddPollPresenter presenter;
    private AddPollPlace place;

    @Inject
    public AddPollActivity(final AddPollPresenter addPollPresenter) {
        this.presenter = addPollPresenter;
    }

    public void start(final AcceptsOneWidget container, final EventBus eventBus) {
        container.setWidget(presenter.getView().asWidget());
    }

    public void setPlace(final AddPollPlace place) {
        this.place = place;
    }

    @Override
    public String mayStop() {
        return "Warning: Unsaved changes will be lost if you leave this page.";
    }
}