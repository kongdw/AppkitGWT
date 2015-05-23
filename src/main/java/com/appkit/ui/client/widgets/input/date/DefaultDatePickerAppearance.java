package com.appkit.ui.client.widgets.input.date;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public class DefaultDatePickerAppearance implements DatePickerAppearance {

    static {
        Resources.INSTANCE.css().ensureInjected();
    }

    public interface Resources extends ClientBundle {

        Resources INSTANCE = GWT.create(Resources.class);

        @Source({"datepicker.css", "../text/textcontrol.css"})
        DatePickerCss css();

        @Source("calendar.png")
        @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
        ImageResource calendarImage();
    }

    public DatePickerCss css() {
        return Resources.INSTANCE.css();
    }

}
