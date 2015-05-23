package com.appkit.ui.client.widgets.input.radio;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public class DefaultRadioButtonAppearance implements RadioButtonAppearance {

    static {
        Resources.INSTANCE.css().ensureInjected();
    }

    public interface Resources extends ClientBundle {

        Resources INSTANCE = GWT.create(Resources.class);

        @Source("radio.css")
        RadioCss css();
    }

    public RadioCss css() {
        return Resources.INSTANCE.css();
    }
}
