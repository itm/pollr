package de.uniluebeck.itm.ep0.poll.client.ui.custom;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.uniluebeck.itm.ep0.poll.client.i18n.PollClientConstants;
import de.uniluebeck.itm.ep0.poll.client.ui.BasicView;

public class LocalizePopUp implements BasicView<LocalizePresenter> {

    interface LocalizePopUpUiBinder extends UiBinder<Widget, LocalizePopUp> {
    }

    private static LocalizePopUpUiBinder uiBinder = GWT.create(LocalizePopUpUiBinder.class);
    private static PollClientConstants pollClientConstants = GWT.create(PollClientConstants.class);

    @UiField
    DecoratedPopupPanel dbxPopup;
    @UiField
    FlexTable tblLanguages;
    @UiField
    Button btnAdd;
    @UiField
    Button btnDone;
    private LocalizePresenter presenter;

    public LocalizePopUp() {
        uiBinder.createAndBindUi(this);

        final Label lblLanguage = new Label("Language");
        tblLanguages.setWidget(0, 0, lblLanguage);

        final Label lblValue = new Label("Value");
        tblLanguages.setWidget(0, 1, lblValue);
        localize();
    }

    private void localize() {
        btnAdd.setText(pollClientConstants.newLocalization());
        btnDone.setText(pollClientConstants.done());

    }

    public FlexTable getLanguageTable() {
        return tblLanguages;
    }


    public void show() {
        dbxPopup.show();
    }

    public void setPosition(final int x, final int y) {
        dbxPopup.setPopupPosition(x, y);
    }

    @UiHandler("btnAdd")
    public void handleAddClick(final ClickEvent e) {
        presenter.addRow();
    }

    @UiHandler("btnDone")
    public void handleDoneClick(final ClickEvent e) {
        presenter.updateObject();
        dbxPopup.hide();
    }

    public LocalizePresenter getPresenter() {
        return presenter;
    }

    public void setPresenter(LocalizePresenter presenter) {
        this.presenter = presenter;
    }
}
