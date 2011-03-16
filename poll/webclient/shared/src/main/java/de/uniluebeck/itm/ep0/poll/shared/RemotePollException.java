package de.uniluebeck.itm.ep0.poll.shared;

import java.io.Serializable;

public class RemotePollException extends Exception implements Serializable {

    private static final long serialVersionUID = 932806176360713676L;

    public RemotePollException() {
        super();
    }

    public RemotePollException(String msg) {
        super(msg);
    }

    public RemotePollException(String msg, Throwable t) {
        super(msg, t);
    }
}
