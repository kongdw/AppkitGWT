package com.appkit.ui.client.widgets.toolbar;


import com.appkit.util.client.DOMHelper;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ToolbarButton extends Composite implements ToolbarItem,
        HasText, HasClickHandlers, HasEnabled {

    private ToolbarControl item;
    private Image image = null;
    private Image activeImage = null;

    private Map<HandlerRegistration, ClickHandler> clickHandlers = new HashMap<HandlerRegistration, ClickHandler>();

    public ToolbarButton() {

        item = new ToolbarControl();

        initWidget(item);

        getElement().setTabIndex(0);
        getElement().setAttribute("role", "button");
        getElement().addClassName("appkit-toolbar-button");

        addDomHandler(new MouseDownHandler() {
            @Override
            public void onMouseDown(MouseDownEvent event) {

                event.preventDefault();

                if (!isEnabled()) {
                    return;
                }

                if (event.getNativeButton() == Event.BUTTON_LEFT) {

                    DOM.setCapture(getElement());

                    getElement().addClassName("appkit-state-active");
                    if (activeImage != null) {
                        image.setVisible(false);
                        activeImage.setVisible(true);
                    }
                }


            }
        }, MouseDownEvent.getType());


        addDomHandler(new MouseUpHandler() {
            @Override
            public void onMouseUp(MouseUpEvent event) {
                DOM.releaseCapture(getElement());
                getElement().removeClassName("appkit-state-active");

                if (activeImage != null) {
                    image.setVisible(true);
                    activeImage.setVisible(false);
                }

                if (isEnabled()) {
                    if (DOMHelper.isMouseOverElement(event, getElement())) {
                        Collection<ClickHandler> handlers = clickHandlers.values();
                        for (ClickHandler handler : handlers) {
                            handler.onClick(new ClickEvent() {
                            }
                            );
                        }
                    }
                }

            }
        }, MouseUpEvent.getType());


        addDomHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {

                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    getElement().addClassName("appkit-state-active");
                    if (activeImage != null) {
                        image.setVisible(false);
                        activeImage.setVisible(true);
                    }
                }
            }
        }, KeyDownEvent.getType());


        addDomHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                getElement().removeClassName("appkit-state-active");

                if (activeImage != null) {
                    image.setVisible(true);
                    activeImage.setVisible(false);
                }

                if (isEnabled()) {
                    Collection<ClickHandler> handlers = clickHandlers.values();
                    for (ClickHandler handler : handlers) {
                        handler.onClick(new ClickEvent() {
                        }
                        );
                    }
                }
            }
        }, KeyUpEvent.getType());

    }


    public void setIconImage(Image image) {

        if (this.image != null) {
            this.image.removeFromParent();
        }

        this.image = image;

        if (this.image != null) {

            this.image.setAltText(getText());
            this.image.setPixelSize(32, 32);
            this.image.getElement().setAttribute("width", "32");
            this.image.getElement().setAttribute("height", "32");

            item.setWidget(this.image);

            if (this.activeImage != null) {
                this.activeImage.removeFromParent();
                this.image.getElement().getParentElement().appendChild(this.activeImage.getElement());
            }
        }

    }

    public void setActiveIconImage(Image image) {

        if (this.activeImage != null) {
            this.activeImage.removeFromParent();
        }

        this.activeImage = image;

        if (this.activeImage != null) {

            this.activeImage.setPixelSize(32, 32);
            this.activeImage.getElement().setAttribute("width", "32");
            this.activeImage.getElement().setAttribute("height", "32");
            this.activeImage.setVisible(false);

            if (this.image != null) {
                this.image.getElement().getParentElement().appendChild(this.activeImage.getElement());
            }
        }

    }

    public void setIcon(ImageResource imageResource) {

        if (imageResource != null) {
            setIconImage(new Image(imageResource));
        }

    }

    public void setActiveIcon(ImageResource imageResource) {

        if (imageResource != null) {
            setActiveIconImage(new Image(imageResource));
        }

    }

    public Image getIcon() {
        return image;
    }


    @Override
    public String getText() {
        return item.getText();
    }

    @Override
    public void setText(String text) {

        item.setText(text);

        if (this.image != null) {
            this.image.setAltText(getText());
        }
    }

    public void setEnabled(boolean enabled) {

        item.setEnabled(enabled);

        if (enabled) {
            getElement().setTabIndex(0);
        } else {
            getElement().setTabIndex(-2);
        }

    }

    public boolean isEnabled() {
        return item.isEnabled();
    }

    @Override
    public void onBrowserEvent(Event event) {

        if (event.getTypeInt() == Event.ONCLICK) {
            event.preventDefault();
            return;
        }

        super.onBrowserEvent(event);
    }

    @Override
    public HandlerRegistration addClickHandler(ClickHandler handler) {

        HandlerRegistration registration = new HandlerRegistration() {
            @Override
            public void removeHandler() {
                clickHandlers.remove(this);
            }
        };

        clickHandlers.put(registration, handler);
        return registration;
    }
}
