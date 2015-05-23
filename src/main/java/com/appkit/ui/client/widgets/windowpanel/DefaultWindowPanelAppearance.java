package com.appkit.ui.client.widgets.windowpanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

/**
 * Created by cbruno on 2/18/15.
 */
public class DefaultWindowPanelAppearance implements WindowPanelAppearance {

    static {
        Resources.INSTANCE.css().ensureInjected();
    }

    public interface Resources extends ClientBundle {

        Resources INSTANCE = GWT.create(Resources.class);

        @Source("windowpanel.css")
        WindowPanelCss css();
    }

    public WindowPanelCss css() {
        return Resources.INSTANCE.css();
    }

}
