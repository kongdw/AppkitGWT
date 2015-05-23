package com.appkit.ui.client.widgets.input.radio;


import com.google.gwt.resources.client.CssResource;

public interface RadioButtonAppearance {

    public interface RadioCss extends CssResource {

        @ClassName("appkit-Radio")
        String radioClass();

        @ClassName("appkit-Radio-Button")
        String radioButtonClass();

        @ClassName("appkit-Radio-inner")
        String radioInnerClass();

        @ClassName("appkit-Radio-Button-cell")
        String radioButtonCellClass();

        @ClassName("appkit-Radio-cell")
        String radioCellClass();

        @ClassName("appkit-Radio-label")
        String radioLabelClass();

        @ClassName("appkit-state-selected")
        String radioSelectedClass();

        @ClassName("appkit-state-disabled")
        String disabledClass();

        @ClassName("appkit-state-focus")
        String focusClass();

    }

    RadioCss css();
}
