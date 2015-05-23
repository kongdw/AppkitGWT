package com.appkit.ui.client.widgets.menu;


import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public abstract class AbstractMenu extends Composite
        implements HasWidgets, KeyDownHandler

{

    private AbstractMenu parentMenu;

    public abstract void addMenuItem(AbstractMenuItem item);

    public abstract AbstractMenuItem getMenuItem(int index);

    public abstract int getMenuItemCount();

    public abstract void setActiveMenuItem(AbstractMenuItem menuItem);

    public abstract AbstractMenuItem getActiveMenuItem();


    public abstract void close();

    public abstract void open();

    protected void setParentMenu(AbstractMenu parentMenu) {
        this.parentMenu = parentMenu;
    }

    protected AbstractMenu getParentMenu() {
        return parentMenu;
    }

    @Override
    public void add(Widget w) {
        if (w instanceof AbstractMenuItem) {
            addMenuItem((AbstractMenuItem) w);
        }
    }

}
