package com.appkit.ui.client.events.search;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;


public interface HasPerformSearchHandlers<T> extends HasHandlers {

    /**
     * Adds a {@link PerformSearchEvent} handler.
     *
     * @param handler the handler
     * @return the registration for the event
     */

    HandlerRegistration addPerformSearchHandler(PerformSearchHandler<T> handler);
}
