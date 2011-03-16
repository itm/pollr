package de.uniluebeck.itm.ep0.poll.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import de.uniluebeck.itm.ep0.poll.client.PollApp;
import de.uniluebeck.itm.ep0.poll.client.PollAppGinjector;
import de.uniluebeck.itm.ep0.poll.client.i18n.PollClientConstants;
import de.uniluebeck.itm.ep0.poll.client.ui.custom.LocalizableTextBox;
import de.uniluebeck.itm.ep0.poll.client.ui.custom.LocalizePresenter;

public class OptionListViewImpl extends Composite implements OptionListView {
    
    interface OptionListWidgetUiBinder extends
            UiBinder<Widget, OptionListViewImpl> {
    }
    private static OptionListWidgetUiBinder uiBinder = GWT.create(OptionListWidgetUiBinder.class);
    private static PollClientConstants pollClientConstants = GWT.create(PollClientConstants.class);
    
    @UiField
    Label lblNumber;
    @UiField
    RadioButton rbnText;
    @UiField
    RadioButton rbnDate;
    @UiField(provided = true)
    LocalizableTextBox tbxName;
    @UiField
    FlexTable tblOptions;
    @UiField
    Button btnAddOption;
    @UiField
    DisclosurePanel pnlDisclosure;
    @UiField
    Label lblName;
    
    
    private PollAppGinjector injector;
    private Presenter presenter;
    
    @Inject
    public OptionListViewImpl(final PollAppGinjector injector) {
        this.injector = injector;       
                
        initPresenter();
        
        initWidget(uiBinder.createAndBindUi(this));
        
        setWidth("100%");
        setHeight("100%");   
        localize();
    }
    
    private void localize() {
        lblName.setText(pollClientConstants.name());
        rbnText.setText(pollClientConstants.text());
        rbnDate.setText(pollClientConstants.date());
        btnAddOption.setText(pollClientConstants.addOption());
        pnlDisclosure.setTitle(pollClientConstants.options());
    }

    private void initPresenter() {
        final LocalizePresenter nameBoxPresenter = injector.getLocalizePresenter();
        this.tbxName = nameBoxPresenter.getView();        
    }
    
    public void setNumber(final int number) {
        lblNumber.setText("Option-List #" + number);
    }
    
    public Label getNumberLabel() {
        return lblNumber;
    }
    
    public LocalizableTextBox getNameBox() {
        return tbxName;
    }
    
    public FlexTable getOptionTable() {
        return tblOptions;
    }
    
    public int getOptionTableRowCount() {
        return tblOptions.getRowCount();
    }
    
    public RadioButton getTextRadioButton() {
        return rbnText;
    }
    
    public RadioButton getDateRadioButton() {
        return rbnDate;
    }
    
    public Presenter getPresenter() {
        return presenter;
    }
    
    public void setPresenter(final Presenter presenter) {
        this.presenter = presenter;
    }
    
    @UiHandler("btnAddOption")
    void onAddOptionClick(final ClickEvent event) {
        addOptionView();
        pnlDisclosure.setOpen(true);
    }
    
    @UiHandler("rbnText")
    void handleTextChoice(final ClickEvent e) {
        presenter.setOptionType(PollApp.OptionType.TEXT);
    }
    
    @UiHandler("rbnDate")
    void handleDateChoice(final ClickEvent e) {
        presenter.setOptionType(PollApp.OptionType.DATE);
    }
    
    private void addOptionView() {
        final int currentCell = (tblOptions.getRowCount() <= 0 || tblOptions.getCellCount(0) < 0) ? 0 : tblOptions.getCellCount(0);
        final OptionPresenter optionPresenter = injector.getOptionPresenter();
        optionPresenter.setNumber(currentCell + 1);
        optionPresenter.setOptionType(presenter.getOptionType());
        tblOptions.setWidget(0, currentCell, optionPresenter.getView().asWidget());
    }
}
