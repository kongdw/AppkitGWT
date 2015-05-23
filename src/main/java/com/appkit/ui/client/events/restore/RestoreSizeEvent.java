package com.appkit.ui.client.events.restore;


import com.google.gwt.event.shared.GwtEvent;

/**
 * Created by cbruno on 1/17/15.
 */
public class RestoreSizeEvent<T> extends GwtEvent<RestoreSizeHandler<T>> {

    /**
     * Handler type.
     */
    private static Type<RestoreSizeHandler<?>> TYPE;

    public static <T> void fire(HasRestoreSizeHandlers<T> source) {
        if (TYPE != null) {
            RestoreSizeEvent<T> event = new RestoreSizeEvent<T>();
            source.fireEvent(event);
        }
    }

    /**
     * Gets the type associated with this event.
     *
     * @return returns the handler type
     */
    public static Type<RestoreSizeHandler<?>> getType() {
        return TYPE != null ? TYPE : (TYPE = new Type<RestoreSizeHandler<?>>());
    }

    // The instance knows its of type T, but the TYPE
    // field itself does not, so we have to do an unsafe cast here.
    @SuppressWarnings("unchecked")
    @Override
    public final Type<RestoreSizeHandler<T>> getAssociatedType() {
        return (Type) TYPE;
    }

    @Override
    protected void dispatch(RestoreSizeHandler<T> handler) {
        handler.onRestore(this);
    }


}
