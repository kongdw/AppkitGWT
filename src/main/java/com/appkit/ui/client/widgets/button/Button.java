package com.appkit.ui.client.widgets.button;

import com.appkit.ui.client.events.tap.TapEvent;
import com.appkit.ui.client.events.tap.TapHandler;
import com.appkit.ui.client.events.touch.TouchHandler;
import com.appkit.ui.client.widgets.Control;
import com.appkit.ui.client.widgets.menu.Menu;
import com.appkit.ui.client.widgets.popover.Popover;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;


public class Button extends Control {

    public enum ButtonStyle {DEFAULT, PRIMARY, DANGER, INFO, SUCCESS, WARNING, INVERSE, LINK}

    protected Element titleElement = null;
    protected Element imageElement = null;
    protected Element contentWrap = null;
    protected Image icon = null;

    private boolean continuous = false;
    private Timer continuousTimer = null;

    private ButtonAppearance appearance;
    private ButtonStyle style;

    static ButtonAppearance DEFAULT_APPEARANCE = GWT.create(DefaultButtonAppearance.class);

    public Button(ButtonAppearance appearance) {
        super(DOM.createDiv());
        this.appearance = appearance;
        render();
    }

    public Button(String title) {

        this(DEFAULT_APPEARANCE);
        setText(title);

    }

    public Button() {
        this(DEFAULT_APPEARANCE);

    }

    private void render() {

        getElement().setAttribute("role", "button");
        getElement().setClassName(appearance.css().buttonClass());
        getElement().addClassName("appkit-button");

        contentWrap = DOM.createDiv();
        contentWrap.setClassName(appearance.css().buttonContentWrapClass());

        getElement().appendChild(contentWrap);

        addMouseDownHandler(new MouseDownHandler() {
            @Override
            public void onMouseDown(MouseDownEvent event) {

                event.preventDefault();

                if (!isEnabled())
                    return;

                if (event.getNativeButton() == Event.BUTTON_LEFT) {

                    event.stopPropagation();

                    DOM.setCapture(getElement());

                    getElement().addClassName("appkit-state-active");

                    if (continuous) {
                        continuousTimer = new Timer() {
                            @Override
                            public void run() {
                                Button.this.fireEvent(new ClickEvent() {
                                });
                            }
                        };


                        Timer t = new Timer() {
                            @Override
                            public void run() {
                                if (continuousTimer != null) {
                                    continuousTimer.scheduleRepeating(150);
                                }

                            }
                        };

                        t.schedule(400);
                    }

                    //need to close transient popover (unless its the parent) or menu
                    // since we are stopping propagation
                    Popover.closeTransientPopoverIfNecessary(Button.this);
                    Menu.closeCurrentlyOpenMenuIfNecessary();

                }
            }
        });

        addMouseUpHandler(new MouseUpHandler() {
            @Override
            public void onMouseUp(MouseUpEvent event) {
                DOM.releaseCapture(getElement());
                getElement().removeClassName("appkit-state-active");

                if (continuousTimer != null) {
                    continuousTimer.cancel();
                    continuousTimer = null;
                }
            }
        });

        addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {

                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    event.preventDefault();

                    if (!isEnabled())
                        return;

                    DOM.setCapture(getElement());
                    getElement().addClassName("appkit-state-active");
                }


            }
        });

        addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    DOM.releaseCapture(getElement());
                    getElement().removeClassName("appkit-state-active");


                    if (!isEnabled())
                        return;

                    Button.this.fireEvent(new ClickEvent() {
                    });
                }
            }
        });

        addTouchHandler(new TouchHandler() {
            @Override
            public void onTouchCancel(TouchCancelEvent event) {

            }

            @Override
            public void onTouchEnd(TouchEndEvent event) {
                DOM.releaseCapture(getElement());
                getElement().removeClassName("appkit-state-active");

                if (continuousTimer != null) {
                    continuousTimer.cancel();
                    continuousTimer = null;
                }

            }

            @Override
            public void onTouchMove(TouchMoveEvent event) {

            }

            @Override
            public void onTouchStart(TouchStartEvent event) {

                event.preventDefault();
                event.stopPropagation();

                if (!isEnabled())
                    return;

                DOM.setCapture(getElement());
                getElement().addClassName("appkit-state-active");

                if (continuous) {
                    continuousTimer = new Timer() {
                        @Override
                        public void run() {
                            Button.this.fireEvent(new ClickEvent() {
                            });
                        }
                    };


                    Timer t = new Timer() {
                        @Override
                        public void run() {
                            if (continuousTimer != null)
                                continuousTimer.scheduleRepeating(150);
                        }
                    };

                    t.schedule(400);
                }

                Popover.closeTransientPopoverIfNecessary(Button.this);
                Menu.closeCurrentlyOpenMenuIfNecessary();
            }
        });

        addTapHandler(new TapHandler() {
            @Override
            public void onTap(TapEvent event) {
                if (!isEnabled())
                    return;

                Button.this.fireEvent(new ClickEvent() {
                });
            }
        });

    }

    public ButtonAppearance getAppearance() {
        return appearance;
    }

    public void setButtonStyle(ButtonStyle style1) {

        getElement().removeClassName(appearance.css().primaryButtonClass());
        getElement().removeClassName(appearance.css().infoButtonClass());
        getElement().removeClassName(appearance.css().inverseButtonClass());
        getElement().removeClassName(appearance.css().warningButtonClass());
        getElement().removeClassName(appearance.css().successButtonClass());
        getElement().removeClassName(appearance.css().dangerButtonClass());
        getElement().removeClassName(appearance.css().linkButtonClass());


        if (style1 == ButtonStyle.PRIMARY) {
            getElement().addClassName(appearance.css().primaryButtonClass());
        } else if (style1 == ButtonStyle.INFO) {
            getElement().addClassName(appearance.css().infoButtonClass());
        } else if (style1 == ButtonStyle.INVERSE) {
            getElement().addClassName(appearance.css().inverseButtonClass());
        } else if (style1 == ButtonStyle.WARNING) {
            getElement().addClassName(appearance.css().warningButtonClass());
        } else if (style1 == ButtonStyle.SUCCESS) {
            getElement().addClassName(appearance.css().successButtonClass());
        } else if (style1 == ButtonStyle.DANGER) {
            getElement().addClassName(appearance.css().dangerButtonClass());
        } else if (style1 == ButtonStyle.LINK) {
            getElement().addClassName(appearance.css().linkButtonClass());
        }

        this.style = style1;

    }

    public ButtonStyle getButtonStyle() {
        return style;
    }

    public void setIcon(Image image) {

        if (image != null) {
            this.icon = image;

            if (this.icon != null) {

                this.icon.setSize("16px", "16px");
                if (imageElement == null) {
                    imageElement = DOM.createDiv();
                    imageElement.setClassName(appearance.css().buttonImageClass());
                    imageElement.addClassName("appkit-button-icon");
                    if (titleElement != null) {
                        DOM.insertBefore(contentWrap, imageElement, titleElement);
                    } else {
                        contentWrap.appendChild(imageElement);
                    }
                }

                imageElement.appendChild(this.icon.getElement());
                imageElement.getStyle().setWidth(imageElement.getOffsetHeight() + 8, Style.Unit.PX);

                if (titleElement != null) {
                    titleElement.getStyle().setPaddingRight(16.0, Style.Unit.PX);
                }

            }
        } else {
            if (imageElement != null) {
                imageElement.removeFromParent();
                imageElement = null;
                if (titleElement != null) {
                    titleElement.getStyle().setPaddingRight(0, Style.Unit.PX);
                }
            }
        }
    }

    public Image getIcon() {
        return icon;
    }

    public void setText(String title) {

        if (title != null) {

            if (titleElement == null) {

                titleElement = DOM.createDiv();
                titleElement.setClassName(appearance.css().buttonTitleClass());

                contentWrap.appendChild(titleElement);
            }

            titleElement.setInnerHTML(title);

            if (imageElement != null) {
                titleElement.getStyle().setPaddingRight(16.0, Style.Unit.PX);
            } else {
                titleElement.getStyle().setPaddingRight(0.0, Style.Unit.PX);
            }


        } else {
            if (titleElement != null) {
                titleElement.removeFromParent();
                titleElement = null;
            }
        }
    }

    public String getText() {
        return titleElement.getInnerText();
    }


    public void setContinuous(boolean continuous) {
        this.continuous = continuous;
    }

    public boolean isContinuous() {
        return continuous;
    }

    @Override
    public void setEnabled(boolean enabled) {

        super.setEnabled(enabled);

        if (!this.isEnabled()) {
            getElement().addClassName("appkit-state-disabled");
            setTabIndex(-2);
        } else {
            getElement().removeClassName("appkit-state-disabled");
            if (getCanFocus()) {
                setTabIndex(0);
            } else {
                setTabIndex(-2);
            }

        }
    }

    @Override
    public void onBrowserEvent(Event event) {
        if (event.getTypeInt() == Event.ONCLICK) {

            if (!isEnabled()) {
                event.preventDefault();
                return;
            }
        }

        super.onBrowserEvent(event);
    }

}
