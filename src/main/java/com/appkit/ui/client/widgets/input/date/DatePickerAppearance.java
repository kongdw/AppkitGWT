package com.appkit.ui.client.widgets.input.date;


import com.appkit.ui.client.widgets.input.text.TextControlAppearance;

public interface DatePickerAppearance extends TextControlAppearance {

    public interface DatePickerCss extends TextControlCss {

        @ClassName("appkit-DatePicker")
        String datePickerClass();

        @ClassName("appkit-DatePicker-toggle")
        String datePickerToggleClass();

        @ClassName("appkit-DatePicker-toggle-active")
        String datePickerToggleActive();

        @ClassName("appkit-state-focus")
        String appkitStateFocus();
    }

    DatePickerCss css();
}
