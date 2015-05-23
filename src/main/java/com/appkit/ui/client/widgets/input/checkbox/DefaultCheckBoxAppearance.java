package com.appkit.ui.client.widgets.input.checkbox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public class DefaultCheckBoxAppearance implements CheckBoxAppearance {

    static {
        Resources.INSTANCE.css().ensureInjected();
    }

    public interface Resources extends ClientBundle {

        Resources INSTANCE = GWT.create(Resources.class);

        @Source("checkbox.css")
        CheckboxCss css();

        @Source("check.png")
        @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
        ImageResource checkImage();
    }

    public CheckboxCss css() {
        return Resources.INSTANCE.css();
    }
}
