package com.appkit.ui.client.layout.tabpanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public class DefaultTabItemAppearance implements TabItemAppearance {

    static {
        Resources.INSTANCE.css().ensureInjected();
    }

    interface Resources extends ClientBundle {

        Resources INSTANCE = GWT.create(Resources.class);

        @Source("tabitem.css")
        TabItemCss css();

        @Source("close.png")
        @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
        ImageResource closeImage();

        @Source("close-highlighted.png")
        @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
        ImageResource closeHighlightedImage();
    }

    public TabItemCss css() {
        return Resources.INSTANCE.css();
    }

}
