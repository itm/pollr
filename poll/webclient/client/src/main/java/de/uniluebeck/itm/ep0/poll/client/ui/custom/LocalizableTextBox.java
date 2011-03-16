package de.uniluebeck.itm.ep0.poll.client.ui.custom;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.uniluebeck.itm.ep0.poll.client.ui.BasicView;
import de.uniluebeck.itm.ep0.poll.domain.XoLocalizedString;

public class LocalizableTextBox extends Composite implements
        HasValue<XoLocalizedString>, BasicView<LocalizePresenter> {

    interface LocalizeableTextBoxUiBinder extends
            UiBinder<Widget, LocalizableTextBox> {
    }

    private static LocalizeableTextBoxUiBinder uiBinder = GWT.create(LocalizeableTextBoxUiBinder.class);
    @UiField
    PushButton btnLocalize;
    @UiField
    TextBox tbxValue;
    private LocalizePresenter presenter;
    private XoLocalizedString value;

    public LocalizableTextBox() {
        initWidget(uiBinder.createAndBindUi(this));

        // Set default value
        this.value = new XoLocalizedString();
    }

    public String getBoxText() {
        return tbxValue.getText();
    }

    public void setBoxText(final String text) {
        tbxValue.setText(text);
    }

    public TextBox getTextBox() {
        return tbxValue;
    }

    public HandlerRegistration addValueChangeHandler(
            final ValueChangeHandler<XoLocalizedString> handler) {
        // TODO Auto-generated method stub
        return null;
    }

    public XoLocalizedString getValue() {
        return value;
    }

    public void setValue(final XoLocalizedString value) {
        if (null == value) {
            return;
        }

        String language = LocaleInfo.getCurrentLocale().getLocaleName();
        if ("default".equals(language)) {
            language = "en";
        }
        final int i = language.indexOf("_");
        if (i >= 0) {
            language = language.substring(0, i);
        }

        tbxValue.setValue(value.getLocalizedValue(language));
        this.value = value;
    }

    public void setValue(final XoLocalizedString value, final boolean fireEvents) {
        setValue(value);
        // TODO Do something with fireEvents
    }

    @UiHandler("btnLocalize")
    public void handleLocalizeClick(final ClickEvent e) {
        presenter.getLanguages();

        presenter.showPopUp();
    }

    public void setPresenter(final LocalizePresenter presenter) {
        this.presenter = presenter;
    }

    public LocalizePresenter getPresenter() {
        return presenter;
    }
}
