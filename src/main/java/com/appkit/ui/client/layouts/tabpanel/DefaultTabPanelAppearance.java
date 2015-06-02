package com.appkit.ui.client.layouts.tabpanel;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public class DefaultTabPanelAppearance implements TabPanelAppearance {

    static {
        Resources.INSTANCE.css().ensureInjected();
    }

    interface Resources extends ClientBundle {

        Resources INSTANCE = GWT.create(Resources.class);

        @Source("tabpanel.css")
        TabPanelCss css();

        @Source("chevron-left.png")
        @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
        ImageResource chevronLeftImage();

        @Source("chevron-right.png")
        @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
        ImageResource chevronRightImage();
    }

    public TabPanelCss css() {
        return Resources.INSTANCE.css();
    }

}
