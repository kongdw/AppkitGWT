package com.appkit.interactions.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;


public interface StyleResources extends ClientBundle {

    static StyleResources INSTANCE = GWT.create(StyleResources.class);

    @Source("interaction.css")
    InteractionsCss interactionCSS();

    @Source("ResizeIndicator.png")
    ImageResource resizeIndicatorImage();

}
