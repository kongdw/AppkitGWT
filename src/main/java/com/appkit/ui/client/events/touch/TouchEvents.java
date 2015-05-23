package com.appkit.ui.client.events.touch;

import com.google.gwt.user.client.Event;

public class TouchEvents {
    public static boolean isTouchEvent(Event event) {
        int eventType = event.getTypeInt();
        return eventType == Event.ONTOUCHSTART || eventType == Event.ONTOUCHMOVE ||
                eventType == Event.ONTOUCHEND || eventType == Event.ONTOUCHCANCEL;
    }
}
