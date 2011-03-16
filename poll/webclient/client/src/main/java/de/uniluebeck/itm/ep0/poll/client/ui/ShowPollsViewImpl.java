package de.uniluebeck.itm.ep0.poll.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.uniluebeck.itm.ep0.poll.client.i18n.PollClientConstants;
import de.uniluebeck.itm.ep0.poll.client.place.AddPollPlace;
import de.uniluebeck.itm.ep0.poll.domain.XoPollInfo;
import de.uniluebeck.itm.ep0.poll.shared.LoadPollsResult;
import de.uniluebeck.itm.ep0.poll.util.PollUtil;

public class ShowPollsViewImpl extends Composite implements ShowPollsView {

    private static ShowPollsViewImplUiBinder uiBinder = GWT
            .create(ShowPollsViewImplUiBinder.class);

    private static PollClientConstants pollClientConstants = GWT.create(PollClientConstants.class);

    interface ShowPollsViewImplUiBinder extends
            UiBinder<Widget, ShowPollsViewImpl> {
    }

    @UiField
    FlexTable tblLocalPolls;
    @UiField
    FlexTable tblRemotePolls;
    private ShowPollsPresenter presenter;
    @UiField
    TextBox tbxRemotePollsURL;
    @UiField
    TextBox tbxRemoteLanguageCode;
    @UiField
    Button btnAddPoll;
    @UiField
    Label lblLocalPolls;
    @UiField
    Label lblRemotePolls;
    @UiField
    Label lblRemoteURL;
    @UiField
    Button btnLoadLocalPolls;
    @UiField
    Label lblRemoteLanguageCode;
    @UiField
    Button btnLoadRemotePolls;
    @UiField
    Label lblPollName;
    @UiField
    TextBox tbxPollName;


    public ShowPollsViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        localize();
    }

    private void localize() {
        btnAddPoll.setText(pollClientConstants.addPoll());
        tblLocalPolls.setWidget(0, 0, new Label(pollClientConstants.pollTitle()));
        tblRemotePolls.setWidget(0, 0, new Label(pollClientConstants.pollTitle()));
        lblLocalPolls.setText(pollClientConstants.localPolls());
        lblRemotePolls.setText(pollClientConstants.remotePolls());
        lblRemoteURL.setText(pollClientConstants.remoteURL());
        btnLoadLocalPolls.setText(pollClientConstants.loadLocalPolls());
        lblRemoteLanguageCode.setText(pollClientConstants.remoteLanguageCode());
        btnLoadRemotePolls.setText(pollClientConstants.loadRemotePolls());
    }

    public void setPollInfos(LoadPollsResult pollInfos) {

        FlexTable pollTable;
        String remoteURL = "&remoteURL=";
        String remoteLanguageCode = "&remoteLanguageCode=";

        if (Boolean.TRUE.equals(pollInfos.getIsRemote())) {
            pollTable = tblRemotePolls;
            remoteURL = remoteURL + pollInfos.getRemotePollSystemURL();
            remoteLanguageCode = remoteLanguageCode + pollInfos.getRemoteLanguageCode();
        } else {
            pollTable = tblLocalPolls;
        }

        pollTable.clear();
        pollTable.removeAllRows();
        pollTable.setWidget(0, 0, new Label("Poll Title"));

        for (XoPollInfo xoPollInfo : pollInfos.getPollInfos()) {
            final SafeHtml caption = SafeHtmlUtils.fromString(pollClientConstants.showAndVote());
            final int rowNum = pollTable.getRowCount();
            pollTable.setWidget(
                    rowNum,
                    0,
                    new Label(xoPollInfo.getName().getLocalizedValue(
                            LocaleInfo.getCurrentLocale().getLocaleName())));
            pollTable.setWidget(rowNum, 1, new Hyperlink(caption,
                    "VotePlace:" + "pollUuid=" + xoPollInfo.getUuid() + remoteURL
                            + remoteLanguageCode));
        }
    }

    public ShowPollsPresenter getPresenter() {
        return presenter;
    }

    public void setPresenter(ShowPollsPresenter presenter) {
        this.presenter = presenter;
    }

    @UiHandler("btnAddPoll")
    void onClickAddPoll(final ClickEvent e) {
        final AddPollPlace place = new AddPollPlace();
        presenter.goTo(place);
    }

    @UiHandler("btnLoadLocalPolls")
    void onClickLoadLocalPolls(final ClickEvent e) {
        if (PollUtil.nullOrEmpty(tbxPollName.getText())) {
            presenter.loadPolls("", LocaleInfo.getCurrentLocale().getLocaleName());
        } else {
            presenter.searchPolls(tbxPollName.getText());
        }
    }

    @UiHandler("btnLoadRemotePolls")
    void onClickLoadRemotePolls(final ClickEvent e) {
        presenter.loadPolls(tbxRemotePollsURL.getValue(), tbxRemoteLanguageCode.getValue());
    }
}
