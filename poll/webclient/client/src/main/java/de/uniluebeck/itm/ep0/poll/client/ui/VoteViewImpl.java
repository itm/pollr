package de.uniluebeck.itm.ep0.poll.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import de.uniluebeck.itm.ep0.poll.client.PollAppGinjector;
import de.uniluebeck.itm.ep0.poll.client.i18n.PollClientConstants;

public class VoteViewImpl extends Composite implements VoteView {

    interface VoteViewImplUiBinder extends UiBinder<Widget, VoteViewImpl> {
    }

    private static VoteViewImplUiBinder uiBinder = GWT.create(VoteViewImplUiBinder.class);
    private static PollClientConstants pollClientConstants = GWT.create(PollClientConstants.class);
    
    @UiField
    Label lblPollName;
    @UiField
    FlexTable tblOptionLists;
    @UiField
    Label lblVoterName;
    @UiField
    TextBox tbxVoterName;
    @UiField
    Button btnSave;
    @UiField
    Label lblPoll;

    private PollAppGinjector injector;
    private Presenter presenter;

    @Inject
    public VoteViewImpl(final PollAppGinjector injector) {
        this.injector = injector;

        initWidget(uiBinder.createAndBindUi(this));

        localize();
        
    }

    private void localize() {
        lblVoterName.setText(pollClientConstants.yourName());
        lblPoll.setText(pollClientConstants.poll());
        btnSave.setText(pollClientConstants.done());
        
    }

    public void addOptionListView(final VoteOptionListView voteOptionListView) {
        final int currentRow = (tblOptionLists.getRowCount() < 0) ? 0 : tblOptionLists.getRowCount();
        tblOptionLists.setWidget(currentRow, 0, voteOptionListView.asWidget());
    }

    public void setPollName(final String pollName) {
        lblPollName.setText(pollName);
    }

    public String getPollName() {
        return lblPollName.getText().trim();
    }

    public String getVoter() {
        return tbxVoterName.getText().trim();
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public void setPresenter(final Presenter presenter) {
        this.presenter = presenter;
    }

    @UiHandler("btnSave")
    void handleSave(final ClickEvent e) {
        presenter.save();
    }
}