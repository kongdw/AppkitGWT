package com.appkit.ui.client.widgets.input.date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public class DefaultCalendarViewAppearance implements CalendarViewAppearance {

    static {
        Resources.INSTANCE.css().ensureInjected();
    }

    public interface Resources extends ClientBundle {

        Resources INSTANCE = GWT.create(Resources.class);

        @Source("calendar.css")
        CalendarViewCss css();

        @Source("chevron-right.png")
        @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
        ImageResource chevronRightImage();

        @Source("chevron-left.png")
        @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
        ImageResource chevronLeftImage();

        @Source("double-left.png")
        @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
        ImageResource doubleLeftImage();

        @Source("double-right.png")
        @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
        ImageResource doubleRightImage();


    }

    public CalendarViewCss css() {
        return Resources.INSTANCE.css();
    }

}
