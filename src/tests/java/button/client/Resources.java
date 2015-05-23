package button.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;


public interface Resources extends ClientBundle {

    public Resources INSTANCE = GWT.create(Resources.class);

    @Source("color.png")
    ImageResource colorImage();

    @Source("trash.png")
    ImageResource trashImage();

}
