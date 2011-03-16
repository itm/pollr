package de.uniluebeck.itm.ep0.poll.client.ui.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class MessageBoxViewImpl implements MessageBoxView, ClickHandler {

    private static MessageBoxViewImplUiBinder uiBinder = GWT.create(MessageBoxViewImplUiBinder.class);

    interface MessageBoxViewImplUiBinder extends UiBinder<Widget, MessageBoxViewImpl> {
    }

    private Presenter presenter;
    @UiField
    DialogBox dbxPopup;
    @UiField
    HTML message;
    @UiField
    Image image;
    @UiField
    FlexTable buttonTable;
    /*@UiField
    DisclosurePanel stacktracePanel;
    @UiField
    Label stacktraceLabel;*/

    public MessageBoxViewImpl() {
        uiBinder.createAndBindUi(this);
    }

    @UiFactory
    protected MessageBoxViewImpl createDialog() {
        return this;
    }

    public void setPresenter(final Presenter presenter) {
        this.presenter = presenter;
    }

    public String getTitle() {
        return dbxPopup.getTitle();
    }

    public String getMessage() {
        return message.getText();
    }

    public void setMessage(final String text) {
        message.setHTML(text);
    }

    public void setMessageImageUrl(final String url) {
        image.setUrl(url);
    }

    public void setButtons(final String... buttons) {
        buttonTable.clear();
        int i = 0;
        for (String label : buttons) {
            final Button button = new Button(label, this);
            button.setWidth("75px");
            buttonTable.setWidget(0, i++, button);
        }
    }

    public void hide() {
        dbxPopup.hide();
    }

    public void onClick(final ClickEvent event) {
        final Button button = (Button) event.getSource();
        presenter.buttonClicked(button.getText());
    }

    public void setCaption(final String title) {
        dbxPopup.setText(title);
    }

    public void show() {
        dbxPopup.show();
        dbxPopup.center();
    }

    public void setStacktrace(final String stacktrace) {
        //stacktraceLabel.setText(stacktrace);
    }

    public void setStacktracePanelVisible(final boolean isVisible) {
        //stacktracePanel.setVisible(isVisible);
    }

    public Widget asWidget() {
        return dbxPopup;
    }
}
