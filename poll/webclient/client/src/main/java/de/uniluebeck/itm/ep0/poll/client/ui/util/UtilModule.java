package de.uniluebeck.itm.ep0.poll.client.ui.util;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

public class UtilModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(MessageBoxView.class).to(MessageBoxViewImpl.class).in(Singleton.class);
        bind(MessageBox.class);
    }

}
