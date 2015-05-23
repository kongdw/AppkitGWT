package com.appkit.ui.client.widgets.menu;

import com.google.gwt.resources.client.CssResource;

public interface MenuAppearance {

    public interface MenuCss extends CssResource {

        @ClassName("appkit-Menu")
        String menuClass();


    }

    public MenuCss css();

}
