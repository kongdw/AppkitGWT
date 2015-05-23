package com.appkit.ui.client.events.search;

import com.google.gwt.event.shared.EventHandler;


public interface PerformSearchHandler<T> extends EventHandler {

    /**
     * Called when {@link PerformSearchEvent} is fired.
     *
     * @param event the {@link PerformSearchEvent} that was fired
     */

    void onPerformSearch(PerformSearchEvent<T> event);
}
