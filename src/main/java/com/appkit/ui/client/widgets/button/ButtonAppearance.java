package com.appkit.ui.client.widgets.button;

import com.google.gwt.resources.client.CssResource;

public interface ButtonAppearance {

    public interface ButtonCss extends CssResource {

        @ClassName("appkit-Button")
        String buttonClass();

        @ClassName("appkit-Button-content-wrap")
        String buttonContentWrapClass();

        @ClassName("appkit-Button-title")
        String buttonTitleClass();

        @ClassName("appkit-Button-image")
        String buttonImageClass();

        @ClassName("danger")
        String dangerButtonClass();

        @ClassName("primary")
        String primaryButtonClass();

        @ClassName("info")
        String infoButtonClass();

        @ClassName("success")
        String successButtonClass();

        @ClassName("warning")
        String warningButtonClass();

        @ClassName("inverse")
        String inverseButtonClass();

        @ClassName("link")
        String linkButtonClass();

        @ClassName("appkit-ImageButton")
        String imageButtonClass();


    }

    ButtonCss css();

}
