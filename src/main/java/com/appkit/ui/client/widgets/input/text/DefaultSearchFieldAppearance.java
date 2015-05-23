package com.appkit.ui.client.widgets.input.text;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public class DefaultSearchFieldAppearance implements SearchFieldAppearance {

    static {
        Resources.INSTANCE.css().ensureInjected();
    }

    public interface Resources extends ClientBundle {

        Resources INSTANCE = GWT.create(Resources.class);

        @Source({"searchfield.css", "textcontrol.css"})
        SearchFieldCss css();

        @Source("search.png")
        @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
        ImageResource searchImage();

        @Source("circle-close.png")
        @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
        ImageResource cancelSearchImage();

        @Source("circle-close-active.png")
        @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
        ImageResource cancelSearchActiveImage();
    }

    public SearchFieldCss css() {
        return Resources.INSTANCE.css();
    }
}
