package com.appkit.ui.client.events.action;

import com.google.gwt.event.shared.EventHandler;

public interface ActionHandler extends EventHandler {

    /**
     * Called when {@link ActionEvent} is fired.
     *
     * @param event the {@link ActionEvent} that was fired
     */

    void onAction(ActionEvent event);
}
