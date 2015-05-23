package com.appkit.ui.client.widgets.toolbar;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.FlowPanel;

public class ToolbarFlexibleSpace extends FlowPanel implements ToolbarItem {

    private Toolbar toolbar;

    public ToolbarFlexibleSpace() {

        getElement().addClassName("appkit-toolbar-flex");
    }

    public void setWidth(double w) {
        getElement().getStyle().setWidth(w, Style.Unit.PX);
    }

}
