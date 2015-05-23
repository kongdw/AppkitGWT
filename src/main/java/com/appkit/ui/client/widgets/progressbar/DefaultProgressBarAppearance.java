package com.appkit.ui.client.widgets.progressbar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public class DefaultProgressBarAppearance implements ProgressBarAppearance {

    static {
        Resources.INSTANCE.css().ensureInjected();
    }

    public interface Resources extends ClientBundle {

        Resources INSTANCE = GWT.create(Resources.class);

        @Source("progressbar.css")
        ProgressBarCss css();
    }

    public ProgressBarCss css() {
        return Resources.INSTANCE.css();
    }

}