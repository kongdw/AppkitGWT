package com.appkit.ui.client.events.action;

import com.google.gwt.event.shared.GwtEvent;

public class ActionEvent extends GwtEvent<ActionHandler> {

    /**
     * Handler type.
     */
    private static GwtEvent.Type<ActionHandler> TYPE;

    public static void fire(HasActionHandlers source) {
        if (TYPE != null) {
            ActionEvent event = new ActionEvent();
            source.fireEvent(event);
        }
    }

    /**
     * Gets the type associated with this event.
     *
     * @return returns the handler type
     */
    public static GwtEvent.Type<ActionHandler> getType() {
        return TYPE != null ? TYPE : (TYPE = new GwtEvent.Type<ActionHandler>());
    }

    // The instance knows its of type T, but the TYPE
    // field itself does not, so we have to do an unsafe cast here.
    @SuppressWarnings("unchecked")
    @Override
    public final GwtEvent.Type<ActionHandler> getAssociatedType() {
        return (GwtEvent.Type) TYPE;
    }

    @Override
    protected void dispatch(ActionHandler handler) {
        handler.onAction(this);
    }
}
