package com.appkit.ui.client.widgets.popover;


import com.google.gwt.resources.client.CssResource;

public interface PopoverAppearance {

    public interface PopoverCss extends CssResource {

        @ClassName("appkit-Popover")
        String popoverClass();

        @ClassName("appkit-Popover-Arrow")
        String arrowClass();

        @ClassName("appkit-Popover-UpArrow")
        String upArrowClass();

        @ClassName("appkit-Popover-LeftArrow")
        String leftArrowClass();

        @ClassName("appkit-Popover-DownArrow")
        String downArrowClass();

        @ClassName("appkit-Popover-RightArrow")
        String rightArrowClass();


    }

    PopoverCss css();
}
