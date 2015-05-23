package com.appkit.ui.client.widgets.input.text;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public class DefaultTextControlAppearance implements TextControlAppearance {

    static {
        Resources.INSTANCE.css().ensureInjected();
    }

    public interface Resources extends ClientBundle {

        Resources INSTANCE = GWT.create(Resources.class);

        @Source("textcontrol.css")
        TextControlCss css();
    }

    public TextControlCss css() {
        return Resources.INSTANCE.css();
    }
}
