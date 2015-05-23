package com.appkit.ui.client.widgets.input.color.colorpanel;


import com.google.gwt.resources.client.CssResource;

public interface ColorWheelAppearance {

    public interface ColorWheelCss extends CssResource {

        @ClassName("appkit-ColorPanel-Wheel")
        String wheel();

        @ClassName("appkit-ColorPanel-BlackWheel")
        String blackWheel();

        @ClassName("appkit-ColorPanel-SelectBox")
        String selectBox();

    }

    ColorWheelCss css();
}
