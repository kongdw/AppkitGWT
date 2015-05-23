package com.appkit.ui.client.widgets.menu;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.Iterator;

public class MenuItem extends AbstractMenuItem implements HasWidgets, HasText {

    private Element titleElement;
    private Element accessoryElement;
    private Element markerElement;

    private Menu subMenu;

    private String iconClass = null;
    private boolean subMenuVisible = false;
    private boolean checkable = false;
    private boolean checked = false;

    private MenuItemAppearance appearance;

    static MenuItemAppearance DEFAULT_APPEARANCE = GWT.create(DefaultMenuItemAppearance.class);

    public MenuItem(String title) {
        this(DEFAULT_APPEARANCE);
        setText(title);
    }

    public MenuItem(MenuItemAppearance appearance) {
        super(DOM.createDiv());
        this.appearance = appearance;
        render();
    }

    @UiConstructor
    public MenuItem() {
        this(DEFAULT_APPEARANCE);
    }

    private void render() {

        getElement().setClassName(appearance.css().menuItemClass());
        getElement().addClassName("appkit-menu-item");
        getElement().setAttribute("role", "menuitem");
        getElement().setTabIndex(0);

        titleElement = DOM.createDiv();
        titleElement.setClassName(appearance.css().menuItemTitleClass());
        getElement().appendChild(titleElement);

        accessoryElement = DOM.createDiv();
        accessoryElement.setClassName(appearance.css().menuItemAccessoryClass());
        getElement().appendChild(accessoryElement);

        markerElement = DOM.createDiv();
        markerElement.setClassName(appearance.css().menuItemMarkerClass());
        getElement().appendChild(markerElement);

        subMenuVisible = false;

        sinkEvents(Event.ONMOUSEDOWN | Event.ONTOUCHSTART | Event.ONTOUCHEND |
                Event.ONMOUSEOVER | Event.ONMOUSEOUT | Event.ONCLICK);

    }

    public MenuItemAppearance getAppearance() {
        return appearance;
    }

    public void setChecked(boolean checked) {
        if (this.checked != checked) {
            this.checked = checked;

            if (this.checked && this.checkable) {
                markerElement.addClassName(appearance.css().iconCheckClass());
                getElement().setAttribute("aria-checked", "true");
            } else {
                markerElement.removeClassName(appearance.css().iconCheckClass());
                getElement().setAttribute("aria-checked", "false");
            }
        }
    }

    @Override
    public void setEnabled(boolean enabled1) {
        super.setEnabled(enabled1);

        if (!isEnabled()) {
            getElement().addClassName(appearance.css().menuItemDisabledClass());
            getElement().setAttribute("aria-disabled", "true");
        } else {
            getElement().removeClassName(appearance.css().menuItemDisabledClass());
            getElement().removeAttribute("aria-disabled");
        }
    }


    public void setCheckable(boolean checkable) {

        if (this.checkable != checkable) {

            this.checkable = checkable;

            if (this.checkable) {

                if (this.iconClass != null) {
                    markerElement.removeClassName(this.iconClass);
                }

                markerElement.getStyle().setDisplay(Style.Display.BLOCK);
                if (this.checked) {
                    markerElement.addClassName("icon-check");
                }
            } else {
                markerElement.getStyle().setDisplay(Style.Display.NONE);
                markerElement.removeClassName("icon-check");
            }

        }
    }

    public boolean isCheckable() {
        return checkable;
    }


    public void setIcon(String iconClass) {

        setCheckable(false);

        if (this.iconClass != null) {
            markerElement.removeClassName(this.iconClass);
        }

        if (iconClass != null) {

            this.iconClass = iconClass;
            markerElement.getStyle().setDisplay(Style.Display.BLOCK);
            markerElement.addClassName(iconClass);

        } else {
            markerElement.getStyle().setDisplay(Style.Display.NONE);
        }
    }


    public void setSubmenu(Menu menu) {

        subMenu = menu;

        if (subMenu != null) {
            accessoryElement.getStyle().setDisplay(Style.Display.BLOCK);
            subMenu.setParentMenu(getMenu());
        } else {
            accessoryElement.getStyle().setDisplay(Style.Display.NONE);
        }

    }

    public Menu getSubmenu() {
        return subMenu;
    }

    @Override
    public String getText() {
        if (titleElement != null) {
            return titleElement.getInnerText();
        }
        return "";
    }

    @Override
    public void setText(String text) {
        if (titleElement != null)
            titleElement.setInnerHTML(text);
    }

    public void setActive(boolean active) {

        if (active) {
            getElement().addClassName(appearance.css().menuItemActiveClass());
            getElement().addClassName("appkit-state-selected");
            if (subMenu != null) {

                subMenuVisible = true;

                Timer timer = new Timer() {
                    @Override
                    public void run() {
                        if (isActive()) {

                            subMenu.setVisible(true);

                            int leftPosition = getElement().getAbsoluteLeft() + getElement().getOffsetWidth();
                            int subMenuWidth = subMenu.getOffsetWidth();

                            if (leftPosition + subMenuWidth > RootPanel.get().getOffsetWidth()) {
                                leftPosition = RootPanel.get().getOffsetWidth() - subMenuWidth;
                            }

                            int topPosition = getElement().getAbsoluteTop() - 5;
                            int subMenuHeight = subMenu.getOffsetHeight();

                            if (topPosition + subMenuHeight > RootPanel.get().getOffsetHeight()) {
                                topPosition = RootPanel.get().getOffsetHeight() - subMenuHeight;
                            }

                            subMenu.setPosition(
                                    Math.max(0, leftPosition),
                                    Math.max(0, topPosition)
                            );

                        } else {
                            subMenu.setVisible(false);
                        }
                    }
                };

                timer.schedule(200);

            }

        } else {
            getElement().removeClassName(appearance.css().menuItemActiveClass());
            getElement().removeClassName("appkit-state-selected");

            if (subMenu != null) {
                subMenu.setVisible(false);
            }

            subMenuVisible = false;
        }
    }

    public boolean isActive() {
        return getElement().hasClassName(appearance.css().menuItemActiveClass());
    }

    @Override
    protected void setMenu(AbstractMenu menu) {
        this.menu = menu;

        if (subMenu != null) {
            subMenu.setParentMenu(getMenu());
        }
    }

    @Override
    public void onBrowserEvent(Event event) {

        int eventType = event.getTypeInt();
        switch (eventType) {
            case Event.ONMOUSEDOWN: {
                event.preventDefault();
                event.stopPropagation();

            }
            break;
            case Event.ONMOUSEOVER: {
                if (isEnabled()) {
                    if (menu != null) {
                        menu.setActiveMenuItem(this);
                    }
                }

            }
            break;
            case Event.ONMOUSEOUT: {
                if (menu != null) {
                    if (subMenu != null) {
                        if (!subMenuVisible) {
                            menu.setActiveMenuItem(null);
                        }
                    } else {
                        menu.setActiveMenuItem(null);
                    }
                }

            }
            break;
            case Event.ONCLICK: {
                handleClick();

            }
            break;
            case Event.ONTOUCHSTART: {
                event.stopPropagation();

                if (isEnabled()) {
                    if (menu != null) {
                        menu.setActiveMenuItem(this);
                    }
                }

            }
            break;
            case Event.ONTOUCHEND: {
                if (menu != null) {
                    if (subMenu != null) {
                        if (!subMenuVisible) {
                            menu.setActiveMenuItem(null);
                        }
                    } else {
                        menu.setActiveMenuItem(null);
                    }
                }

            }
            break;
        }

        super.onBrowserEvent(event);
    }

    protected void handleClick() {

        if (isEnabled() && subMenu == null) {

            final Timer t = new Timer() {
                private int tcount = 0;

                @Override
                public void run() {

                    if (tcount > 0) {
                        cancel();

                        if (checkable) {
                            setChecked(!checked);
                        }

                        if (menu != null && subMenu == null) {
                            ((Menu) menu).setLastSelectedItem(MenuItem.this);
                            menu.close();
                        }

                        getElement().removeClassName(appearance.css().menuItemActiveClass());
                        getElement().removeClassName("appkit-state-selected");
                        DOM.releaseCapture(getElement());
                        return;
                    }

                    if (getElement().hasClassName(appearance.css().menuItemActiveClass())) {
                        getElement().removeClassName(appearance.css().menuItemActiveClass());
                        getElement().removeClassName("appkit-state-selected");
                    } else {
                        getElement().addClassName(appearance.css().menuItemActiveClass());
                        getElement().addClassName("appkit-state-selected");
                        DOM.setCapture(getElement());
                        tcount++;
                    }
                }
            };

            t.scheduleRepeating(50);
        }

    }


    @Override
    public void add(Widget w) {
        if (w instanceof Menu) {
            setSubmenu((Menu) w);
        }
    }


    @Override
    public void clear() {
        setSubmenu(null);
    }

    @Override
    public Iterator<Widget> iterator() {
        return null;
    }

    @Override
    public boolean remove(Widget w) {
        return false;
    }


}
