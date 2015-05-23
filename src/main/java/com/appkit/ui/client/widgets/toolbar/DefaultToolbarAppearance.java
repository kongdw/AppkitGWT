package com.appkit.ui.client.widgets.toolbar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public class DefaultToolbarAppearance implements ToolbarAppearance {

    static {
        Resources.INSTANCE.css().ensureInjected();
    }

    public interface Resources extends ClientBundle {

        Resources INSTANCE = GWT.create(Resources.class);

        @Source("toolbar.css")
        ToolbarCss css();

    }


    @Override
    public ToolbarCss css() {
        return Resources.INSTANCE.css();
    }
}
