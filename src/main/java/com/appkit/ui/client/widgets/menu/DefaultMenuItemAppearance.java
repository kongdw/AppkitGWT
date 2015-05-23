package com.appkit.ui.client.widgets.menu;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public class DefaultMenuItemAppearance implements MenuItemAppearance {

    static {
        Resources.INSTANCE.css().ensureInjected();
    }

    public interface Resources extends ClientBundle {

        public Resources INSTANCE = GWT.create(Resources.class);

        @Source("menuitem.css")
        MenuItemCss css();

        @Source("check.png")
        @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
        ImageResource iconCheck();

        @Source("check-highlight.png")
        @ImageResource.ImageOptions(repeatStyle = ImageResource.RepeatStyle.None)
        ImageResource iconCheckHighlight();
    }

    public MenuItemCss css() {
        return Resources.INSTANCE.css();
    }

}
