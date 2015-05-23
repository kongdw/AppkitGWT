package com.appkit.ui.client.events.minimize;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;


public interface HasMinimizeHandlers<T> extends HasHandlers {

    /**
     * Adds a {@link MinimizeEvent} handler.
     *
     * @param handler the handler
     * @return the registration for the event
     */

    HandlerRegistration addMinimizeHandler(MinimizeHandler<T> handler);
}
