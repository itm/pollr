package de.uniluebeck.itm.ep0.poll.client.ui;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface PollImageBundle extends ClientBundle {

    @Source("de/uniluebeck/itm/ep0/poll/client/ui/custom/locale.png")
    ImageResource locale();

    @Source("de/uniluebeck/itm/ep0/poll/client/ui/Pollr.png")
    ImageResource pollr();

    @Source("de/uniluebeck/itm/ep0/poll/client/ui/Yes.png")
    ImageResource yes();

    @Source("de/uniluebeck/itm/ep0/poll/client/ui/No.png")
    ImageResource no();
}
