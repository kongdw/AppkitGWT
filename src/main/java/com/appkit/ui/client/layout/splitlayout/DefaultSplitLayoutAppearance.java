package com.appkit.ui.client.layout.splitlayout;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public class DefaultSplitLayoutAppearance implements SplitLayoutAppearance {

    static {
        Resources.INSTANCE.css().ensureInjected();
    }

    interface Resources extends ClientBundle {

        Resources INSTANCE = GWT.create(Resources.class);

        @Source("splitlayout.css")
        SplitLayoutCss css();

        @Source("handle-h.png")
        @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
        ImageResource splitHorizontalHandleImage();

        @Source("handle-v.png")
        @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
        ImageResource splitVerticalHandleImage();
    }

    public SplitLayoutCss css() {
        return Resources.INSTANCE.css();
    }
}
