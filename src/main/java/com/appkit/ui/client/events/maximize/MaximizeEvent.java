package com.appkit.ui.client.events.maximize;

import com.google.gwt.event.shared.GwtEvent;


public class MaximizeEvent<T> extends GwtEvent<MaximizeHandler<T>> {

    /**
     * Handler type.
     */
    private static Type<MaximizeHandler<?>> TYPE;

    public static <T> void fire(HasMaximizeHandlers<T> source) {
        if (TYPE != null) {
            MaximizeEvent<T> event = new MaximizeEvent<T>();
            source.fireEvent(event);
        }
    }

    /**
     * Gets the type associated with this event.
     *
     * @return returns the handler type
     */
    public static Type<MaximizeHandler<?>> getType() {
        return TYPE != null ? TYPE : (TYPE = new Type<MaximizeHandler<?>>());
    }

    // The instance knows its of type T, but the TYPE
    // field itself does not, so we have to do an unsafe cast here.
    @SuppressWarnings("unchecked")
    @Override
    public final Type<MaximizeHandler<T>> getAssociatedType() {
        return (Type) TYPE;
    }

    @Override
    protected void dispatch(MaximizeHandler<T> handler) {
        handler.onMaximize(this);
    }
}
