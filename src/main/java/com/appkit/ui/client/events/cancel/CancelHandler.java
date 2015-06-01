package com.appkit.ui.client.events.cancel;


import com.google.gwt.event.shared.EventHandler;

public interface CancelHandler extends EventHandler {

    /**
     * Called when {@link CancelEvent} is fired.
     *
     * @param event the {@link CancelEvent} that was fired
     */

    void onCancel(CancelEvent event);
}