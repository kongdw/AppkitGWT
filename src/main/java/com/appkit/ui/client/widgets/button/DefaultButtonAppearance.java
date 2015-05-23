package com.appkit.ui.client.widgets.button;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public class DefaultButtonAppearance implements ButtonAppearance {

    static {
        Resources.INSTANCE.css().ensureInjected();
    }

    public interface Resources extends ClientBundle {

        Resources INSTANCE = GWT.create(Resources.class);

        @Source("button.css")
        ButtonCss css();
    }

    public ButtonCss css() {
        return Resources.INSTANCE.css();
    }

}
