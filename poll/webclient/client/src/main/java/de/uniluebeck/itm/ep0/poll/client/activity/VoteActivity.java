package de.uniluebeck.itm.ep0.poll.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import de.uniluebeck.itm.ep0.poll.client.place.VotePlace;
import de.uniluebeck.itm.ep0.poll.client.ui.VotePresenter;

public class VoteActivity extends AbstractActivity {

    private VotePresenter presenter;
    private VotePlace place;

    @Inject
    public VoteActivity(final VotePresenter presenter/*, final VotePlace place*/) {
        this.presenter = presenter;
        //this.place = place;
    }

    public void start(final AcceptsOneWidget container, final EventBus eventBus) {
        presenter.setPlace(place);
        presenter.loadPoll();
        container.setWidget(presenter.getView().asWidget());
    }

    public void setPlace(final VotePlace place) {
        this.place = place;
    }
}