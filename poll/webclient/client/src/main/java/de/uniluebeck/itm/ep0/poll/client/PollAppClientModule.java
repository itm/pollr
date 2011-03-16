package de.uniluebeck.itm.ep0.poll.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import de.uniluebeck.itm.ep0.poll.client.mvp.PollAppPlaceHistoryMapper;
import de.uniluebeck.itm.ep0.poll.client.place.ShowPollsPlace;
import de.uniluebeck.itm.ep0.poll.client.ui.AddPollView;
import de.uniluebeck.itm.ep0.poll.client.ui.AddPollViewImpl;
import de.uniluebeck.itm.ep0.poll.client.ui.OptionListView;
import de.uniluebeck.itm.ep0.poll.client.ui.OptionListViewImpl;
import de.uniluebeck.itm.ep0.poll.client.ui.OptionView;
import de.uniluebeck.itm.ep0.poll.client.ui.OptionViewImpl;
import de.uniluebeck.itm.ep0.poll.client.ui.PollAppView;
import de.uniluebeck.itm.ep0.poll.client.ui.PollAppViewImpl;
import de.uniluebeck.itm.ep0.poll.client.ui.ShowPollsView;
import de.uniluebeck.itm.ep0.poll.client.ui.ShowPollsViewImpl;
import de.uniluebeck.itm.ep0.poll.client.ui.VoteOptionListView;
import de.uniluebeck.itm.ep0.poll.client.ui.VoteOptionListViewImpl;
import de.uniluebeck.itm.ep0.poll.client.ui.VoteView;
import de.uniluebeck.itm.ep0.poll.client.ui.VoteViewImpl;

public class PollAppClientModule extends AbstractGinModule {

    protected void configure() {
        /* View bindings */
        bind(PollAppView.class).to(PollAppViewImpl.class).in(Singleton.class);
        bind(AddPollView.class).to(AddPollViewImpl.class).in(Singleton.class);
        bind(OptionListView.class).to(OptionListViewImpl.class);
        bind(OptionView.class).to(OptionViewImpl.class);
        bind(ShowPollsView.class).to(ShowPollsViewImpl.class).in(Singleton.class);
        bind(VoteView.class).to(VoteViewImpl.class);
        bind(VoteOptionListView.class).to(VoteOptionListViewImpl.class);
    }

    @Singleton
    @Provides
    EventBus provideEventBus() {
        return new SimpleEventBus();
    }

    @Singleton
    @Provides
    PlaceHistoryHandler providePlaceHistoryHandler(
            final PollAppPlaceHistoryMapper mapper,
            final PlaceController placeController, final EventBus eventBus) {
        final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(
                mapper);
        historyHandler.register(placeController, eventBus, new ShowPollsPlace());
        return historyHandler;
    }

    @Singleton
    @Provides
    PlaceController providePlaceController(final EventBus eventBus) {
        return new PlaceController(eventBus);
    }
}
