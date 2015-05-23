package com.appkit.ui.client.events.restore;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;


public interface HasRestoreSizeHandlers<T> extends HasHandlers {

    /**
     * Adds a {@link RestoreSizeEvent} handler.
     *
     * @param handler the handler
     * @return the registration for the event
     */

    HandlerRegistration addRestoreSizeHandler(RestoreSizeHandler<T> handler);

}
