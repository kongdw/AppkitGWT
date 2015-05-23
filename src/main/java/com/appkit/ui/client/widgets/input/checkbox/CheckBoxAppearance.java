package com.appkit.ui.client.widgets.input.checkbox;


import com.google.gwt.resources.client.CssResource;

public interface CheckBoxAppearance {

    public interface CheckboxCss extends CssResource {

        @ClassName("appkit-Checkbox")
        String checkboxClass();

        @ClassName("appkit-Checkbox-button-cell")
        String checkboxCellClass();

        @ClassName("appkit-Checkbox-bezel-cell")
        String checkboxBezelCellClass();

        @ClassName("appkit-Checkbox-bezel")
        String checkboxBezelClass();

        @ClassName("appkit-Checkbox-label")
        String checkboxLabelClass();

        @ClassName("appkit-state-disabled")
        String disabledClass();

        @ClassName("appkit-Checkbox-check")
        String iconCheckClass();


    }

    CheckboxCss css();
}
