package com.appkit.ui.client.widgets.menu;

import com.appkit.ui.client.widgets.touch.TouchFocusWidget;
import com.google.gwt.dom.client.Element;

public abstract class AbstractMenuItem extends TouchFocusWidget {

    protected AbstractMenu menu = null;
    protected boolean enabled = true;

    public AbstractMenuItem(Element element) {
        super(element);
    }

    protected void setMenu(AbstractMenu menu) {
        this.menu = menu;
    }

    protected AbstractMenu getMenu() {
        return this.menu;
    }


}
