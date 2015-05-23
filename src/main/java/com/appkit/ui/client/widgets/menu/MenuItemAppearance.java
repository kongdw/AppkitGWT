package com.appkit.ui.client.widgets.menu;


import com.google.gwt.resources.client.CssResource;

public interface MenuItemAppearance {

    public interface MenuItemCss extends CssResource {

        @ClassName("appkit-MenuItem")
        String menuItemClass();

        @ClassName("appkit-MenuItem-title")
        String menuItemTitleClass();

        @ClassName("appkit-MenuItem-accessory")
        String menuItemAccessoryClass();

        @ClassName("appkit-MenuItem-marker")
        String menuItemMarkerClass();

        @ClassName("appkit-MenuItem-active")
        String menuItemActiveClass();

        @ClassName("appkit-state-disabled")
        String menuItemDisabledClass();

        @ClassName("icon-check")
        String iconCheckClass();

    }

    public MenuItemCss css();
}
