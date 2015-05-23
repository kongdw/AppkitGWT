package com.appkit.ui.client.events.cancel;

import com.google.gwt.event.shared.EventHandler;

public interface CancelSearchHandler extends EventHandler {

    /**
     * Called when {@link CancelSearchEvent} is fired.
     *
     * @param event the {@link CancelSearchEvent} that was fired
     */

    void onCancelSearch(CancelSearchEvent event);
}
