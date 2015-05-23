package com.appkit.ui.client.widgets.menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.Iterator;


public class Menu extends AbstractMenu implements
        HasCloseHandlers<Menu>, HasSelectionHandlers<Menu> {

    private static Menu openMenu = null;

    private FlowPanel panel;

    private MenuItem lastSelectedItem = null;
    private MenuItem activeItem = null;
    private String startingString = "";
    private Timer startingStringTimer = null;

    private MenuAppearance appearance;

    static MenuAppearance DEFAULT_APPEARANCE = GWT.create(DefaultMenuAppearance.class);

    static {

        DOM.sinkEvents(RootPanel.getBodyElement(), Event.ONMOUSEDOWN | Event.ONTOUCHSTART);
        RootPanel.get().addHandler(new MouseDownHandler() {
            @Override
            public void onMouseDown(MouseDownEvent event) {
                if (openMenu != null) {
                    openMenu.close();
                }
            }
        }, MouseDownEvent.getType());

        RootPanel.get().addHandler(new TouchStartHandler() {
            @Override
            public void onTouchStart(TouchStartEvent event) {
                if (openMenu != null) {
                    openMenu.close();
                }
            }
        }, TouchStartEvent.getType());

        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
                if (openMenu != null) {
                    openMenu.close();
                }
            }
        });

    }

    public Menu(MenuAppearance appearance) {
        this.appearance = appearance;
        render();
    }

    @UiConstructor
    public Menu() {
        this(DEFAULT_APPEARANCE);
    }

    public static Menu getCurrentlyOpenMenu() {
        return openMenu;
    }

    public static void closeCurrentlyOpenMenuIfNecessary() {
        if (openMenu != null) {
            openMenu.close();
        }
    }

    private void render() {

        panel = new FlowPanel();

        initWidget(panel);

        setStyleName(appearance.css().menuClass());
        getElement().addClassName("appkit-menu");
        getElement().setTabIndex(0);
        getElement().setAttribute("role", "menu");

        panel.addDomHandler(new MouseDownHandler() {
            @Override
            public void onMouseDown(MouseDownEvent event) {
                event.preventDefault();
                event.stopPropagation();
            }
        }, MouseDownEvent.getType());

        panel.addDomHandler(new MouseMoveHandler() {
            @Override
            public void onMouseMove(MouseMoveEvent event) {
                getElement().focus();
                DOM.releaseCapture(getElement());
            }
        }, MouseMoveEvent.getType());

        panel.addDomHandler(this, KeyDownEvent.getType());

        setVisible(false);

        RootPanel.get().add(this);


    }

    public MenuAppearance getAppearance() {
        return appearance;
    }

    @Override
    public void setVisible(boolean visible) {

        if (!visible) {
            setActiveMenuItem(null);
        }

        super.setVisible(visible);

    }


    public void close() {

        if (isVisible()) {
            if (getParentMenu() != null) {
                getParentMenu().close();
            }

            this.setVisible(false);

            CloseEvent.fire(this, this);
        }

    }

    public void open() {

        if (openMenu != null) {
            if (!openMenu.equals(this)) {
                openMenu.close();
            }
        }

        openMenu = this;

        setVisible(true);
    }

    public void setPosition(int x, int y) {
        getElement().getStyle().setLeft(x, Style.Unit.PX);
        getElement().getStyle().setTop(y, Style.Unit.PX);
    }

    public void addMenuItem(AbstractMenuItem item) {
        if (item != null) {
            item.setMenu(this);
            panel.add(item);
        }
    }

    public AbstractMenuItem getMenuItem(int itemIndex) {
        if (itemIndex > -1 && itemIndex < getMenuItemCount()) {
            return (AbstractMenuItem) panel.getWidget(itemIndex);
        }

        return null;
    }

    public void setSelectedIndex(int index) {
        AbstractMenuItem item = getMenuItem(index);
        if (item instanceof MenuItem) {
            setLastSelectedItem((MenuItem) item);
        } else {
            setLastSelectedItem(null);
        }

    }

    public void setActiveMenuItem(int itemIndex) {

        if (itemIndex < panel.getWidgetCount()) {
            setActiveMenuItem((MenuItem) panel.getWidget(itemIndex));
        } else {
            setActiveMenuItem(null);
        }
    }

    @Override
    public void setActiveMenuItem(AbstractMenuItem menuItem) {

        if (activeItem != null &&
                !activeItem.equals(menuItem)) {
            activeItem.setActive(false);
        }

        activeItem = (MenuItem) menuItem;

        if (activeItem != null) {
            activeItem.setActive(true);
        }

    }

    @Override
    public AbstractMenuItem getActiveMenuItem() {
        return activeItem;
    }

    public int getMenuItemCount() {
        return panel.getWidgetCount();
    }

    public void setLastSelectedItem(MenuItem menuItem) {

        if (menuItem != null) {
            if (!menuItem.equals(lastSelectedItem)) {
                lastSelectedItem = menuItem;
            }

            SelectionEvent.fire(this, this);
        } else {
            lastSelectedItem = menuItem;
        }
    }

    public int getLastSelectedIndex() {
        if (lastSelectedItem != null) {

            Iterator<Widget> it = iterator();
            int idx = -1;
            while (it.hasNext()) {
                Widget w = it.next();
                idx++;
                if (w instanceof MenuItem) {
                    if (w.equals(lastSelectedItem)) {
                        return idx;
                    }
                }
            }
        }

        return -1;
    }

    public MenuItem getLastSelectedItem() {
        return lastSelectedItem;
    }

    public MenuItem getNextMenuItem(int itemIndex) {

        itemIndex++;
        int count = getMenuItemCount();
        while (itemIndex < count) {
            AbstractMenuItem item = (AbstractMenuItem) panel.getWidget(itemIndex);
            if (item instanceof MenuItem) {
                if (item.isVisible() && item.isEnabled()) {
                    return (MenuItem) item;
                }
            }

            itemIndex++;
        }

        return null;
    }

    public MenuItem getPreviousMenuItem(int itemIndex) {
        itemIndex--;

        while (itemIndex > -1) {
            AbstractMenuItem item = (AbstractMenuItem) panel.getWidget(itemIndex);
            if (item instanceof MenuItem) {
                if (item.isVisible() && item.isEnabled()) {
                    return (MenuItem) item;
                }

            }

            itemIndex--;
        }

        return null;
    }

    public int indexOfItemEqualToString(String text) {
        int index = -1;
        if (text != null) {
            if (text.length() > 0) {

                Iterator<Widget> it = this.iterator();
                int i = 0;
                while (it.hasNext()) {
                    Widget w = it.next();
                    if (w instanceof MenuItem) {
                        String label = ((MenuItem) w).getText();
                        if (text.length() == label.length()) {
                            if (text.toUpperCase().equals(label.toUpperCase())) {
                                index = i;
                                break;
                            }
                        }
                    }

                    i++;
                }
            }
        }

        return index;

    }

    public int indexOfItemStartingWithString(String text) {

        int index = -1;
        if (text != null) {

            if (text.length() > 0) {

                Iterator<Widget> it = this.iterator();
                int i = 0;
                while (it.hasNext()) {
                    Widget w = it.next();
                    if (w instanceof MenuItem) {
                        String label = ((MenuItem) w).getText();
                        if (text.length() <= label.length()) {
                            if (text.toUpperCase().equals(label.substring(0, text.length()).toUpperCase())) {
                                startingString += text;
                                index = i;
                                break;
                            }
                        }
                    }

                    i++;

                }

            }


        }

        if (startingStringTimer != null) {
            startingStringTimer.cancel();
        }

        startingStringTimer = new Timer() {
            @Override
            public void run() {
                startingString = "";
            }
        };

        startingStringTimer.schedule(600);

        return index;
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

    @Override
    public HandlerRegistration addCloseHandler(CloseHandler<Menu> handler) {
        return addHandler(handler, CloseEvent.getType());
    }


    @Override
    public HandlerRegistration addSelectionHandler(SelectionHandler<Menu> handler) {
        return addHandler(handler, SelectionEvent.getType());
    }


    @Override
    public void onKeyDown(KeyDownEvent event) {

        event.preventDefault();
        int keyCode = event.getNativeKeyCode();
        switch (keyCode) {
            case KeyCodes.KEY_ENTER: {
                if (getActiveMenuItem() != null) {

                    getActiveMenuItem().fireEvent(new ClickEvent() {
                    });

                    ((MenuItem) getActiveMenuItem()).handleClick();
                }

            }
            break;
            case KeyCodes.KEY_ESCAPE: {
                close();
            }
            break;
            case KeyCodes.KEY_DOWN: {
                if (activeItem != null) {
                    int currentIndex = panel.getWidgetIndex(activeItem);
                    final MenuItem nextItem = getNextMenuItem(currentIndex);
                    if (nextItem != null) {

                        setActiveMenuItem(nextItem);
                        scrollActiveMenuItemIntoView();


                    }
                } else {
                    if (panel.getWidgetCount() > 0)
                        setActiveMenuItem(getNextMenuItem(-1));
                }

            }
            break;
            case KeyCodes.KEY_UP: {
                if (activeItem != null) {
                    int currentIndex = panel.getWidgetIndex(activeItem);
                    final MenuItem prevItem = getPreviousMenuItem(currentIndex);
                    if (prevItem != null) {

                        setActiveMenuItem(prevItem);

                        scrollActiveMenuItemIntoView();


                    }
                } else {
                    if (panel.getWidgetCount() > 0)
                        setActiveMenuItem(getPreviousMenuItem(panel.getWidgetCount()));
                }

            }
            break;
            case KeyCodes.KEY_RIGHT: {
                if (activeItem != null) {
                    Menu subMenu = activeItem.getSubmenu();
                    if (subMenu != null) {
                        if (subMenu.getMenuItemCount() > 0) {
                            subMenu.setActiveMenuItem(0);
                            subMenu.getElement().focus();
                        }
                    } else {

                        AbstractMenu menuBar = getParentMenu();
                        while (menuBar != null) {
                            if (menuBar instanceof MenuBar) {
                                menuBar.onKeyDown(event);
                                return;
                            }

                            menuBar = menuBar.getParentMenu();
                        }
                    }
                }

            }
            break;
            case KeyCodes.KEY_LEFT: {
                if (getParentMenu() != null) {
                    AbstractMenu parentMenu = getParentMenu();
                    if (parentMenu instanceof Menu) {
                        getParentMenu().getElement().focus();
                        setVisible(false);
                    } else if (parentMenu instanceof MenuBar) {
                        parentMenu.onKeyDown(event);
                    }

                }
            }
            break;
            default: {
                String letter = String.valueOf((char) keyCode);
                if (Character.isLetterOrDigit(letter.charAt(0))) {
                    int idx = indexOfItemStartingWithString(startingString + letter);
                    if (idx > -1) {
                        setActiveMenuItem(idx);
                        DOM.setCapture(getElement());
                        activeItem.getElement().scrollIntoView();
                    }
                }

            }
            break;
        }
    }


    private void scrollActiveMenuItemIntoView() {
        if (activeItem != null) {

            int top = activeItem.getElement().getOffsetTop() - getElement().getScrollTop();
            int bottom = activeItem.getElement().getOffsetTop()
                    + activeItem.getOffsetHeight() - getElement().getScrollTop() -
                    getElement().getOffsetHeight();

            if (top < 0) {

                DOM.setCapture(getElement());
                getElement().setScrollTop(
                        getElement().getScrollTop() + top - 5
                );

            } else if (bottom > 0) {

                DOM.setCapture(getElement());
                getElement().setScrollTop(
                        getElement().getScrollTop() + bottom + 5
                );

            }
        }

    }

}
