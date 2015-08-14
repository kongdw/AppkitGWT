package com.appkit.ui.client.layouts.splitlayout;


import com.google.gwt.resources.client.CssResource;

public interface SplitLayoutAppearance {

    public interface SplitLayoutCss extends CssResource {

        @ClassName("appkit-Splitlayout-east-panel")
        String splitLayoutEastPanelClass();

        @ClassName("appkit-Splitlayout-west-panel")
        String splitLayoutWestPanelClass();

        @ClassName("appkit-Splitlayout-north-panel")
        String splitLayoutNorthPanelClass();

        @ClassName("appkit-Splitlayout-south-panel")
        String splitLayoutSouthPanelClass();

        @ClassName("appkit-SplitLayoutPanel-VDragger")
        String gwtSplitLayoutPanelVDragger();

        @ClassName("appkit-SplitLayoutPanel-HDragger")
        String gwtSplitLayoutPanelHDragger();

        @ClassName("appkit-SplitLayoutPanel")
        String gwtSplitLayoutPanel();
    }

    SplitLayoutCss css();
}
