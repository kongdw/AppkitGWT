package com.appkit.ui.client.widgets.button;


import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Image;

public class ImageButton extends Button {

    public ImageButton(ButtonAppearance appearance) {

        super(appearance);
    }

    public ImageButton() {
        super();
        getElement().addClassName(getAppearance().css().imageButtonClass());
    }

    public ImageButton(Image image) {
        super();
        setIcon(image);

        getElement().addClassName(getAppearance().css().imageButtonClass());
    }


    @Override
    public void setIcon(Image image) {

        if (image != null) {
            icon = image;

            if (imageElement == null) {
                imageElement = DOM.createDiv();
                imageElement.setClassName(getAppearance().css().buttonImageClass());
                imageElement.addClassName("appkit-button-icon");
                if (titleElement != null) {
                    DOM.insertBefore(contentWrap, imageElement, titleElement);
                } else {
                    contentWrap.appendChild(imageElement);
                }
            }

            imageElement.getStyle().setWidth(100, Style.Unit.PCT);
            imageElement.getStyle().setHeight(100, Style.Unit.PCT);

            imageElement.appendChild(icon.getElement());
        } else {
            if (imageElement != null) {
                imageElement.removeFromParent();
                imageElement = null;

            }
        }
    }

}
