package com.appkit.ui.client.layout.tabpanel;


import com.google.gwt.resources.client.CssResource;

public interface TabPanelAppearance {

    public interface TabPanelCss extends CssResource {

        @ClassName("appkit-TabPanel")
        String tabPanelClass();

        @ClassName("appkit-TabPanelBody")
        String tabPanelBodyClass();

        @ClassName("appkit-TabBar")
        String tabBarClass();

        @ClassName("appkit-TabPanel-TabsTop")
        String tabPanelPositionTopClass();

        @ClassName("appkit-TabPanel-TabsBottom")
        String tabPanelPositionBottomClass();

        @ClassName("appkit-TabBar-TabsTop")
        String tabBarPositionTopClass();

        @ClassName("appkit-TabBar-TabsBottom")
        String tabBarPositionBottomClass();

        @ClassName("appkit-TabPanel-ScrollLeftBtn")
        String tabPanelScrollLeftBtnClass();

        @ClassName("appkit-TabPanel-ScrollRightBtn")
        String tabPanelScrollRightBtnClass();

    }

    TabPanelCss css();

}
