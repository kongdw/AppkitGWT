package com.appkit.ui.client.widgets;

import com.appkit.ui.client.widgets.touch.TouchFocusWidget;
import com.google.gwt.dom.client.Element;


public class Control extends TouchFocusWidget {

    private boolean canFocus = true;

    public Control(Element element) {
        super(element);
    }

    public void setCanFocus(boolean canFocus) {
        this.canFocus = canFocus;

        if (this.canFocus) {
            setTabIndex(0);
        } else {
            setTabIndex(-2);
        }
    }


    public boolean getCanFocus() {
        return canFocus;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        if (!enabled) {
            getElement().setAttribute("aria-disabled", "true");
        } else {
            getElement().removeAttribute("aria-disabled");
        }
    }
}
