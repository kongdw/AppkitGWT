package com.appkit.ui.client.layouts.accordian;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public class DefaultAccordionLayoutAppearance implements AccordionLayoutAppearance {

    static {
        Resources.INSTANCE.css().ensureInjected();
    }

    interface Resources extends ClientBundle {

        Resources INSTANCE = GWT.create(Resources.class);

        @Source("accordian.css")
        AccordionLayoutCss css();

        @Source("minus.png")
        @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
        ImageResource minusImage();

        @Source("plus.png")
        @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
        ImageResource plusImage();

    }


    @Override
    public AccordionLayoutCss css() {
        return Resources.INSTANCE.css();
    }
}
