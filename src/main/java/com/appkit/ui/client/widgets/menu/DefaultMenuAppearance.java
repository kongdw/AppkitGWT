package com.appkit.ui.client.widgets.menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;


public class DefaultMenuAppearance implements MenuAppearance {

    static {
        Resources.INSTANCE.css().ensureInjected();
    }

    public interface Resources extends ClientBundle {

        public Resources INSTANCE = GWT.create(Resources.class);

        @Source("menu.css")
        MenuCss css();
    }

    public MenuCss css() {
        return Resources.INSTANCE.css();
    }


}