package com.appkit.ui.client.events.search;

import com.google.gwt.event.shared.GwtEvent;

public class PerformSearchEvent<T> extends GwtEvent<PerformSearchHandler<T>> {

    /**
     * Handler type.
     */
    private static Type<PerformSearchHandler<?>> TYPE;

    public static <T> void fire(HasPerformSearchHandlers<T> source, T value) {
        if (TYPE != null) {
            PerformSearchEvent<T> event = new PerformSearchEvent<T>();
            source.fireEvent(event);
        }
    }

    /**
     * Gets the type associated with this event.
     *
     * @return returns the handler type
     */
    public static Type<PerformSearchHandler<?>> getType() {
        return TYPE != null ? TYPE : (TYPE = new Type<PerformSearchHandler<?>>());
    }

    // The instance knows its of type T, but the TYPE
    // field itself does not, so we have to do an unsafe cast here.
    @SuppressWarnings("unchecked")
    @Override
    public final Type<PerformSearchHandler<T>> getAssociatedType() {
        return (Type) TYPE;
    }

    @Override
    protected void dispatch(PerformSearchHandler<T> handler) {
        handler.onPerformSearch(this);
    }
}
