package com.appkit.ui.client.widgets.alert;


import com.google.gwt.resources.client.CssResource;

public interface AlertAppearance {

    public interface AlertCss extends CssResource {

        @ClassName("appkit-Alert")
        String alertClass();

        @ClassName("appkit-Alert-content")
        String alertContentClass();

        @ClassName("appkit-Alert-icon")
        String alertIconClass();

        @ClassName("appkit-Alert-button-Panel")
        String alertButtonPanelClass();

        @ClassName("appkit-Alert-confirm-Btn")
        String alertConfirmButtonClass();

        @ClassName("appkit-Alert-cancel-Btn")
        String alertCancelButtonClass();

        @ClassName("appkit-Alert-Style-alert")
        String alertIconStyleClass();

        @ClassName("appkit-Alert-Style-ok")
        String okIconStyleClass();

        @ClassName("appkit-Alert-Style-question")
        String questionIconStyleClass();

        @ClassName("appkit-Alert-Style-error")
        String errorIconStyleClass();

    }

    AlertCss css();

}
