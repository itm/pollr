package de.uniluebeck.itm.ep0.poll.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import de.uniluebeck.itm.ep0.poll.client.ui.PollAppView;

public class PollApp implements EntryPoint {

    /**
     * This string refers to the div-element's id in <code>PollApp.html</code>.
     * All widgets will be placed inside this div.
     */
    private static final String DIV_ID = "container";
    private PollAppGinjector injector = GWT.create(PollAppGinjector.class);

    /**
     * Entry point method.
     */
    public void onModuleLoad() {
        final PollAppView pollAppView = injector.getPollAppView();

        // Start ActivityManager for the main widget with our ActivityMapper
        injector.getContentActivityManager().setDisplay(
                pollAppView.getMainPanel());

        RootPanel.get(DIV_ID).add(pollAppView.asWidget());

        // Goes to place represented on URL or default place
        injector.getPlaceHistoryHandler().handleCurrentHistory();

        hideLoadingIndicator();
    }

    /**
     * Hides the loading indicator that is shown before the app starts.
     */
    private void hideLoadingIndicator() {
        final Scheduler.ScheduledCommand command = new Scheduler.ScheduledCommand() {
            public void execute() {
                DOM.getElementById("loading").getStyle()
                        .setVisibility(Style.Visibility.HIDDEN);
            }
        };
        Scheduler.get().scheduleDeferred(command);
    }

    public static enum OptionType {
        TEXT, DATE
    }
}
