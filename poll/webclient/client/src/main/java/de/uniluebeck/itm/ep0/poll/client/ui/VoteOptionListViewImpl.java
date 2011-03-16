package de.uniluebeck.itm.ep0.poll.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import de.uniluebeck.itm.ep0.poll.client.PollAppGinjector;

import java.util.Iterator;

public class VoteOptionListViewImpl extends Composite implements VoteOptionListView {

    interface VoteOptionListViewImplUiBinder extends UiBinder<Widget, VoteOptionListViewImpl> {
    }

    private static VoteOptionListViewImplUiBinder uiBinder = GWT.create(VoteOptionListViewImplUiBinder.class);

    @UiField
    Label lblOptionListName;
    @UiField
    FlexTable tblVotes;

    private PollAppGinjector injector;
    private Presenter presenter;

    @Inject
    public VoteOptionListViewImpl(final PollAppGinjector injector) {
        this.injector = injector;

        initWidget(uiBinder.createAndBindUi(this));

        setWidth("100%");
        setHeight("100%");

        tblVotes.getElement().getStyle().setProperty("margin", "3px");
        tblVotes.setCellSpacing(2);
        tblVotes.setCellPadding(2);
    }

    public String getOptionListName() {
        return lblOptionListName.getText();
    }

    public void setOptionListName(final String name) {
        lblOptionListName.setText(name);
    }

    public FlexTable getVoteTable() {
        return tblVotes;
    }

    public int getVoteTableRowCount() {
        return tblVotes.getRowCount();
    }

    public void setVoteTableWidget(int row, int column, Widget widget) {
        tblVotes.setWidget(row, column, widget);
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public void setPresenter(final Presenter presenter) {
        this.presenter = presenter;
    }
}