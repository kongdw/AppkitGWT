package com.appkit.ui.client.events.minimize;

import com.google.gwt.event.shared.GwtEvent;

public class MinimizeEvent<T> extends GwtEvent<MinimizeHandler<T>> {

    /**
     * Handler type.
     */
    private static Type<MinimizeHandler<?>> TYPE;

    public static <T> void fire(HasMinimizeHandlers<T> source) {
        if (TYPE != null) {
            MinimizeEvent<T> event = new MinimizeEvent<T>();
            source.fireEvent(event);
        }
    }

    /**
     * Gets the type associated with this event.
     *
     * @return returns the handler type
     */
    public static Type<MinimizeHandler<?>> getType() {
        return TYPE != null ? TYPE : (TYPE = new Type<MinimizeHandler<?>>());
    }

    // The instance knows its of type T, but the TYPE
    // field itself does not, so we have to do an unsafe cast here.
    @SuppressWarnings("unchecked")
    @Override
    public final Type<MinimizeHandler<T>> getAssociatedType() {
        return (Type) TYPE;
    }

    @Override
    protected void dispatch(MinimizeHandler<T> handler) {
        handler.onMinimize(this);
    }
}
