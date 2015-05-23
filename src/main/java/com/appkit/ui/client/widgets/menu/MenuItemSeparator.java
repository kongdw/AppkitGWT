package com.appkit.ui.client.widgets.menu;

import com.google.gwt.user.client.DOM;

public class MenuItemSeparator extends AbstractMenuItem {


    public MenuItemSeparator() {
        super(DOM.createDiv());
        render();
    }


    private void render() {
        getElement().setClassName("appkit-menu-item-separator");
    }

}
