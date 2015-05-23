package com.appkit.ui.client.events.maximize;

import com.google.gwt.event.shared.EventHandler;

/**
 * Created by cbruno on 1/17/15.
 */
public interface MaximizeHandler<T> extends EventHandler {

    /**
     * Called when {@link com.appkit.ui.client.events.maximize.MaximizeEvent} is fired.
     *
     * @param event the {@link com.appkit.ui.client.events.maximize.MaximizeEvent} that was fired
     */

    void onMaximize(MaximizeEvent<T> event);
}
