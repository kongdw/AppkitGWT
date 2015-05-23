package com.appkit.ui.client.events.restore;

import com.google.gwt.event.shared.EventHandler;


public interface RestoreSizeHandler<T> extends EventHandler {

    /**
     * Called when {@link RestoreSizeEvent} is fired.
     *
     * @param event the {@link RestoreSizeEvent} that was fired
     */

    void onRestore(RestoreSizeEvent<T> event);
}
