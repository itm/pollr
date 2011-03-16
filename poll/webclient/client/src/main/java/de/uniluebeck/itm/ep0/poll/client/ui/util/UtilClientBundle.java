package de.uniluebeck.itm.ep0.poll.client.ui.util;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface UtilClientBundle extends ClientBundle {

    @Source("de/uniluebeck/itm/ep0/poll/client/ui/util/success.png")
    ImageResource getSuccessImageResource();

    @Source("de/uniluebeck/itm/ep0/poll/client/ui/util/warning.png")
    ImageResource getWarningImageResource();

    @Source("de/uniluebeck/itm/ep0/poll/client/ui/util/error.png")
    ImageResource getErrorImageResource();
}
