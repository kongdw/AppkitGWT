package com.appkit.ui.client.widgets.input.combo;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public class DefaultComboBoxAppearance implements ComboBoxAppearance {

    static {
        Resources.INSTANCE.css().ensureInjected();
    }

    public interface Resources extends ClientBundle {

        Resources INSTANCE = GWT.create(Resources.class);

        @Source({"combobox.css", "../text/textcontrol.css"})
        ComboBoxCss css();

        @Source("chevron-down.png")
        @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
        ImageResource chevronDownImage();
    }

    public ComboBoxCss css() {
        return Resources.INSTANCE.css();
    }


}
