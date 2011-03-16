package de.uniluebeck.itm.ep0.poll.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.inject.Inject;

import de.uniluebeck.itm.ep0.poll.client.PollAppGinjector;
import de.uniluebeck.itm.ep0.poll.client.i18n.PollClientConstants;
import de.uniluebeck.itm.ep0.poll.client.ui.custom.LocalizableTextBox;
import de.uniluebeck.itm.ep0.poll.client.ui.custom.LocalizePresenter;
import de.uniluebeck.itm.ep0.poll.domain.XoLocalizedString;

public class AddPollViewImpl extends Composite implements AddPollView {

    private static final int CELL_SPACING = 5;
    private static final int CELL_PADDING = 3;

    private static PollClientConstants pollClientConstants = GWT.create(PollClientConstants.class);

    interface AddPollViewImplUiBinder extends UiBinder<Widget, AddPollViewImpl> {
    }

    private static AddPollViewImplUiBinder uiBinder = GWT.create(AddPollViewImplUiBinder.class);
    @UiField
    VerticalPanel pnlMain;
    @UiField
    SimplePanel pnlError;
    @UiField(provided = true)
    LocalizableTextBox tbxName;
    @UiField
    DateBox dbxValidFrom;
    @UiField
    DateBox dbxValidTo;
    @UiField
    CheckBox cbxIsPublic;
    @UiField
    Button btnCreatePoll;
    @UiField
    Button btnAddOptionList;
    @UiField
    FlexTable tblOptionLists;
    @UiField
    DisclosurePanel pnlDisclosure;
    @UiField
    Label lblCreatePoll;
    @UiField
    Label lblName;
    @UiField
    Label lblValidFrom;
    @UiField
    Label lblValidTo;


    private PollAppGinjector injector;
    private Presenter presenter;

    @Inject
    public AddPollViewImpl(final PollAppGinjector injector) {
        this.injector = injector;

        initLocalizableTextBoxPresenter();

        initWidget(uiBinder.createAndBindUi(this));

        setWidth("100%");
        setHeight("100%");

        tblOptionLists.setWidth("32em");
        tblOptionLists.setCellSpacing(CELL_SPACING);
        tblOptionLists.setCellPadding(CELL_PADDING);
        localize();
    }

    private void localize() {
        lblCreatePoll.setText(pollClientConstants.createPoll());
        lblName.setText(pollClientConstants.name());
        lblValidFrom.setText(pollClientConstants.validFrom());
        lblValidTo.setText(pollClientConstants.validTo());
        cbxIsPublic.setText(pollClientConstants.isPublic());
        btnAddOptionList.setText(pollClientConstants.addOptionList());
        btnCreatePoll.setText(pollClientConstants.savePoll());
        pnlDisclosure.setTitle(pollClientConstants.optionLists());
    }

    private void initLocalizableTextBoxPresenter() {
        final LocalizePresenter nameBoxPresenter = injector.getLocalizePresenter();
        this.tbxName = nameBoxPresenter.getView();
    }

    @UiHandler("btnCreatePoll")
    void handleCreatePoll(final ClickEvent e) {
        presenter.addPoll();
        presenter.goToShowPolls();
    }

    @UiHandler("btnAddOptionList")
    void handleAddOptionList(final ClickEvent e) {
        addOptionListView();
        pnlDisclosure.setOpen(true);
    }

    public void addErrorMessage(final String msg) {
        pnlError.add(new Label(msg));
    }

    public void removeErrorMessage() {
        final Widget lblError = pnlError.getWidget();
        if (lblError != null) {
            pnlError.remove(lblError);
        }
    }

    private void addOptionListView() {
        final int currentRow = (tblOptionLists.getRowCount() < 0) ? 0
                : tblOptionLists.getRowCount();
        final OptionListPresenter optionListPresenter = injector.getOptionListPresenter();
        optionListPresenter.setNumber(currentRow + 1);
        tblOptionLists.setWidget(currentRow, 0, optionListPresenter.getView().asWidget());
    }

    public VerticalPanel getMainPanel() {
        return pnlMain;
    }

    public SimplePanel getErrorPanel() {
        return pnlError;
    }

    public HasValue<XoLocalizedString> getNameBox() {
        return tbxName;
    }

    public DateBox getValidFromBox() {
        return dbxValidFrom;
    }

    public DateBox getValidToBox() {
        return dbxValidTo;
    }

    public CheckBox getIsPublicBox() {
        return cbxIsPublic;
    }

    public Button getCreatePollButton() {
        return btnCreatePoll;
    }

    public Button getAddOptionListButton() {
        return btnAddOptionList;
    }

    public FlexTable getOptionListsTable() {
        return tblOptionLists;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public void setPresenter(final Presenter presenter) {
        this.presenter = presenter;
    }
}
