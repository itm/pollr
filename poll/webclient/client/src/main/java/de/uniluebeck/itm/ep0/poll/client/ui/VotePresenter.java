package de.uniluebeck.itm.ep0.poll.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import de.uniluebeck.itm.ep0.poll.api.PollClientServiceAsync;
import de.uniluebeck.itm.ep0.poll.client.PollAppGinjector;
import de.uniluebeck.itm.ep0.poll.client.place.ShowPollsPlace;
import de.uniluebeck.itm.ep0.poll.client.place.VotePlace;
import de.uniluebeck.itm.ep0.poll.client.ui.util.ExceptionEvent;
import de.uniluebeck.itm.ep0.poll.client.ui.util.StacktraceUtil;
import de.uniluebeck.itm.ep0.poll.domain.XoOptionList;
import de.uniluebeck.itm.ep0.poll.domain.XoPoll;
import de.uniluebeck.itm.ep0.poll.domain.XoVote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VotePresenter implements VoteView.Presenter {

    private VoteView view;
    private EventBus eventBus;
    private PlaceController placeController;
    private VotePlace place;
    private PollClientServiceAsync service;
    private PollAppGinjector injector;
    private XoPoll poll;
    private Map<String, VoteOptionListPresenter> voteOptionListPresenters;

    @Inject
    public VotePresenter(final VoteView view,
                         final EventBus eventBus,
                         final PlaceController placeController,
                         final PollClientServiceAsync service,
                         final PollAppGinjector injector) {
        this.view = view;
        this.eventBus = eventBus;
        this.placeController = placeController;
        this.service = service;
        this.injector = injector;

        voteOptionListPresenters = new HashMap<String, VoteOptionListPresenter>();

        bind();
    }

    private void bind() {
        this.view.setPresenter(this);
    }

    public VoteView getView() {
        return view;
    }

    /*
     * Navigate to another place.
     */
    public void goToShowPollsPlace() {
        placeController.goTo(new ShowPollsPlace());
    }

    public void setPoll(final XoPoll poll) {
        this.poll = poll;
    }

    public XoPoll getPoll() {
        return poll;
    }

    public void loadPoll() {
        GWT.log("loadPoll()");

        // Set up the call back object.
        final AsyncCallback<XoPoll> callback = new AsyncCallback<XoPoll>() {

            public void onFailure(final Throwable caught) {
                eventBus.fireEvent(new ExceptionEvent(caught.getMessage(), StacktraceUtil.stacktraceToString(caught)));
            }

            public void onSuccess(final XoPoll result) {
                if (result == null) {
                    return;
                }
                setPoll(result);
                renderPoll();
            }
        };

        // Make the call to the poll-client service.
        if (null != place) {
            GWT.log("getPoll( " + place.getUuid() + " )");
            service.getPoll(place.getUuid().trim(), place.getRemoteURL(), place.getRemoteLanguageCode(), callback);
        } else {
            GWT.log("VotePresenter: place is NULL");
        }
    }

    private void renderPoll() {
        final String pollName = poll.getName().getLocalizedValue(LocaleInfo.getCurrentLocale().getLocaleName());
        view.setPollName(" \"" + pollName + "\"");

        for (XoOptionList optionList : poll.getOptionLists()) {
            final VoteOptionListPresenter voteOptionListPresenter = injector.getVoteOptionListPresenter();
            voteOptionListPresenters.put(optionList.getId(), voteOptionListPresenter);

            voteOptionListPresenter.setOptionList(optionList);
            voteOptionListPresenter.renderOptionList();

            view.addOptionListView(voteOptionListPresenter.getView());
        }
    }

    public void vote(final List<XoVote> votes) {
        // Set up the callback object.
        final AsyncCallback<Void> callback = new AsyncCallback<Void>() {

            public void onFailure(final Throwable caught) {
                eventBus.fireEvent(new ExceptionEvent(caught.getMessage(), StacktraceUtil.stacktraceToString(caught)));
            }

            public void onSuccess(final Void result) {
                // Everything is fine. Nothing more to do.
            }
        };

        // Make the call to the poll-client service.
        service.vote(votes, callback);
    }

    public void save() {
        final String voter = view.getVoter();

        if ("".equals(voter) || null == voter) return;

        List<XoVote> allVotes = new ArrayList<XoVote>();
        for (VoteOptionListPresenter voteOptionListPresenter : voteOptionListPresenters.values()) {
            final List<XoVote> optionListVotes = voteOptionListPresenter.extractVotes();
            for (XoVote v : optionListVotes) {
                v.setVoter(voter);
                allVotes.add(v);
            }
        }
        vote(allVotes); // GWT-RPC call
        goToShowPollsPlace();
    }

    public VotePlace getPlace() {
        return place;
    }

    public void setPlace(final VotePlace place) {
        this.place = place;
    }
}