package de.uniluebeck.itm.ep0.poll.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.inject.Inject;

import de.uniluebeck.itm.ep0.poll.client.PollApp;
import de.uniluebeck.itm.ep0.poll.client.PollAppGinjector;
import de.uniluebeck.itm.ep0.poll.client.i18n.PollClientConstants;
import de.uniluebeck.itm.ep0.poll.client.ui.custom.LocalizableTextBox;
import de.uniluebeck.itm.ep0.poll.client.ui.custom.LocalizePresenter;

public class OptionViewImpl extends Composite implements OptionView {

    interface OptionViewUiBinder extends UiBinder<Widget, OptionViewImpl> {
    }

    private static OptionViewUiBinder uiBinder = GWT
            .create(OptionViewUiBinder.class);
    private static PollClientConstants pollClientConstants = GWT.create(PollClientConstants.class);

    @UiField
    Label lblNumber;
    @UiField
    Label lblText;
    @UiField
    Label lblDate;
    @UiField(provided = true)
    LocalizableTextBox tbxText;
    @UiField
    DateBox dbxDate;
    @UiField(provided = true)
    LocalizableTextBox tbxDescription;
    @UiField
    Label lblDescription;
    
    
    private PollAppGinjector injector;
    private Presenter presenter;

    @Inject
    public OptionViewImpl(final PollAppGinjector injector) {
        this.injector = injector;
        
        initPresenter();
        
        initWidget(uiBinder.createAndBindUi(this));

        setWidth("100%");
        setHeight("100%");

        // Default option type
        setOptionType(PollApp.OptionType.TEXT); 
        localize();
    }

    private void localize() {
        lblText.setText(pollClientConstants.text());
        lblDate.setText(pollClientConstants.date());
        lblDescription.setText(pollClientConstants.description());
    }

    private void initPresenter() {
        final LocalizePresenter textBoxPresenter = injector.getLocalizePresenter();
        this.tbxText = textBoxPresenter.getView();        
        
        final LocalizePresenter descriptionBoxPresenter = injector.getLocalizePresenter();
        this.tbxDescription = descriptionBoxPresenter.getView();               
    }    
    
    public void setNumber(final int number) {
        lblNumber.setText("Option #" + number);
    }

    public void setOptionType(final PollApp.OptionType optionType) {
        if (optionType == PollApp.OptionType.TEXT) {
            lblText.setVisible(true);
            tbxText.setVisible(true);
            lblDate.setVisible(false);
            dbxDate.setVisible(false);

        } else if (optionType == PollApp.OptionType.DATE) {
            lblText.setVisible(false);
            tbxText.setVisible(false);
            lblDate.setVisible(true);
            dbxDate.setVisible(true);
        }
    }

    public Label getNumberLabel() {
        return lblNumber;
    }

    public Label getTextLabel() {
        return lblText;
    }

    public Label getDateLabel() {
        return lblDate;
    }

    public LocalizableTextBox getTextBox() {
        return tbxText;
    }

    public DateBox getDateBox() {
        return dbxDate;
    }

    public LocalizableTextBox getDescriptionBox() {
        return tbxDescription;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public void setPresenter(final Presenter presenter) {
        this.presenter = presenter;
    }
}
