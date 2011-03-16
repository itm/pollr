package de.uniluebeck.itm.ep0.poll.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import de.uniluebeck.itm.ep0.poll.api.PollClientServiceAsync;
import de.uniluebeck.itm.ep0.poll.client.ui.util.ExceptionEvent;
import de.uniluebeck.itm.ep0.poll.client.ui.util.StacktraceUtil;
import de.uniluebeck.itm.ep0.poll.domain.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class VoteOptionListPresenter implements VoteOptionListView.Presenter {

    private VoteOptionListView view;
    private EventBus eventBus;
    private PlaceController placeController;
    private PollClientServiceAsync service;
    private XoOptionList optionList;
    private Map<String, List<XoVote>> votes;

    /* Create image bundle */
    private PollImageBundle images = GWT.create(PollImageBundle.class);

    @Inject
    public VoteOptionListPresenter(final VoteOptionListView view,
                                   final EventBus eventBus,
                                   final PlaceController placeController,
                                   final PollClientServiceAsync service) {
        this.view = view;
        this.eventBus = eventBus;
        this.placeController = placeController;
        this.service = service;

        bind();
    }

    private void bind() {
        this.view.setPresenter(this);
    }

    public VoteOptionListView getView() {
        return view;
    }

    public XoOptionList getOptionList() {
        return optionList;
    }

    public void setOptionList(final XoOptionList optionList) {
        this.optionList = optionList;
    }

    public Map<String, List<XoVote>> getVotes() {
        return votes;
    }

    public void setVotes(Map<String, List<XoVote>> votes) {
        this.votes = votes;
    }

    public void renderOptionList() {
        final String optionListName = optionList.getName().
                getLocalizedValue(LocaleInfo.getCurrentLocale().getLocaleName());

        view.setOptionListName(optionListName);

        initHeader();
    }

    public void initHeader() {
        int currentRow = (view.getVoteTableRowCount() < 0) ? 0 : view.getVoteTableRowCount();
        int currentCell = 1;

        /* Header */
        for (XoOption option : optionList.getOptions()) {
            Label lblOptionName = null;
            if (option instanceof XoTextOption) {
                lblOptionName = new Label(((XoTextOption) option).getText().
                        getLocalizedValue(LocaleInfo.getCurrentLocale().getLocaleName()));
            } else if (option instanceof XoDateOption) {
                Date d = ((XoDateOption) option).getDateOption();
                DateTimeFormat df =DateTimeFormat.getFormat(PredefinedFormat.DATE_MEDIUM);
                lblOptionName = new Label(df.format(d));
            }
            view.setVoteTableWidget(currentRow, currentCell++, lblOptionName);
        }

        /* Votes */
        loadVotes(optionList.getId());
    }

    public void initVotes() {
        for (String voter : votes.keySet()) {
            int currentRow = (view.getVoteTableRowCount() < 0) ? 0 : view.getVoteTableRowCount();
            int currentCell = 1;
            final Label lblVoterName = new Label(voter);

            for (XoOption option : optionList.getOptions()) {
                boolean yes = false;
                for (XoVote vote : votes.get(voter)) {
                    if (option.getId().equals(vote.getOptionId())) {
                        yes = true;
                    }
                }
                if (yes) {
                    view.setVoteTableWidget(currentRow, currentCell++, new Image(images.yes()));
                } else {
                    view.setVoteTableWidget(currentRow, currentCell++, new Image(images.no()));
                }
            }
            view.setVoteTableWidget(currentRow++, currentCell++, lblVoterName);
        }
    }

    public void addVoteRow() {
        final int currentRow = (view.getVoteTableRowCount() < 0) ? 0 : view.getVoteTableRowCount();
        int currentCell = 1;

        for (XoOption option : optionList.getOptions()) {
            final CheckBox cbxOption = new CheckBox();
            cbxOption.setName(option.getId().toString());
            view.setVoteTableWidget(currentRow, currentCell++, cbxOption);
        }
    }

    public List<XoVote> extractVotes() {
        List<XoVote> votes = new ArrayList<XoVote>();

        for (final Iterator<Widget> i = view.getVoteTable().iterator(); i.hasNext();) {
            final Widget widget = i.next();
            final XoVote vote = new XoVote();
            if (widget instanceof CheckBox) {
                final String optionId = ((CheckBox) widget).getName();
                final boolean checked = ((CheckBox) widget).getValue();
                GWT.log("optionId: " + optionId.toString() + ", value: " + checked);
                if (checked) {
                    vote.setOptionId(optionId);
                    votes.add(vote);
                }
            }
        }

        return votes;
    }

    public void loadVotes(String optionListId) {
        GWT.log("loadVotes( optionListId:" + optionListId + " )");

        // Set up the callback object.
        final AsyncCallback<Map<String, List<XoVote>>> callback = new AsyncCallback<Map<String, List<XoVote>>>() {

            public void onFailure(final Throwable caught) {
                eventBus.fireEvent(new ExceptionEvent(caught.getMessage(), StacktraceUtil.stacktraceToString(caught)));
            }

            public void onSuccess(final Map<String, List<XoVote>> result) {
                setVotes(result);
                initVotes();
                addVoteRow();
            }
        };

        // Make the call to the poll-client service.
        service.getVotesForOptionList(Integer.parseInt(optionListId), callback);
    }
}