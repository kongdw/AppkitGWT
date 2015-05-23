package com.appkit.ui.client.widgets.input.combo;

import com.appkit.ui.client.widgets.input.text.TextControlAppearance;


public interface ComboBoxAppearance extends TextControlAppearance {

    public interface ComboBoxCss extends TextControlCss {

        @ClassName("appkit-ComboBox")
        String comboBoxClass();

        @ClassName("appkit-ComboBox-toggle")
        String comboBoxToggleClass();

        @ClassName("appkit-state-focus")
        String comboBoxFocusClass();

        @ClassName("appkit-ComboBox-toggle-active")
        String comboBoxToggleActive();

        @ClassName("appkit-Menu-ComboBox")
        String menuComboBoxClass();

    }


    @Override
    ComboBoxCss css();
}
