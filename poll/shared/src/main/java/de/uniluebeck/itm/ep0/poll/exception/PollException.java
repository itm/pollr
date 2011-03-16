package de.uniluebeck.itm.ep0.poll.exception;

public class PollException extends Exception {

    private static final long serialVersionUID = 195705818069535842L;

    public PollException() {
    }

    public PollException(final String arg0) {
        super(arg0);
    }

    public PollException(final Throwable arg0) {
        super(arg0);
    }

    public PollException(final String arg0, final Throwable arg1) {
        super(arg0, arg1);
    }
}
