package com.appkit.interactions.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.HasResizeHandlers;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;


public class ResizeController implements MouseMoveHandler, MouseUpHandler, TouchMoveHandler, TouchEndHandler {

    private boolean resizing;
    private boolean resizeStarted;
    private double mouseDownStartDX;
    private double mouseDownStartDY;

    private double minWidth;
    private double minHeight;
    private double maxWidth;
    private double maxHeight;

    private Element resizerEl = null;
    private Widget widget = null;

    private HandlerRegistration mouseMoveHandlerRegistration;
    private HandlerRegistration mouseUpHandlerRegistration;
    private HandlerRegistration touchMoveHandlerRegistration;
    private HandlerRegistration touchEndHandlerRegistration;


    public ResizeController() {

        StyleResources.INSTANCE.interactionCSS().ensureInjected();

        minWidth = 50.0;
        maxWidth = Double.MAX_VALUE;

        minHeight = 50.0;
        maxHeight = Double.MAX_VALUE;
    }

    public double getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(double minWidth) {
        this.minWidth = minWidth;
    }

    public double getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(double minHeight) {
        this.minHeight = minHeight;
    }

    public double getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(double maxWidth) {
        this.maxWidth = maxWidth;
    }

    public double getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(double maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void makeResizable(Widget w) {

        removeResizable();

        widget = w;

        if (widget != null) {

            widget.getElement().getStyle().setPosition(Style.Position.RELATIVE);

            widget.sinkEvents(Event.ONMOUSEMOVE | Event.ONMOUSEUP | Event.ONTOUCHMOVE | Event.ONTOUCHEND);

            /* add resizer div */
            resizerEl = DOM.createDiv();
            resizerEl.addClassName(StyleResources.INSTANCE.interactionCSS().resizeIndicatorClass());

            DOM.sinkEvents(resizerEl, Event.ONMOUSEDOWN | Event.ONTOUCHSTART);
            DOM.setEventListener(resizerEl, new EventListener() {

                @Override
                public void onBrowserEvent(Event event) {
                    if (event.getTypeInt() == Event.ONMOUSEDOWN) {
                        event.preventDefault();
                        event.stopPropagation();

                        mouseDownStartDX = widget.getAbsoluteLeft() + widget.getElement().getClientWidth() - event.getClientX();
                        mouseDownStartDY = widget.getAbsoluteTop() + widget.getElement().getClientHeight() - event.getClientY();

                        resizing = true;

                        DOM.setCapture(widget.getElement());

                    } else if (event.getTypeInt() == Event.ONTOUCHSTART) {

                        event.preventDefault();
                        event.stopPropagation();

                        if (event.getTouches().length() > 0) {

                            Touch touch = event.getTouches().get(0);

                            mouseDownStartDX = widget.getElement().getClientWidth() - touch.getRelativeX(widget.getElement());
                            mouseDownStartDY = widget.getElement().getClientHeight() - touch.getRelativeY(widget.getElement());

                            resizing = true;

                            DOM.setCapture(widget.getElement());

                        }


                    }
                }
            });

            widget.getElement().appendChild(resizerEl);

            if (widget instanceof HasMouseMoveHandlers) {
                mouseMoveHandlerRegistration = ((HasMouseMoveHandlers) widget).addMouseMoveHandler(this);
            }

            if (widget instanceof HasMouseUpHandlers) {
                mouseUpHandlerRegistration = ((HasMouseUpHandlers) widget).addMouseUpHandler(this);
            }

            if (widget instanceof HasTouchMoveHandlers) {
                touchMoveHandlerRegistration = ((HasTouchMoveHandlers) widget).addTouchMoveHandler(this);
            }

            if (widget instanceof HasTouchEndHandlers) {
                touchEndHandlerRegistration = ((HasTouchEndHandlers) widget).addTouchEndHandler(this);
            }
        }
    }

    public void removeResizable() {

        if (resizerEl != null) {
            resizerEl.removeFromParent();
        }

        if (mouseMoveHandlerRegistration != null) {
            mouseMoveHandlerRegistration.removeHandler();
        }

        if (mouseUpHandlerRegistration != null) {
            mouseUpHandlerRegistration.removeHandler();
        }

        if (touchMoveHandlerRegistration != null) {
            touchMoveHandlerRegistration.removeHandler();
        }

        if (touchEndHandlerRegistration != null) {
            touchEndHandlerRegistration.removeHandler();
        }

        widget = null;

    }

    @Override
    public void onMouseMove(MouseMoveEvent event) {

        if (resizing && widget != null) {

            RootPanel.getBodyElement().getStyle().setCursor(Style.Cursor.SE_RESIZE);

            double x = event.getClientX() + Window.getScrollLeft()
                    - widget.getAbsoluteLeft() + mouseDownStartDX;

            double y = event.getClientY() + Window.getScrollTop()
                    - widget.getAbsoluteTop() + mouseDownStartDY;

            if (x >= minWidth && x <= maxWidth) {
                widget.setWidth(x + "px");
            }

            if (y >= minHeight && y <= maxHeight) {
                widget.setHeight(y + "px");
            }

            if (widget instanceof RequiresResize) {
                ((RequiresResize) widget).onResize();
            }

            if (widget instanceof HasResizeHandlers) {
                ResizeEvent.fire(((HasResizeHandlers) widget), widget.getOffsetWidth(), widget.getOffsetHeight());
            }


            if (!resizeStarted) {
                resizeStarted = true;
            }
        }
    }

    @Override
    public void onMouseUp(MouseUpEvent event) {

        resizing = false;
        resizeStarted = false;

        RootPanel.getBodyElement().getStyle().setCursor(Style.Cursor.DEFAULT);

        if (widget != null) {
            DOM.releaseCapture(widget.getElement());
        }


    }


    @Override
    public void onTouchEnd(TouchEndEvent event) {
        resizing = false;
        resizeStarted = false;

        RootPanel.getBodyElement().getStyle().setCursor(Style.Cursor.DEFAULT);

        if (widget != null) {
            DOM.releaseCapture(widget.getElement());
        }
    }

    @Override
    public void onTouchMove(TouchMoveEvent event) {

        if (resizing && widget != null) {

            if (event.getTouches().length() > 0) {

                Touch touch = event.getTouches().get(0);

                double x = touch.getRelativeX(widget.getElement()) + Window.getScrollLeft()
                        + mouseDownStartDX;

                double y = touch.getRelativeY(widget.getElement()) + Window.getScrollTop()
                        + mouseDownStartDY;

                if (x >= minWidth && x <= maxWidth) {
                    widget.setWidth(x + "px");
                }

                if (y >= minHeight && y <= maxHeight) {
                    widget.setHeight(y + "px");
                }

                if (widget instanceof RequiresResize) {
                    ((RequiresResize) widget).onResize();
                }

                if (widget instanceof HasResizeHandlers) {
                    ResizeEvent.fire(((HasResizeHandlers) widget), widget.getOffsetWidth(), widget.getOffsetHeight());
                }

                if (!resizeStarted) {
                    resizeStarted = true;
                }
            }

        }
    }
}
