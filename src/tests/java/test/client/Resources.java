package test.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface Resources extends ClientBundle {

    public Resources INSTANCE = GWT.create(Resources.class);

    @Source("home.png")
    ImageResource homeImage();

    @Source("trash.png")
    ImageResource trashImage();

    @Source("trash-active.png")
    ImageResource trashActiveImage();

    @Source("color.png")
    ImageResource colorImage();

    @Source("color-active.png")
    ImageResource colorActiveImage();
}
