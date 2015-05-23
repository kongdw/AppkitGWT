package com.appkit.ui.client.widgets.input.slider;

import com.google.gwt.resources.client.CssResource;

/**
 * Created by cbruno on 2/19/15.
 */
public interface SliderAppearance {

    public interface SliderCss extends CssResource {

        @ClassName("appkit-Slider")
        String sliderClass();

        @ClassName("appkit-Slider-vertical")
        String sliderVerticalClass();

        @ClassName("appkit-Slider-horizontal")
        String sliderHorizontalClass();

        @ClassName("appkit-Slider-handle")
        String sliderHandleClass();

        @ClassName("appkit-Slider-range")
        String sliderRangeClass();

        @ClassName("appkit-state-disabled")
        String disabledClass();

        @ClassName("appkit-state-active")
        String activeClass();

        @ClassName("appkit-Slider-value-tip")
        String sliderValueTipClass();

    }

    SliderCss css();
}
