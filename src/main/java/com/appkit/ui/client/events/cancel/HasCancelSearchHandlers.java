package com.appkit.ui.client.events.cancel;


import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasCancelSearchHandlers extends HasHandlers {

    /**
     * Adds a {@link CancelSearchEvent} handler.
     *
     * @param handler the handler
     * @return the registration for the event
     */

    HandlerRegistration addCancelSearchHandler(CancelSearchHandler handler);
}