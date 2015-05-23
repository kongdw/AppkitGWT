package com.appkit.ui.client.widgets.windowpanel;

import com.appkit.geometry.shared.Point;
import com.appkit.geometry.shared.Rectangle;
import com.appkit.geometry.shared.Size;
import com.appkit.interactions.client.ResizeController;
import com.appkit.interactions.client.StyleResources;
import com.appkit.ui.client.events.maximize.HasMaximizeHandlers;
import com.appkit.ui.client.events.maximize.MaximizeEvent;
import com.appkit.ui.client.events.maximize.MaximizeHandler;
import com.appkit.ui.client.events.minimize.HasMinimizeHandlers;
import com.appkit.ui.client.events.minimize.MinimizeEvent;
import com.appkit.ui.client.events.minimize.MinimizeHandler;
import com.appkit.ui.client.events.restore.HasRestoreSizeHandlers;
import com.appkit.ui.client.events.restore.RestoreSizeEvent;
import com.appkit.ui.client.events.restore.RestoreSizeHandler;
import com.appkit.ui.client.events.tap.TapEvent;
import com.appkit.ui.client.events.tap.TapHandler;
import com.appkit.ui.client.events.touch.TouchHandler;
import com.appkit.ui.client.util.DOMHelper;
import com.appkit.ui.client.widgets.menu.DefaultMenuAppearance;
import com.appkit.ui.client.widgets.menu.Menu;
import com.appkit.ui.client.widgets.popover.Popover;
import com.appkit.ui.client.widgets.touch.TouchFocusWidget;
import com.google.gwt.animation.client.Animation;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.*;

import java.util.Iterator;


public class WindowPanel extends Composite implements
        ProvidesResize, RequiresResize, HasWidgets,
        HasMouseDownHandlers, HasMouseUpHandlers, HasMouseMoveHandlers, HasKeyDownHandlers,
        HasResizeHandlers, HasTouchMoveHandlers, HasTouchEndHandlers,
        HasCloseHandlers<WindowPanel>, HasMaximizeHandlers<WindowPanel>,
        HasMinimizeHandlers<WindowPanel>, HasRestoreSizeHandlers<WindowPanel> {


    private static WindowPanel TOP_WINDOW = null;
    private static WindowPanel CURRENT_MODAL = null;
    private static Element MODAL_OVERLAY = null;

    HandlerRegistration windowResizeHandler;

    private AbsolutePanel panel;

    private ResizeController resizeController = null;
    private DragController dragController = null;

    private boolean resizable = false;
    private boolean draggable = false;
    private boolean isMaximized = false;
    private boolean modal = false;

    private int minWidth = 20;
    private int minHeight = 20;
    private int maxWidth = 10000;
    private int maxHeight = 10000;

    private Rectangle lastRestoredFrame = null;
    private WindowPanelTitleBar _titleBar;
    private FlowPanel content;

    private WindowPanelAppearance appearance;
    private static WindowPanelAppearance DEFAULT_appearance = GWT.create(DefaultWindowPanelAppearance.class);

    public WindowPanel(WindowPanelAppearance appearance) {
        this.appearance = appearance;
        render();
    }

    public WindowPanel() {
        this(DEFAULT_appearance);
    }

    private void render() {

        panel = new AbsolutePanel();

        initWidget(panel);

        getElement().setClassName(appearance.css().windowPanelClass());
        getElement().setAttribute("role", "dialog");
        getElement().addClassName("appkit-window-panel");

        _titleBar = new WindowPanelTitleBar(this);
        this.add(_titleBar);

        content = new FlowPanel();
        content.getElement().setClassName(appearance.css().windowPanelContentClass());
        content.getElement().addClassName("appkit-window-panel-content");

        this.add(content);

        this.setDraggable(true);
        this.setMinimizable(false);
        this.setMaximizable(false);

        this.setFrame(0, 0, 200, 200);

        RootPanel.get().add(this);

        addMouseDownHandler(new MouseDownHandler() {
            @Override
            public void onMouseDown(MouseDownEvent event) {
                moveToTop();
            }
        });

        addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {

                if (event.getNativeKeyCode() == KeyCodes.KEY_TAB) {
                    if (modal) {
                        event.preventDefault();
                        keepFocus();
                    }
                }

                if (event.getNativeKeyCode() == KeyCodes.KEY_X) {
                    if (event.isControlKeyDown()) {
                        event.preventDefault();
                        close();
                    }
                }

            }
        });
    }

    public WindowPanelAppearance getAppearance() {
        return appearance;
    }

    public void add(Widget w) {
        if (panel.getWidgetCount() < 2) {
            panel.add(w);
        } else {
            content.add(w);
        }
    }

    @Override
    public void clear() {
        content.clear();
    }

    @Override
    public Iterator<Widget> iterator() {
        return content.iterator();
    }

    @Override
    public boolean remove(Widget w) {
        return content.remove(w);
    }

    public Rectangle getFrame() {
        return new Rectangle(getPosition(), getSize());
    }

    public void setFrame(int x, int y, int w, int h) {

        setFramePosition(x, y);
        setFrameSize(w, h);
    }

    public void center() {

        boolean isVisible = isVisible();

        setVisible(true);

        final int maxX = RootPanel.get().getOffsetWidth();
        final int maxY = RootPanel.get().getOffsetHeight();

        setFramePosition((maxX - getFrameWidth()) / 2, (maxY - getFrameHeight()) / 2 - 32);

        setVisible(isVisible);
    }

    public boolean getDraggable() {
        return draggable;
    }

    public void setDraggable(boolean draggable) {

        if (this.draggable != draggable) {
            this.draggable = draggable;

            if (this.draggable) {

                if (dragController == null) {
                    dragController = new DragController();
                }

                dragController.makeDraggable(this, _titleBar);
                dragController.setContainInParent(true);

                _titleBar.getElement().addClassName(appearance.css().windowPanelDraggableClass());

            } else {
                dragController.removeDraggable();
                _titleBar.getElement().removeClassName(appearance.css().windowPanelDraggableClass());
            }
        }
    }

    public String getTitle() {
        return _titleBar.getTitleBarText();
    }

    public void setTitle(String title) {
        _titleBar.setTitleBarText(title);
    }

    public boolean getResizable() {
        return resizable;
    }

    public void setResizable(boolean resizable) {

        if (this.resizable != resizable) {

            this.resizable = resizable;

            if (this.resizable) {

                if (resizeController == null) {
                    resizeController = new ResizeController();
                    resizeController.setMaxWidth(maxWidth);
                    resizeController.setMinWidth(minWidth);
                    resizeController.setMaxHeight(maxHeight);
                    resizeController.setMinHeight(minHeight);
                }
                resizeController.makeResizable(this);
            } else {
                resizeController.removeResizable();
            }
        }
    }

    public boolean getModal() {
        return modal;
    }

    public void setModal(boolean modal) {

        if (this.modal != modal) {
            this.modal = modal;

            if (!this.modal) {

                destroyModalOverlay();
            }

        }
    }

    public boolean isClosable() {
        return _titleBar.getCloseVisible();
    }

    public void setClosable(boolean close) {
        _titleBar.setShowClose(close);
    }

    public boolean isMaximizable() {
        return _titleBar.getMaximizeVisible();
    }

    public void setMaximizable(boolean max) {
        _titleBar.setShowMaximize(max);
    }

    public boolean isMinimizable() {
        return _titleBar.getMinimizeVisible();
    }

    public void setMinimizable(boolean min) {
        _titleBar.setShowMinimize(min);
    }

    public int getFrameWidth() {
        return getOffsetWidth();
    }

    public void setFrameWidth(int w) {
        if (w >= 0) {
            DOMHelper.setOffsetWidth(this, w);
            onResize();
        }
    }

    public int getFrameHeight() {
        return getOffsetHeight();
    }

    public void setFrameHeight(int h) {
        if (h >= 0) {
            DOMHelper.setOffsetHeight(this, h);
            onResize();
        }
    }

    public Point getPosition() {
        return new Point(getAbsoluteLeft(), getAbsoluteTop());
    }

    public void setFramePosition(int x, int y) {
        DOMHelper.setLeft(this, x);
        DOMHelper.setTop(this, y);
    }

    public Size getSize() {
        return new Size(getFrameWidth(), getFrameHeight());
    }

    public void setFrameSize(int w, int h) {
        if (w >= 0) {
            setFrameWidth(w);
        }

        if (h >= 0) {
            setFrameHeight(h);
        }
    }

    public int getMinWidth() {
        if (resizeController != null) {
            return (int) resizeController.getMinWidth();
        }

        return getFrameWidth();
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
        if (resizeController != null) {
            resizeController.setMinWidth(minWidth);
        }
    }

    public int getMinHeight() {
        if (resizeController != null) {
            return (int) resizeController.getMinHeight();
        }

        return getFrameHeight();
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
        if (resizeController != null) {
            resizeController.setMinHeight(minHeight);
        }
    }

    public int getMaxWidth() {
        if (resizeController != null) {
            return (int) resizeController.getMaxWidth();
        }

        return getFrameHeight();
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        if (resizeController != null) {
            resizeController.setMaxWidth(maxWidth);
        }
    }

    public int getMaxHeight() {
        if (resizeController != null) {
            return (int) resizeController.getMaxHeight();
        }

        return getFrameHeight();
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        if (resizeController != null) {
            resizeController.setMaxHeight(maxHeight);
        }
    }

    public void maximize() {

        if (!isMaximized) {

            final int maxX = RootPanel.get().getOffsetWidth();
            final int maxY = RootPanel.get().getOffsetHeight();

            final WindowPanel theWindow = this;

            lastRestoredFrame = getFrame();

            Animation animation = new Animation() {
                @Override
                protected void onUpdate(double progress) {

                    double left = (1 - progress) * lastRestoredFrame.getLeft();
                    double right = lastRestoredFrame.getRight() + progress * (maxX - lastRestoredFrame.getRight());

                    double top = (1 - progress) * lastRestoredFrame.getTop();
                    double bottom = lastRestoredFrame.getBottom() + progress * (maxY - lastRestoredFrame.getBottom());

                    setFrame((int) left, (int) top, (int) (right - left),
                            (int) (bottom - top));

                    ResizeEvent.fire(theWindow, getFrameWidth(), getFrameHeight());
                    onResize();
                }

                @Override
                protected void onComplete() {
                    setFrame(0, 0, maxX, maxY);
                    isMaximized = true;
                    _titleBar.setMaximized(true);
                    MaximizeEvent.fire(theWindow);

                    windowResizeHandler = Window.addResizeHandler(new ResizeHandler() {
                        @Override
                        public void onResize(ResizeEvent event) {
                            setFrame(0, 0, RootPanel.get().getOffsetWidth(), RootPanel.get().getOffsetHeight());
                        }
                    });

                    ResizeEvent.fire(theWindow, getFrameWidth(), getFrameHeight());
                    onResize();

                }
            };

            animation.run(300);

        }
    }

    public void unmaximize() {

        if (isMaximized) {

            final int maxX = RootPanel.get().getOffsetWidth();
            final int maxY = RootPanel.get().getOffsetHeight();

            final WindowPanel theWindow = this;

            if (windowResizeHandler != null) {
                windowResizeHandler.removeHandler();
            }

            Animation animation = new Animation() {
                @Override
                protected void onUpdate(double progress) {

                    double left = progress * lastRestoredFrame.getLeft();
                    double right = lastRestoredFrame.getRight() + (1 - progress) * (maxX - lastRestoredFrame.getRight());

                    double top = progress * lastRestoredFrame.getTop();
                    double bottom = lastRestoredFrame.getBottom() + (1 - progress) * (maxY - lastRestoredFrame.getBottom());

                    setFrame((int) left, (int) top, (int) (right - left),
                            (int) (bottom - top));

                    ResizeEvent.fire(theWindow, getFrameWidth(), getFrameHeight());
                    onResize();
                }

                @Override
                protected void onComplete() {
                    setFrame((int) lastRestoredFrame.getLeft(), (int) lastRestoredFrame.getTop(),
                            (int) lastRestoredFrame.getWidth(), (int) lastRestoredFrame.getHeight());

                    isMaximized = false;

                    _titleBar.setMaximized(false);

                    RestoreSizeEvent.fire(theWindow);

                    ResizeEvent.fire(theWindow, getFrameWidth(), getFrameHeight());

                    onResize();

                }
            };


            animation.run(300);

        }
    }

    public void close() {

        setVisible(false);

        if (lastRestoredFrame != null && isMaximized) {
            setFrame((int) lastRestoredFrame.getLeft(), (int) lastRestoredFrame.getTop(),
                    (int) lastRestoredFrame.getWidth(), (int) lastRestoredFrame.getHeight());

            _titleBar.setMaximized(false);
            isMaximized = false;
        }


        CloseEvent.fire(this, this);
    }


    private void keepFocus() {
        Timer t = new Timer() {
            @Override
            public void run() {
                Element activeElement = DOMHelper.activeElement();
                boolean active = true;
                if (activeElement != null) {

                    active = getElement().isOrHasChild(activeElement) |
                            activeElement.hasClassName(DefaultMenuAppearance.Resources.INSTANCE.css().menuClass());
                }

                if (!active) {
                    _titleBar.focusOnFirstButton();
                }
            }
        };

        t.schedule(0);
    }

    @Override
    public void setVisible(boolean visible) {

        super.setVisible(visible);

        if (isVisible()) {

            moveToTop();

            if (modal) {

                if (CURRENT_MODAL != null) {
                    CURRENT_MODAL.close();
                }

                createModalOverlay();
                keepFocus();

                CURRENT_MODAL = this;

            }

        } else {

            if (CURRENT_MODAL != null) {
                if (CURRENT_MODAL.equals(this)) {
                    destroyModalOverlay();
                    CURRENT_MODAL = null;
                }
            }

            if (TOP_WINDOW != null) {
                if (TOP_WINDOW.equals(this)) {
                    TOP_WINDOW = null;
                }
            }

            onResize();
        }
    }


    private void createModalOverlay() {

        if (MODAL_OVERLAY == null) {

            MODAL_OVERLAY = DOM.createDiv();
            MODAL_OVERLAY.setClassName(appearance.css().windowPanelOverlayClass());

            DOM.sinkEvents(MODAL_OVERLAY, Event.MOUSEEVENTS);
            DOM.setEventListener(MODAL_OVERLAY, new EventListener() {
                @Override
                public void onBrowserEvent(Event event) {
                    event.stopPropagation();

                    if (event.getTypeInt() == Event.ONMOUSEDOWN) {
                        if (Menu.getCurrentlyOpenMenu() != null) {
                            Menu.getCurrentlyOpenMenu().close();
                        }
                    }


                }
            });

            RootPanel.getBodyElement().appendChild(MODAL_OVERLAY);
        }
    }

    private void destroyModalOverlay() {
        if (MODAL_OVERLAY != null) {
            MODAL_OVERLAY.removeFromParent();
            MODAL_OVERLAY = null;
        }
    }


    public void moveToTop() {

        if (TOP_WINDOW == null) {
            getElement().getStyle().setZIndex(1002);
            TOP_WINDOW = this;
            return;
        }

        if (!TOP_WINDOW.equals(this)) {
            TOP_WINDOW.getElement().getStyle().setZIndex(1001);
            getElement().getStyle().setZIndex(1002);
            TOP_WINDOW = this;
        }

    }


    @Override
    public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
        return addDomHandler(handler, MouseDownEvent.getType());
    }

    @Override
    public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
        return addDomHandler(handler, MouseMoveEvent.getType());
    }

    @Override
    public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
        return addDomHandler(handler, MouseUpEvent.getType());
    }

    @Override
    public HandlerRegistration addCloseHandler(CloseHandler<WindowPanel> handler) {
        return addHandler(handler, CloseEvent.getType());
    }

    @Override
    public HandlerRegistration addMaximizeHandler(MaximizeHandler<WindowPanel> handler) {
        return addHandler(handler, MaximizeEvent.getType());
    }

    @Override
    public HandlerRegistration addMinimizeHandler(MinimizeHandler<WindowPanel> handler) {
        return addHandler(handler, MinimizeEvent.getType());
    }

    @Override
    public HandlerRegistration addRestoreSizeHandler(RestoreSizeHandler<WindowPanel> handler) {
        return addHandler(handler, RestoreSizeEvent.getType());
    }

    @Override
    public HandlerRegistration addKeyDownHandler(KeyDownHandler handler) {
        return addDomHandler(handler, KeyDownEvent.getType());
    }

    @Override
    public HandlerRegistration addResizeHandler(ResizeHandler handler) {
        return addHandler(handler, ResizeEvent.getType());
    }

    @Override
    public HandlerRegistration addTouchEndHandler(TouchEndHandler handler) {
        return addDomHandler(handler, TouchEndEvent.getType());
    }

    @Override
    public HandlerRegistration addTouchMoveHandler(TouchMoveHandler handler) {
        return addDomHandler(handler, TouchMoveEvent.getType());
    }

    @Override
    public void onResize() {

        Iterator<Widget> subviews = content.iterator();

        while (subviews.hasNext()) {
            Widget widget = subviews.next();
            if (widget instanceof RequiresResize) {
                ((RequiresResize) widget).onResize();
            }
        }
    }

    private class DragController implements MouseDownHandler,
            MouseMoveHandler, MouseUpHandler, TouchStartHandler,
            TouchMoveHandler, TouchEndHandler {

        private Widget widget = null;

        private boolean dragging;
        private boolean containInParent = false;
        private double mouseDownStartX;
        private double mouseDownStartY;

        private double maxX;
        private double maxY;
        private double minX;
        private double minY;

        private HandlerRegistration mouseDownHandlerRegistration;
        private HandlerRegistration mouseMoveHandlerRegistration;
        private HandlerRegistration mouseUpHandlerRegistration;
        private HandlerRegistration touchStartHandlerRegistration;
        private HandlerRegistration touchMoveHandlerRegistration;
        private HandlerRegistration touchEndHandlerRegistration;

        private Widget dragHandle = null;


        public DragController() {
            StyleResources.INSTANCE.interactionCSS().ensureInjected();
        }

        public void makeDraggable(Widget w) {
            makeDraggable(w, null);
        }

        public void makeDraggable(Widget w, Widget handle) {

            removeDraggable();

            widget = w;

            if (handle == null) {
                dragHandle = widget;
            } else {
                dragHandle = handle;
            }

            if (dragHandle instanceof HasMouseDownHandlers) {
                mouseDownHandlerRegistration = ((HasMouseDownHandlers) dragHandle).addMouseDownHandler(this);
            }

            if (dragHandle instanceof HasMouseMoveHandlers) {
                mouseMoveHandlerRegistration = ((HasMouseMoveHandlers) dragHandle).addMouseMoveHandler(this);
            }

            if (dragHandle instanceof HasMouseUpHandlers) {
                mouseUpHandlerRegistration = ((HasMouseUpHandlers) dragHandle).addMouseUpHandler(this);
            }

            if (dragHandle instanceof HasTouchStartHandlers) {
                touchStartHandlerRegistration = ((HasTouchStartHandlers) dragHandle).addTouchStartHandler(this);
            }

            if (dragHandle instanceof HasTouchMoveHandlers) {
                touchMoveHandlerRegistration = ((HasTouchMoveHandlers) dragHandle).addTouchMoveHandler(this);
            }

            if (dragHandle instanceof HasTouchEndHandlers) {
                touchEndHandlerRegistration = ((HasTouchEndHandlers) dragHandle).addTouchEndHandler(this);
            }

        }

        public void setContainInParent(boolean containInParent) {

            this.containInParent = containInParent;
        }

        public boolean isContainInParent() {
            return containInParent;
        }

        public void removeDraggable() {

            if (mouseDownHandlerRegistration != null) {
                mouseDownHandlerRegistration.removeHandler();
            }

            if (mouseMoveHandlerRegistration != null) {
                mouseMoveHandlerRegistration.removeHandler();
            }

            if (mouseUpHandlerRegistration != null) {
                mouseUpHandlerRegistration.removeHandler();
            }

            if (touchStartHandlerRegistration != null) {
                touchStartHandlerRegistration.removeHandler();
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
        public void onMouseDown(MouseDownEvent event) {

            if (event.getNativeButton() == NativeEvent.BUTTON_LEFT) {

                event.preventDefault();
                DOM.setCapture(dragHandle.getElement());

                mouseDownStartX = event.getClientX() - widget.getAbsoluteLeft();
                mouseDownStartY = event.getClientY() - widget.getAbsoluteTop();

                if (containInParent) {
                    Widget parent = widget.getParent();

                    minX = parent.getAbsoluteLeft();
                    minY = parent.getAbsoluteTop();
                    maxX = minX + parent.getOffsetWidth() - widget.getOffsetWidth();
                    maxY = minY + parent.getOffsetHeight() - widget.getOffsetHeight();
                }


                dragging = true;
            }


        }

        @Override
        public void onMouseMove(MouseMoveEvent event) {

            if (dragging) {

                double x = event.getClientX() - mouseDownStartX;
                double y = event.getClientY() - mouseDownStartY;

                if (containInParent) {
                    x = Math.max(minX, Math.min(x, maxX));
                    y = Math.max(minY, Math.min(y, maxY));

                }

                widget.getElement().getStyle().setLeft(x, Style.Unit.PX);
                widget.getElement().getStyle().setTop(y, Style.Unit.PX);
            }

        }

        @Override
        public void onMouseUp(MouseUpEvent event) {

            dragging = false;
            DOM.releaseCapture(dragHandle.getElement());
        }

        @Override
        public void onTouchEnd(TouchEndEvent event) {

            dragging = false;

            DOM.releaseCapture(dragHandle.getElement());
        }

        @Override
        public void onTouchMove(TouchMoveEvent event) {

            if (dragging) {


                if (event.getTouches().length() > 0) {

                    Touch touch = event.getTouches().get(0);

                    double x = touch.getClientX() - mouseDownStartX;
                    double y = touch.getClientY() - mouseDownStartY;

                    if (containInParent) {
                        x = Math.max(minX, Math.min(x, maxX));
                        y = Math.max(minY, Math.min(y, maxY));

                    }

                    widget.getElement().getStyle().setLeft(x, Style.Unit.PX);
                    widget.getElement().getStyle().setTop(y, Style.Unit.PX);

                }
            }

        }

        @Override
        public void onTouchStart(TouchStartEvent event) {

            dragging = true;

            event.preventDefault();
            DOM.setCapture(dragHandle.getElement());

            if (event.getTouches().length() > 0) {

                Touch touch = event.getTouches().get(0);

                mouseDownStartX = touch.getClientX() - widget.getAbsoluteLeft();
                mouseDownStartY = touch.getClientY() - widget.getAbsoluteTop();

                if (containInParent) {
                    Widget parent = widget.getParent();

                    minX = parent.getAbsoluteLeft();
                    minY = parent.getAbsoluteTop();
                    maxX = minX + parent.getOffsetWidth() - widget.getOffsetWidth();
                    maxY = minY + parent.getOffsetHeight() - widget.getOffsetHeight();
                }

            }


        }
    }

    private abstract class WindowPanelTitleBarButton extends TouchFocusWidget {

        public WindowPanelTitleBarButton() {

            super(DOM.createDiv());

            render();
        }

        private void render() {

            getElement().setAttribute("role", "button");

            addMouseDownHandler(new MouseDownHandler() {
                @Override
                public void onMouseDown(MouseDownEvent event) {
                    event.stopPropagation();
                    event.preventDefault();

                    getElement().addClassName(appearance.css().windowPanelActiveButtonClass());

                    DOM.setCapture(getElement());

                    moveToTop();

                    //need to close transient popover (unless its the parent) or menu
                    // since we are stopping propagation
                    Popover.closeTransientPopoverIfNecessary(WindowPanelTitleBarButton.this);
                    Menu.closeCurrentlyOpenMenuIfNecessary();
                }

            });

            addMouseUpHandler(new MouseUpHandler() {
                @Override
                public void onMouseUp(MouseUpEvent event) {
                    getElement().removeClassName(appearance.css().windowPanelActiveButtonClass());
                    DOM.releaseCapture(getElement());
                }
            });

            addMouseOverHandler(new MouseOverHandler() {
                @Override
                public void onMouseOver(MouseOverEvent event) {
                    getElement().addClassName(appearance.css().windowPanelHoverButtonClass());

                }
            });

            addMouseOutHandler(new MouseOutHandler() {
                @Override
                public void onMouseOut(MouseOutEvent event) {
                    getElement().removeClassName(appearance.css().windowPanelHoverButtonClass());
                }
            });

            addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    WindowPanelTitleBarButton.this.onClick();
                    getElement().removeClassName(appearance.css().windowPanelHoverButtonClass());

                }
            });

            addKeyDownHandler(new KeyDownHandler() {
                @Override
                public void onKeyDown(KeyDownEvent event) {
                    if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                        getElement().addClassName(appearance.css().windowPanelActiveButtonClass());
                        DOM.setCapture(getElement());
                    }
                }
            });

            addKeyUpHandler(new KeyUpHandler() {
                @Override
                public void onKeyUp(KeyUpEvent event) {
                    if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                        onClick();
                        getElement().removeClassName(appearance.css().windowPanelHoverButtonClass());
                    }

                    DOM.releaseCapture(getElement());
                }
            });

            addTouchHandler(new TouchHandler() {
                @Override
                public void onTouchCancel(TouchCancelEvent event) {

                }

                @Override
                public void onTouchEnd(TouchEndEvent event) {
                    getElement().removeClassName(appearance.css().windowPanelActiveButtonClass());
                    DOM.releaseCapture(getElement());
                    getElement().removeClassName(appearance.css().windowPanelHoverButtonClass());

                }

                @Override
                public void onTouchMove(TouchMoveEvent event) {

                }

                @Override
                public void onTouchStart(TouchStartEvent event) {
                    event.stopPropagation();
                    event.preventDefault();

                    getElement().addClassName(appearance.css().windowPanelActiveButtonClass());

                    DOM.setCapture(getElement());
                    moveToTop();

                    Popover.closeTransientPopoverIfNecessary(WindowPanelTitleBarButton.this);
                    Menu.closeCurrentlyOpenMenuIfNecessary();
                }
            });

            addTapHandler(new TapHandler() {
                @Override
                public void onTap(TapEvent event) {
                    WindowPanelTitleBarButton.this.onClick();
                    getElement().removeClassName(appearance.css().windowPanelHoverButtonClass());

                }
            });
        }

        public abstract void onClick();

    }

    private class WindowPanelTitleBar extends AbsolutePanel implements
            HasMouseDownHandlers, HasMouseMoveHandlers, HasMouseUpHandlers,
            HasAllTouchHandlers {

        private WindowPanelTitleBarButton _closeButton;
        private WindowPanelTitleBarButton _minimizeButton;
        private WindowPanelTitleBarButton _maximizeButton;

        private Label _titleText;

        private boolean _isMaximized = false;

        WindowPanelTitleBar(final WindowPanel theWindow) {

            getElement().setClassName(appearance.css().windowPanelTitleClass());

            _titleText = new Label();
            _titleText.setStyleName(appearance.css().windowPanelTitleTextClass());

            add(_titleText);

            FlowPanel buttonPane = new FlowPanel();
            buttonPane.setStyleName(appearance.css().windowPanelButtonPaneClass());

            _closeButton = new WindowPanelTitleBarButton() {
                @Override
                public void onClick() {
                    theWindow.close();
                }
            };
            _closeButton.setTabIndex(0);
            _closeButton.setStyleName(appearance.css().windowPanelCloseButtonClass());
            buttonPane.add(_closeButton);

            _maximizeButton = new WindowPanelTitleBarButton() {
                @Override
                public void onClick() {
                    if (_isMaximized) {
                        theWindow.unmaximize();
                    } else {
                        theWindow.maximize();
                    }
                }
            };

            _maximizeButton.setStyleName(appearance.css().windowPanelMaximizeButtonClass());
            _maximizeButton.setTabIndex(0);
            buttonPane.add(_maximizeButton);

            _minimizeButton = new WindowPanelTitleBarButton() {
                @Override
                public void onClick() {
                    MinimizeEvent.fire(theWindow);
                }
            };
            _minimizeButton.setTabIndex(0);
            _minimizeButton.setStyleName(appearance.css().windowPanelMinimizeButtonClass());
            buttonPane.add(_minimizeButton);

            add(buttonPane);

            addMouseDownHandler(new MouseDownHandler() {
                @Override
                public void onMouseDown(MouseDownEvent event) {
                    moveToTop();
                }
            });

        }

        public String getTitleBarText() {
            return _titleText.getText();
        }

        public void setTitleBarText(String txt) {
            _titleText.setText(txt);
        }

        public void setShowMinimize(boolean show) {
            _minimizeButton.setVisible(show);
        }

        public boolean getMinimizeVisible() {
            return _minimizeButton.isVisible();
        }

        public void setMaximized(boolean max) {

            _isMaximized = max;
            if (_isMaximized) {
                _maximizeButton.getElement().setClassName(appearance.css().windowPanelRestoreButtonClass());

            } else {
                _maximizeButton.getElement().setClassName(appearance.css().windowPanelMaximizeButtonClass());

            }
        }


        public void setShowMaximize(boolean show) {
            _maximizeButton.setVisible(show);
        }

        public boolean getMaximizeVisible() {
            return _maximizeButton.isVisible();
        }

        public void setShowClose(boolean show) {
            _closeButton.setVisible(show);
        }

        public boolean getCloseVisible() {
            return _closeButton.isVisible();
        }

        public void focusOnFirstButton() {
            if (_closeButton.isVisible()) {
                _closeButton.setTabIndex(0);
                _closeButton.getElement().focus();
            }
        }


        @Override
        public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
            return addDomHandler(handler, MouseDownEvent.getType());
        }

        @Override
        public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
            return addDomHandler(handler, MouseMoveEvent.getType());
        }

        @Override
        public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
            return addDomHandler(handler, MouseUpEvent.getType());
        }

        @Override
        public HandlerRegistration addTouchCancelHandler(TouchCancelHandler handler) {
            return addDomHandler(handler, TouchCancelEvent.getType());
        }

        @Override
        public HandlerRegistration addTouchEndHandler(TouchEndHandler handler) {
            return addDomHandler(handler, TouchEndEvent.getType());
        }

        @Override
        public HandlerRegistration addTouchMoveHandler(TouchMoveHandler handler) {
            return addDomHandler(handler, TouchMoveEvent.getType());
        }

        @Override
        public HandlerRegistration addTouchStartHandler(TouchStartHandler handler) {
            return addDomHandler(handler, TouchStartEvent.getType());
        }
    }


}
