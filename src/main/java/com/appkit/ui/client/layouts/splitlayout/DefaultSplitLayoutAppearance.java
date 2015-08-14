package com.appkit.ui.client.layouts.splitlayout;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public class DefaultSplitLayoutAppearance implements SplitLayoutAppearance {

    static {
        Resources.INSTANCE.css().ensureInjected();
    }

    interface Resources extends ClientBundle {

        Resources INSTANCE = GWT.create(Resources.class);

        @Source("splitlayout.css")
        SplitLayoutCss css();


    }

    public SplitLayoutCss css() {
        return Resources.INSTANCE.css();
    }
}
