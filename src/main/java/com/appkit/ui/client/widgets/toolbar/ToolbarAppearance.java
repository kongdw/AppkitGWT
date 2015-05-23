package com.appkit.ui.client.widgets.toolbar;


import com.google.gwt.resources.client.CssResource;

public interface ToolbarAppearance {

    public interface ToolbarCss extends CssResource {

        @ClassName("appkit-Toolbar")
        String toolbarClass();
    }

    ToolbarCss css();
}
