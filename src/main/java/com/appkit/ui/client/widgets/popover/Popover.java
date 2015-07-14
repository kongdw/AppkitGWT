package com.appkit.ui.client.widgets.popover;

import com.appkit.geometry.shared.Size;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
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

    public enum PopoverArrowDirection {NONE, UP, RIGHT, DOWN, LEFT}

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

            arrow.removeClassName(appearance.css().upArrowClass());
            arrow.removeClassName(appearance.css().leftArrowClass());
            arrow.removeClassName(appearance.css().downArrowClass());
            arrow.removeClassName(appearance.css().rightArrowClass());

            arrow.getStyle().setDisplay(Style.Display.BLOCK);

            switch (arrowDirection) {
                case UP:
                    arrow.addClassName(appearance.css().upArrowClass());
                    break;
                case LEFT:
                    arrow.addClassName(appearance.css().leftArrowClass());
                    break;
                case DOWN:
                    arrow.addClassName(appearance.css().downArrowClass());
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

    public void showAndPointToPosition(int left, int top) {
        final int l = left;
        final int t = top;

        setVisible(true);
        //get dimensions
        final Size arrowSize = new Size(arrow.getOffsetWidth(), arrow.getOffsetHeight());
        final Size panelSize = new Size(panel.getOffsetWidth(), panel.getOffsetHeight());

        setVisible(false);

        Scheduler.ScheduledCommand command = new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {

                int finalTop = t;
                int finalLeft = l;

                switch (arrowDirection) {
                    case UP: {
                        finalTop += arrowSize.getHeight();
                        finalLeft -= (panelSize.getWidth() / 2);
                    }
                    break;
                    case DOWN: {
                        finalTop -= (panelSize.getHeight() + arrowSize.getHeight());
                        finalLeft -= (panelSize.getWidth() / 2);
                    }
                    break;
                    case LEFT: {
                        finalLeft += (arrowSize.getWidth());
                        finalTop -= (panelSize.getHeight() / 2);
                    }
                    break;
                    case RIGHT: {
                        finalLeft -= (panelSize.getWidth() + arrowSize.getWidth());
                        finalTop -= (panelSize.getHeight() / 2);
                    }
                    break;
                }

                panel.getElement().getStyle().setLeft(finalLeft, Style.Unit.PX);
                panel.getElement().getStyle().setTop(finalTop, Style.Unit.PX);

                setVisible(true);

            }
        };

        Scheduler.get().scheduleDeferred(command);
    }

    public void setPosition(int left, int top) {
        panel.getElement().getStyle().setLeft(left, Style.Unit.PX);
        panel.getElement().getStyle().setTop(top, Style.Unit.PX);
    }

    /**
     * @param isTransient - if true popover will disappear on mouse down
     */

    public void setTransient(boolean isTransient) {

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

    /**
     *
     * @return boolean - true if transient, false otherwise.
     */

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

    public void fadeIn() {
        doFadeIn(this, getElement());
    }

    private native void doFadeIn(Popover popover, Element el)/*-{

        $wnd.jQuery(el).fadeIn(function () {
            popover.@com.appkit.ui.client.widgets.popover.Popover::setVisible(Z)(true);
        });

    }-*/;

    public void fadeOut() {
        doFadeOut(this, getElement());
    }

    private native void doFadeOut(Popover popover, Element el)/*-{

        $wnd.jQuery(el).fadeOut(function () {
            popover.@com.appkit.ui.client.widgets.popover.Popover::setVisible(Z)(false);
        });

    }-*/;


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
