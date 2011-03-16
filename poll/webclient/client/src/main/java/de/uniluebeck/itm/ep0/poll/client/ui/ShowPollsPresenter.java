package de.uniluebeck.itm.ep0.poll.client.ui;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import de.uniluebeck.itm.ep0.poll.api.PollClientServiceAsync;
import de.uniluebeck.itm.ep0.poll.client.ui.util.ExceptionEvent;
import de.uniluebeck.itm.ep0.poll.client.ui.util.MessageBox;
import de.uniluebeck.itm.ep0.poll.client.ui.util.StacktraceUtil;
import de.uniluebeck.itm.ep0.poll.shared.LoadPollsResult;

public class ShowPollsPresenter implements ShowPollsView.Presenter,
        ExceptionEvent.ExceptionHandler {

    private PlaceController placeConroller;
    private ShowPollsView view;
    private PollClientServiceAsync service;
    private EventBus eventBus;

    @Inject
    public ShowPollsPresenter(final PlaceController placeController,
                              final PollClientServiceAsync service, final ShowPollsView view,
                              final EventBus eventBus) {
        this.placeConroller = placeController;
        this.service = service;
        this.view = view;
        this.eventBus = eventBus;
        bind();
    }

    private void bind() {
        this.view.setPresenter(this);
        eventBus.addHandler(ExceptionEvent.TYPE, this);
    }

    public ShowPollsView getView() {
        return view;
    }

    public void goTo(final Place place) {
        placeConroller.goTo(place);
    }

    public void loadPolls(final String url, final String languageCode) {
        loadPollsAsync(url, languageCode);
    }

    private void loadPollsAsync(final String url, final String languageCode) {
        final AsyncCallback<LoadPollsResult> callback = new AsyncCallback<LoadPollsResult>() {

            public void onFailure(final Throwable caught) {
                eventBus.fireEvent(new ExceptionEvent(caught.getMessage(),
                        StacktraceUtil.stacktraceToString(caught)));
            }

            public void onSuccess(final LoadPollsResult result) {
                view.setPollInfos(result);
            }
        };
        service.getPollInfos(url, languageCode, callback);

    }

    public void onException(final ExceptionEvent event) {
        final String title = "Error while loading polls";
        final String message = event.getMessage();
        final String stacktrace = event.getStacktrace();
        MessageBox.error(title, message, stacktrace, null);
    }

    public void searchPolls(String name) {
        final AsyncCallback<LoadPollsResult> callback = new AsyncCallback<LoadPollsResult>() {

            public void onFailure(final Throwable caught) {
                eventBus.fireEvent(new ExceptionEvent(caught.getMessage(),
                        StacktraceUtil.stacktraceToString(caught)));
            }

            public void onSuccess(final LoadPollsResult result) {
                view.setPollInfos(result);
            }
        };
        //service.findPollInfos(name, callback);

    }
}
