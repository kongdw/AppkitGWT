package com.appkit.ui.client.widgets.popover;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public class DefaultPopoverAppearance implements PopoverAppearance {

    static {
        Resources.INSTANCE.css().ensureInjected();
    }

    public interface Resources extends ClientBundle {

        Resources INSTANCE = GWT.create(Resources.class);

        @Source("popover.css")
        PopoverCss css();
    }

    public PopoverCss css() {
        return Resources.INSTANCE.css();
    }

}
