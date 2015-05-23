package com.appkit.ui.client.widgets.menu;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.Iterator;


public class MenuBar extends AbstractMenu {

    private FlowPanel panel;
    private MenuBarItem activeItem;

    private MenuBarAppearance appearance;

    static MenuBarAppearance DEFAULT_APPEARANCE = GWT.create(DefaultMenuBarAppearance.class);

    public MenuBar(MenuBarAppearance appearance) {
        this.appearance = appearance;
        render();
    }

    public MenuBar() {
        this(DEFAULT_APPEARANCE);
    }


    private void render() {
        panel = new FlowPanel();

        panel.setStyleName(appearance.css().menuBarClass());
        panel.getElement().addClassName("appkit-menubar");
        panel.getElement().setAttribute("role", "menubar");


        initWidget(panel);
    }

    public MenuBarItem getMenuBarItem(int index) {
        if (index > -1 && index < panel.getWidgetCount()) {
            return (MenuBarItem) panel.getWidget(index);
        }
        return null;
    }

    public int getMenuBarItemCount() {
        return panel.getWidgetCount();
    }


    @Override
    public void addMenuItem(AbstractMenuItem item) {

        if (item instanceof MenuBarItem) {
            if (panel.getWidgetCount() == 0) {
                item.getElement().addClassName(appearance.css().menuBarItemFirstClass());
            }

            ((MenuBarItem) item).setMenuBar(this);

            panel.add(item);
        }
    }

    @Override
    public AbstractMenuItem getMenuItem(int index) {
        if (index > -1 && index < panel.getWidgetCount())
            return (AbstractMenuItem) panel.getWidget(index);

        return null;
    }

    @Override
    public int getMenuItemCount() {
        return panel.getWidgetCount();
    }

    @Override
    public void setActiveMenuItem(AbstractMenuItem menuItem) {
        activeItem = (MenuBarItem) menuItem;
    }

    @Override
    public AbstractMenuItem getActiveMenuItem() {
        return activeItem;
    }


    protected int getIndexOfActiveItem() {
        return panel.getWidgetIndex(activeItem);
    }

    @Override
    public void close() {
        //do nothing
    }

    @Override
    public void open() {
        //do nothing
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
    public void onKeyDown(KeyDownEvent event) {

        int keyCode = event.getNativeKeyCode();

        switch (keyCode) {
            case KeyCodes.KEY_LEFT: {
                int selectedIndex = getIndexOfActiveItem();
                if (selectedIndex - 1 > -1) {
                    MenuBarItem next = getMenuBarItem(selectedIndex - 1);
                    if (next != null) {
                        next.open();
                    }
                }

            }
            break;
            case KeyCodes.KEY_RIGHT: {
                int selectedIndex = getIndexOfActiveItem();
                if (selectedIndex + 1 < getMenuBarItemCount()) {
                    MenuBarItem next = getMenuBarItem(selectedIndex + 1);
                    if (next != null) {
                        next.open();
                    }
                }
            }
            break;
        }
    }
}
