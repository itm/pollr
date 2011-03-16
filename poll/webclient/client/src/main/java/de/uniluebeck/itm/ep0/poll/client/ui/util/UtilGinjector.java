package de.uniluebeck.itm.ep0.poll.client.ui.util;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules({UtilModule.class})
public interface UtilGinjector extends Ginjector {

    MessageBox getMessageBox();
}
