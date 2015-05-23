package com.appkit.ui.client.widgets.popover;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

import java.util.Iterator;

public class Popover extends Composite implements HasWidgets {

    public enum PopoverArrowDirection {NONE, TOP, RIGHT, BOTTOM, LEFT}

    private FlowPanel panel;

    private Element arrow;

    private boolean isTransient = false;
    private PopoverArrowDirection arrowDirection;

    private HandlerRegistration popoverMouseDownRegistration;
    private HandlerRegistration popoverTouchStartRegistration;

    private PopoverAppearance appearance;
    private static PopoverAppearance DEFAULT_APPEARANCE = GWT.create(DefaultPopoverAppearance.class);

    private static Popover openTransientPopover = null;

    public Popover(PopoverAppearance appearance) {

        this.appearance = appearance;

        render();
    }

    @UiConstructor
    public Popover() {
        this(DEFAULT_APPEARANCE);
    }

    static {
        RootPanel.get().addDomHandler(
                new MouseDownHandler() {
                    @Override
                    public void onMouseDown(MouseDownEvent event) {
                        if (openTransientPopover != null) {
                            openTransientPopover.setVisible(false);
                            openTransientPopover = null;
                        }
                    }
                }, MouseDownEvent.getType()
        );

        RootPanel.get().addDomHandler(
                new TouchStartHandler() {
                    @Override
                    public void onTouchStart(TouchStartEvent event) {
                        if (openTransientPopover != null) {
                            openTransientPopover.setVisible(false);
                            openTransientPopover = null;
                        }
                    }
                }, TouchStartEvent.getType()
        );

        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
                if (openTransientPopover != null) {
                    openTransientPopover.setVisible(false);
                    openTransientPopover = null;
                }
            }
        });
    }

    public static void closeTransientPopoverIfNecessary(Widget sender) {
        if (sender != null) {
            if (openTransientPopover != null) {
                Widget parent = sender.getParent();
                while (!parent.equals(RootPanel.get())) {
                    if (parent.equals(openTransientPopover)) {
                        return;
                    }
                    parent = parent.getParent();
                }

                Popover.getCurrentTransientPopover().setVisible(false);
            }
        }

    }

    public static Popover getCurrentTransientPopover() {
        return openTransientPopover;
    }

    private void render() {

        panel = new FlowPanel();
        initWidget(panel);

        panel.getElement().addClassName(appearance.css().popoverClass());
        panel.getElement().addClassName("appkit-popover");

        arrow = DOM.createDiv();
        arrow.setClassName(appearance.css().arrowClass());

        getElement().appendChild(arrow);

        setArrowDirection(PopoverArrowDirection.NONE);
        setVisible(false);

        RootPanel.get().add(this);
    }

    public void setArrowDirection(PopoverArrowDirection direction) {

        if (arrowDirection != direction) {

            arrowDirection = direction;

            arrow.removeClassName(appearance.css().topArrowClass());
            arrow.removeClassName(appearance.css().leftArrowClass());
            arrow.removeClassName(appearance.css().bottomArrowClass());
            arrow.removeClassName(appearance.css().rightArrowClass());

            arrow.getStyle().setDisplay(Style.Display.BLOCK);

            switch (arrowDirection) {
                case TOP:
                    arrow.addClassName(appearance.css().topArrowClass());
                    break;
                case LEFT:
                    arrow.addClassName(appearance.css().leftArrowClass());
                    break;
                case BOTTOM:
                    arrow.addClassName(appearance.css().bottomArrowClass());
                    break;
                case RIGHT:
                    arrow.addClassName(appearance.css().rightArrowClass());
                    break;
                case NONE:
                    arrow.getStyle().setDisplay(Style.Display.NONE);
                    break;
            }
        }
    }

    public PopoverArrowDirection getArrowDirection() {
        return arrowDirection;
    }

    public void setPosition(int left, int top) {

        boolean visible = isVisible();
        setVisible(true);

        panel.getElement().getStyle().setLeft(left - getOffsetWidth() / 2.0, Style.Unit.PX);
        panel.getElement().getStyle().setTop(top, Style.Unit.PX);

        setVisible(visible);
    }

    public void setIsTransient(boolean isTransient) {

        if (this.isTransient != isTransient) {
            this.isTransient = isTransient;

            if (popoverMouseDownRegistration != null) {
                popoverMouseDownRegistration.removeHandler();
            }

            if (popoverTouchStartRegistration != null) {
                popoverTouchStartRegistration.removeHandler();
            }

            if (this.isTransient) {

                popoverMouseDownRegistration = panel.addDomHandler(
                        new MouseDownHandler() {
                            @Override
                            public void onMouseDown(MouseDownEvent event) {
                                event.stopPropagation();
                            }
                        }, MouseDownEvent.getType()
                );

                popoverTouchStartRegistration = panel.addDomHandler(
                        new TouchStartHandler() {
                            @Override
                            public void onTouchStart(TouchStartEvent event) {
                                event.stopPropagation();
                            }
                        }, TouchStartEvent.getType()
                );
            }
        }
    }

    public boolean isTransient() {
        return isTransient;
    }

    @Override
    public void setVisible(boolean visible) {

        boolean wasVisible = isVisible();

        super.setVisible(visible);

        if (isTransient()) {
            if (visible) {
                openTransientPopover = this;
            } else {
                if (wasVisible) {
                    openTransientPopover = null;
                }
            }
        }

    }

    @Override
    public void add(Widget w) {
        panel.add(w);
    }

    @Override
    public void clear() {
        panel.clear();
    }

    @Override
    public Iterator<Widget> iterator() {
        return panel.iterator();
    }

    @Override
    public boolean remove(Widget w) {
        return panel.remove(w);
    }
}
