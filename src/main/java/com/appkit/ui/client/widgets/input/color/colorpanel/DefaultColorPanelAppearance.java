package com.appkit.ui.client.widgets.input.color.colorpanel;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;

public class DefaultColorPanelAppearance implements ColorPanelAppearance {

    static {
        Resources.INSTANCE.css().ensureInjected();
    }

    public interface Resources extends ClientBundle {

        Resources INSTANCE = GWT.create(Resources.class);

        @Source("colorpanel.css")
        ColorPanelCss css();

        @Source("wheel_button.png")
        @ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
        ImageResource wheelButton();

        @Source("wheel_button_h.png")
        @ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
        ImageResource wheelButtonHighlighted();

        @Source("slider_button.png")
        @ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
        ImageResource sliderButton();

        @Source("slider_button_h.png")
        @ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
        ImageResource sliderButtonHighlighted();

        @Source("brightness_bar.png")
        ImageResource brightnessBar();
    }

    public ColorPanelCss css() {
        return Resources.INSTANCE.css();
    }

    public Resources resources() {
        return Resources.INSTANCE;
    }

}
