package com.appkit.ui.client.events.cancel;


import com.google.gwt.event.shared.GwtEvent;

public class CancelSearchEvent extends GwtEvent<CancelSearchHandler> {
    /**
     * Handler type.
     */
    private static Type<CancelSearchHandler> TYPE;

    public static void fire(HasCancelSearchHandlers source) {
        if (TYPE != null) {
            CancelSearchEvent event = new CancelSearchEvent();
            source.fireEvent(event);
        }
    }

    /**
     * Gets the type associated with this event.
     *
     * @return returns the handler type
     */
    public static Type<CancelSearchHandler> getType() {
        return TYPE != null ? TYPE : (TYPE = new Type<CancelSearchHandler>());
    }

    // The instance knows its of type T, but the TYPE
    // field itself does not, so we have to do an unsafe cast here.
    @SuppressWarnings("unchecked")
    @Override
    public final Type<CancelSearchHandler> getAssociatedType() {
        return (Type) TYPE;
    }

    @Override
    protected void dispatch(CancelSearchHandler handler) {
        handler.onCancelSearch(this);
    }
}
