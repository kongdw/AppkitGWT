package com.appkit.ui.client.widgets.menu;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public class DefaultMenuBarAppearance implements MenuBarAppearance {

    static {
        Resources.INSTANCE.css().ensureInjected();
    }

    public interface Resources extends ClientBundle {

        public Resources INSTANCE = GWT.create(Resources.class);

        @Source("menubar.css")
        MenuBarCss css();
    }

    public MenuBarCss css() {
        return Resources.INSTANCE.css();
    }

}
