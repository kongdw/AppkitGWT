package com.appkit.ui.client.widgets.input.color.colorpanel;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public class DefaultColorWheelAppearance implements ColorWheelAppearance {

    static {
        Resources.INSTANCE.css().ensureInjected();
    }

    public interface Resources extends ClientBundle {

        Resources INSTANCE = GWT.create(Resources.class);

        @Source("colorwheel.css")
        ColorWheelCss css();

        @Source("wheel.png")
        ImageResource wheel();

        @Source("wheel_black.png")
        ImageResource blackWheel();
    }

    public ColorWheelCss css() {
        return Resources.INSTANCE.css();
    }

}
