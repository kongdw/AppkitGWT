package com.appkit.ui.client.application;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;


public class ApplicationCss {

    static {
        Resources.INSTANCE.css().ensureInjected();
    }

    interface Resources extends ClientBundle {

        Resources INSTANCE = GWT.create(Resources.class);

        @Source("app.css")
        CssResource css();
    }

    public CssResource css() {
        return Resources.INSTANCE.css();
    }

}
