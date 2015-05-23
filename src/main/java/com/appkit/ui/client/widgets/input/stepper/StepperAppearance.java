package com.appkit.ui.client.widgets.input.stepper;


import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface StepperAppearance {

    public interface StepperCss extends CssResource {

        @ClassName("appkit-Stepper")
        String stepperClass();

        @ClassName("appkit-Stepper-up")
        String stepperUpButtonClass();

        @ClassName("appkit-Stepper-down")
        String stepperDownButtonClass();

        @ClassName("appkit-Stepper-field")
        String stepperTextFieldClass();

        @ClassName("appkit-state-disabled")
        String disabledClass();
    }

    StepperCss css();

    ImageResource chevronUpImage();

    ImageResource chevronDownImage();

}
