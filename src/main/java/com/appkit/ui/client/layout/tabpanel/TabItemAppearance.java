package com.appkit.ui.client.layout.tabpanel;


import com.google.gwt.resources.client.CssResource;

public interface TabItemAppearance {

    public interface TabItemCss extends CssResource {

        @ClassName("appkit-TabItem")
        String tabItemClass();

        @ClassName("appkit-TabItem-first")
        String tabItemFirstClass();

        @ClassName("appkit-TabItem-closeBtn")
        String tabItemCloseButtonClass();

        @ClassName("appkit-TabItem-Top")
        String tabItemTop();

        @ClassName("appkit-TabItem-Bottom")
        String tabItemBottom();
    }

    TabItemCss css();
}
