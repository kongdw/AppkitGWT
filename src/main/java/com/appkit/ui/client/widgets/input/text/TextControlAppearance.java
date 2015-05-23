package com.appkit.ui.client.widgets.input.text;

import com.google.gwt.resources.client.CssResource;


public interface TextControlAppearance {

    public interface TextControlCss extends CssResource {

        @ClassName("appkit-TextControl")
        String textControlClass();

        @ClassName("appkit-TextControl-input")
        String textControlInputClass();

        @ClassName("appkit-state-active")
        String activeClass();

        @ClassName("appkit-state-disabled")
        String disabledClass();

    }

    TextControlCss css();

}
