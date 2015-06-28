package com.appkit.ui.client.widgets.menu;

import com.appkit.ui.client.widgets.popover.Popover;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import java.util.Iterator;

public class MenuBarItem extends AbstractMenuItem implements HasWidgets {

    private Menu menu = null;
    private MenuBar menuBar = null;
    private HandlerRegistration menuCloseHandler = null;

    public MenuBarItem() {

        super(DOM.createDiv());

        getElement().addClassName("appkit-menubar-item");

        addMouseDownHandler(new MouseDownHandler() {
            @Override
            public void onMouseDown(MouseDownEvent event) {
                event.preventDefault();

                toggleMenu();

                event.stopPropagation();

                //need to close transient popover (unless its the parent)
                // since we are stopping propagation
                Popover.closeTransientPopoverIfNecessary(MenuBarItem.this);


            }
        });

        addTouchStartHandler(new TouchStartHandler() {
            @Override
            public void onTouchStart(TouchStartEvent event) {
                event.preventDefault();
                toggleMenu();
                event.stopPropagation();

                //need to close transient popover (unless its the parent)
                // since we are stopping propagation
                Popover.closeTransientPopoverIfNecessary(MenuBarItem.this);
            }
        });

        addMouseOverHandler(new MouseOverHandler() {
            @Override
            public void onMouseOver(MouseOverEvent event) {
                if (getMenuBar().getActiveMenuItem() != null) {
                    open();
                }
            }
        });

        addFocusHandler(new FocusHandler() {
            @Override
            public void onFocus(FocusEvent event) {
                if (getMenuBar().getActiveMenuItem() != null) {
                    open();
                }
            }
        });

        addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {

                int keyCode = event.getNativeKeyCode();
                switch (keyCode) {
                    case KeyCodes.KEY_ENTER: {
                        if (menu != null) {
                            open();
                        }
                    }
                    break;
                    case KeyCodes.KEY_ESCAPE: {
                        close();
                    }
                    break;
                    case KeyCodes.KEY_TAB: {
                        menu.close();
                    }
                    break;
                    case KeyCodes.KEY_DOWN: {
                        if (menu != null) {
                            if (!menu.isVisible()) {
                                open();
                            }
                            menu.getElement().focus();
                            menu.setActiveMenuItem(0);
                        }

                    }
                    break;
                    default: {
                        menuBar.onKeyDown(event);
                    }
                    break;
                }
            }
        });


    }

    public MenuBarItem(String text) {
        this();
        setText(text);
    }

    protected void setMenuBar(MenuBar menuBar) {
        this.menuBar = menuBar;

        if (menu != null) {
            menu.setParentMenu(this.menuBar);
        }
    }

    protected MenuBar getMenuBar() {
        return menuBar;
    }

    protected void close() {
        if (menu != null) {
            if (menu.isVisible())
                menu.close();
        }
    }

    protected void open() {
        if (menu != null) {

            if (!menu.isVisible()) {
                menu.setPosition(
                        getElement().getAbsoluteLeft(),
                        getElement().getAbsoluteTop() + getElement().getOffsetHeight()
                );

                menu.getElement().getStyle().setProperty("minWidth",
                        Math.max(getOffsetWidth() + 64.0, 128.0), Style.Unit.PX);

                menu.open();

                setSelected(true);
            }
        }
    }


    private void toggleMenu() {

        if (menu != null) {
            if (menu.isVisible()) {
                close();
            } else {
                open();
            }
        }
    }


    protected void setSelected(boolean selected) {

        if (selected) {
            getElement().setAttribute("aria-selected", "true");
            getElement().addClassName("appkit-state-selected");
            setFocus(true);
            getMenuBar().setActiveMenuItem(this);
        } else {
            getElement().setAttribute("aria-selected", "false");
            getElement().removeClassName("appkit-state-selected");
            setFocus(false);
            getMenuBar().setActiveMenuItem(null);
        }
    }

    public String getText() {
        return getElement().getInnerText();
    }

    public void setText(String text) {
        getElement().setInnerHTML(text);
    }

    public void setMenu(Menu menu) {

        if (menuCloseHandler != null) {
            menuCloseHandler.removeHandler();
        }

        this.menu = menu;

        if (this.menu != null) {

            this.menu.getElement().addClassName("appkit-menubar-menu");
            menuCloseHandler = this.menu.addCloseHandler(new CloseHandler<Menu>() {
                @Override
                public void onClose(CloseEvent<Menu> event) {
                    setSelected(false);
                }
            });
            if (menuBar != null) {
                this.menu.setParentMenu(menuBar);
            }

        }
    }

    @Override
    public void add(Widget w) {
        if (w instanceof Menu) {
            setMenu((Menu) w);
        }
    }

    @Override
    public void clear() {
        setMenu(null);
    }

    @Override
    public Iterator<Widget> iterator() {
        return menu.iterator();
    }

    @Override
    public boolean remove(Widget w) {
        if (w.equals(this.menu)) {
            setMenu(null);
            return true;
        }

        return false;
    }
}
