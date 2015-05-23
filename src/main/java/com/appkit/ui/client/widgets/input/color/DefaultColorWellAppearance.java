package com.appkit.ui.client.widgets.input.color;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public class DefaultColorWellAppearance implements ColorWellAppearance {

    static {
        Resources.INSTANCE.css().ensureInjected();
    }

    public interface Resources extends ClientBundle {

        Resources INSTANCE = GWT.create(Resources.class);

        @Source("colorwell.css")
        ColorWellCss css();


    }

    public ColorWellCss css() {
        return Resources.INSTANCE.css();
    }
}