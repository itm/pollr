package de.uniluebeck.itm.ep0.poll.client.ui;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import de.uniluebeck.itm.ep0.poll.api.PollClientServiceAsync;
import de.uniluebeck.itm.ep0.poll.client.place.ShowPollsPlace;
import de.uniluebeck.itm.ep0.poll.client.ui.util.ExceptionEvent;
import de.uniluebeck.itm.ep0.poll.client.ui.util.MessageBox;
import de.uniluebeck.itm.ep0.poll.client.ui.util.StacktraceUtil;
import de.uniluebeck.itm.ep0.poll.domain.XoLocalizedString;
import de.uniluebeck.itm.ep0.poll.domain.XoOptionList;
import de.uniluebeck.itm.ep0.poll.domain.XoPoll;

import java.util.Date;
import java.util.Iterator;

public class AddPollPresenter implements AddPollView.Presenter, ExceptionEvent.ExceptionHandler {

    private AddPollView view;
    private PollClientServiceAsync service;
    private EventBus eventBus;
    private PlaceController placeController;

    @Inject
    public AddPollPresenter(final AddPollView view,
            final PollClientServiceAsync service,
            final EventBus eventBus,
            final PlaceController placeController) {
        this.view = view;
        this.service = service;
        this.eventBus = eventBus;
        this.placeController = placeController;

        bind();
    }

    private void bind() {
        this.view.setPresenter(this);
        eventBus.addHandler(ExceptionEvent.TYPE, this);
    }

    public XoPoll getPoll() {
        final XoLocalizedString name = view.getNameBox().getValue();
        final Date validFrom = view.getValidFromBox().getValue();
        final Date validTo = view.getValidToBox().getValue();
        final Boolean isPublic = view.getIsPublicBox().getValue();

        final XoPoll xoPoll = new XoPoll(name);
        xoPoll.setValidFrom(validFrom);
        xoPoll.setValidTo(validTo);
        xoPoll.setIsPublic(isPublic);

        OptionListView.Presenter optionListPresenter;
        XoOptionList xoOptionList;
        for (final Iterator<Widget> i = view.getOptionListsTable().iterator(); i.hasNext();) {
            optionListPresenter = ((OptionListView) i.next()).getPresenter();
            xoOptionList = optionListPresenter.getOptionList();
            if (null != xoOptionList) {
                xoPoll.getOptionLists().add(xoOptionList);
            }
        }

        return xoPoll;
    }

    public void addPoll() {
        // Set up the callback object.
        final AsyncCallback<XoPoll> callback = new AsyncCallback<XoPoll>()  {

            public void onFailure(final Throwable caught) {
                eventBus.fireEvent(new ExceptionEvent(caught.getMessage(), StacktraceUtil.stacktraceToString(caught)));
            }

            public void onSuccess(final XoPoll result) {
            }
        };

        // Make the call to the poll-client service.
        service.addPoll(getPoll(), callback);
    }

    public AddPollView getView() {
        return view;
    }

    public void goToShowPolls() {
        placeController.goTo(new ShowPollsPlace());
    }    
    
    public void onException(final ExceptionEvent event) {
        final String title = "Error while loading languages";
        final String message = event.getMessage();
        final String stacktrace = event.getStacktrace();
        MessageBox.error(title, message, stacktrace, null);
    }
}
