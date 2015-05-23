package com.appkit.ui.client.events.minimize;


import com.google.gwt.event.shared.EventHandler;

public interface MinimizeHandler<T> extends EventHandler {

    /**
     * Called when {@link MinimizeEvent} is fired.
     *
     * @param event the {@link MinimizeEvent} that was fired
     */

    void onMinimize(MinimizeEvent<T> event);
}
