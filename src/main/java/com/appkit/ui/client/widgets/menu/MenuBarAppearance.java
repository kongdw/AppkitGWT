package com.appkit.ui.client.widgets.menu;


import com.google.gwt.resources.client.CssResource;

public interface MenuBarAppearance {

    public interface MenuBarCss extends CssResource {

        @ClassName("appkit-MenuBar")
        String menuBarClass();

        @ClassName("appkit-MenuBar-Item-First")
        String menuBarItemFirstClass();

    }

    public MenuBarCss css();


}
