package de.uniluebeck.itm.ep0.poll.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;

import de.uniluebeck.itm.ep0.poll.client.activity.AddPollActivity;
import de.uniluebeck.itm.ep0.poll.client.activity.ShowPollsActivity;
import de.uniluebeck.itm.ep0.poll.client.activity.VoteActivity;
import de.uniluebeck.itm.ep0.poll.client.mvp.ContentActivityManager;
import de.uniluebeck.itm.ep0.poll.client.ui.AddPollPresenter;
import de.uniluebeck.itm.ep0.poll.client.ui.AddPollView;
import de.uniluebeck.itm.ep0.poll.client.ui.OptionListPresenter;
import de.uniluebeck.itm.ep0.poll.client.ui.OptionPresenter;
import de.uniluebeck.itm.ep0.poll.client.ui.PollAppView;
import de.uniluebeck.itm.ep0.poll.client.ui.ShowPollsPresenter;
import de.uniluebeck.itm.ep0.poll.client.ui.ShowPollsView;
import de.uniluebeck.itm.ep0.poll.client.ui.VoteOptionListPresenter;
import de.uniluebeck.itm.ep0.poll.client.ui.VotePresenter;
import de.uniluebeck.itm.ep0.poll.client.ui.VoteView;
import de.uniluebeck.itm.ep0.poll.client.ui.custom.LocalizePresenter;

@GinModules(PollAppClientModule.class)
public interface PollAppGinjector extends Ginjector {

    EventBus getEventBus();

    /* 
     * Activities 
     */
    ContentActivityManager getContentActivityManager();

    AddPollActivity getAddPollActivity();
    
    ShowPollsActivity getShowPollsActivity();
    
    VoteActivity getVoteActivity();


    /*
     * Places
     */
    PlaceHistoryHandler getPlaceHistoryHandler();

    PlaceController getPlaceController();
    

    /* 
     * Views 
     */
    PollAppView getPollAppView();

    AddPollView getAddPollView();    
    
    ShowPollsView getShowPollsView();
    
    VoteView getVoteView();
    
    /*
     * Presenter
     */
    AddPollPresenter getAddPollPresenter();
    
    OptionListPresenter getOptionListPresenter();
    
    OptionPresenter getOptionPresenter();
    
    ShowPollsPresenter getShowPollsPresenter();
    
    LocalizePresenter getLocalizePresenter();
    
    VotePresenter getVotePresenter();
    
    VoteOptionListPresenter getVoteOptionListPresenter();
}
