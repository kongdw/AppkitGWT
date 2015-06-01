package com.appkit.ui.client.events.cancel;


import com.google.gwt.event.shared.GwtEvent;

public class CancelEvent extends GwtEvent<CancelHandler> {
    /**
     * Handler type.
     */
    private static Type<CancelHandler> TYPE;

    public static void fire(HasCancelHandlers source) {
        if (TYPE != null) {
            CancelEvent event = new CancelEvent();
            source.fireEvent(event);
        }
    }

    /**
     * Gets the type associated with this event.
     *
     * @return returns the handler type
     */
    public static Type<CancelHandler> getType() {
        return TYPE != null ? TYPE : (TYPE = new Type<CancelHandler>());
    }

    // The instance knows its of type T, but the TYPE
    // field itself does not, so we have to do an unsafe cast here.
    @SuppressWarnings("unchecked")
    @Override
    public final Type<CancelHandler> getAssociatedType() {
        return (Type) TYPE;
    }

    @Override
    protected void dispatch(CancelHandler handler) {
        handler.onCancel(this);
    }
}
