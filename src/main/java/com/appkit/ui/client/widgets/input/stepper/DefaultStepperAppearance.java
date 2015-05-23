package com.appkit.ui.client.widgets.input.stepper;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public class DefaultStepperAppearance implements StepperAppearance {


    static {
        Resources.INSTANCE.css().ensureInjected();
    }

    public interface Resources extends ClientBundle {

        Resources INSTANCE = GWT.create(Resources.class);

        @Source("stepper.css")
        StepperCss css();

        @Source("chevron-down.png")
        @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
        ImageResource chevronDownImage();

        @Source("chevron-up.png")
        @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
        ImageResource chevronUpImage();
    }

    public StepperCss css() {
        return Resources.INSTANCE.css();
    }

    @Override
    public ImageResource chevronUpImage() {
        return Resources.INSTANCE.chevronUpImage();
    }

    @Override
    public ImageResource chevronDownImage() {
        return Resources.INSTANCE.chevronDownImage();
    }

}
