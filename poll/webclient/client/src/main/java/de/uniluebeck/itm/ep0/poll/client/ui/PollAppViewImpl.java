package de.uniluebeck.itm.ep0.poll.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import de.uniluebeck.itm.ep0.poll.client.i18n.PollClientConstants;

public class PollAppViewImpl extends Composite implements PollAppView {

	interface PollAppViewImplUiBinder extends UiBinder<Widget, PollAppViewImpl> {
	}

	private static PollAppViewImplUiBinder uiBinder = GWT
			.create(PollAppViewImplUiBinder.class);
	private static PollClientConstants pollClientConstants = GWT.create(PollClientConstants.class);
	@UiField
	HTMLPanel pnlHeader;
	@UiField
	SimplePanel pnlMain;
	@UiField
	ListBox lbLanguages;
	@UiField
	Label lblLanguages;
	
	public PollAppViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		lblLanguages.setText(pollClientConstants.selectLanguage());
		initializeLocaleBox();
		setWidth("100%");
		setHeight("100%");
	}

	private void initializeLocaleBox() {
		String currentLocale = LocaleInfo.getCurrentLocale().getLocaleName();
		if (currentLocale.equals("default")) {
			currentLocale = "en";
		}
		String[] localeNames = LocaleInfo.getAvailableLocaleNames();
		for (String localeName : localeNames) {
			if (!localeName.equals("default")) {
				String nativeName = LocaleInfo
						.getLocaleNativeDisplayName(localeName);
				lbLanguages.addItem(nativeName, localeName);
				if (localeName.equals(currentLocale)) {
					lbLanguages
							.setSelectedIndex(lbLanguages.getItemCount() - 1);
				}
			}
		}
		lbLanguages.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				String localeName = lbLanguages.getValue(lbLanguages
						.getSelectedIndex());
				UrlBuilder builder = Location.createUrlBuilder().setParameter(
						"locale", localeName);
				Window.Location.replace(builder.buildString());
			}
		});
	}

	public ListBox getLbLanguages() {
		return lbLanguages;
	}

	public Label getLblLanguages() {
		return lblLanguages;
	}

	public HTMLPanel getHeaderPanel() {
		return pnlHeader;
	}

	public AcceptsOneWidget getMainPanel() {
		return pnlMain;
	}

	@Override
	public Widget asWidget() {
		return this;
	}
}
