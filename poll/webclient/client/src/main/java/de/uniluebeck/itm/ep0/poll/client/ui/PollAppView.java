package de.uniluebeck.itm.ep0.poll.client.ui;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;

public interface PollAppView extends IsWidget {

    HTMLPanel getHeaderPanel();

    AcceptsOneWidget getMainPanel();
}
