package com.appkit.ui.client.events.maximize;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;


public interface HasMaximizeHandlers<T> extends HasHandlers {

    /**
     * Adds a {@link MaximizeEvent} handler.
     *
     * @param handler the handler
     * @return the registration for the event
     */

    HandlerRegistration addMaximizeHandler(MaximizeHandler<T> handler);
}
