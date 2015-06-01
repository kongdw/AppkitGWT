package com.appkit.ui.client.events.cancel;


import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasCancelHandlers extends HasHandlers {

    /**
     * Adds a {@link CancelEvent} handler.
     *
     * @param handler the handler
     * @return the registration for the event
     */

    HandlerRegistration addCancelHandler(CancelHandler handler);
}