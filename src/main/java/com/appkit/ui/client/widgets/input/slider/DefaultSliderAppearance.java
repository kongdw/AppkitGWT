package com.appkit.ui.client.widgets.input.slider;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;


public class DefaultSliderAppearance implements SliderAppearance {

    static {
        Resources.INSTANCE.css().ensureInjected();
    }

    public interface Resources extends ClientBundle {

        Resources INSTANCE = GWT.create(Resources.class);

        @Source("slider.css")
        SliderCss css();
    }

    public SliderCss css() {
        return Resources.INSTANCE.css();
    }

}
