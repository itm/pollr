package de.uniluebeck.itm.ep0.poll.client.ui.util;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class ExceptionEvent extends GwtEvent<ExceptionEvent.ExceptionHandler> {

    public interface ExceptionHandler extends EventHandler {

        void onException(ExceptionEvent event);
    }
    public static final Type<ExceptionHandler> TYPE = new Type<ExceptionHandler>();
    private final String message;
    private final String stacktrace;

    public ExceptionEvent(final String message, final String stacktrace) {
        this.message = message;
        this.stacktrace = stacktrace;
    }

    protected void dispatch(final ExceptionHandler handler) {
        handler.onException(this);
    }

    @Override
    public Type<ExceptionHandler> getAssociatedType() {
        return TYPE;
    }

    public String getMessage() {
        return message;
    }

    public String getStacktrace() {
        return stacktrace;
    }
}
