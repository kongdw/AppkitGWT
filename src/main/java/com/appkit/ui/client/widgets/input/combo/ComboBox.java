package com.appkit.ui.client.widgets.input.combo;

import com.appkit.ui.client.widgets.input.text.AbstractTextField;
import com.appkit.ui.client.widgets.menu.AbstractMenuItem;
import com.appkit.ui.client.widgets.menu.Menu;
import com.appkit.ui.client.widgets.menu.MenuItem;
import com.appkit.ui.client.widgets.popover.Popover;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.logical.shared.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.Iterator;


public class ComboBox extends AbstractTextField implements
        HasSelectionHandlers<ComboBox>, HasWidgets {

    private boolean autocomplete;

    private Element toggleEl;

    private Menu dropDownMenu;

    private HandlerRegistration dropDownSelectionHandler;
    private HandlerRegistration closeMenuHandler;

    private ComboBoxAppearance appearance;
    static ComboBoxAppearance DEFAULT_APPEARANCE = GWT.create(DefaultComboBoxAppearance.class);

    public ComboBox(ComboBoxAppearance appearance) {
        super(appearance);
    }

    public ComboBox() {
        this(DEFAULT_APPEARANCE);
    }

    protected void render() {

        super.render();

        appearance = (ComboBoxAppearance) getAppearance();

        getElement().addClassName(appearance.css().comboBoxClass());
        getElement().addClassName("appkit-combobox");
        getElement().setAttribute("role", "combobox");
        getElement().setAttribute("aria-haspopup", "true");

        toggleEl = DOM.createDiv();
        toggleEl.setAttribute("role", "button");
        toggleEl.setClassName(appearance.css().comboBoxToggleClass());

        DOM.sinkEvents(toggleEl, Event.ONMOUSEDOWN | Event.ONMOUSEUP | Event.ONCLICK);
        DOM.setEventListener(toggleEl, new EventListener() {
            @Override
            public void onBrowserEvent(Event event) {
                if (event.getTypeInt() == Event.ONMOUSEDOWN) {
                    event.preventDefault();

                    if (isEnabled()) {
                        event.stopPropagation();
                        DOM.setCapture(toggleEl);
                        toggleEl.addClassName(appearance.css().comboBoxToggleActive());

                        Popover.closeTransientPopoverIfNecessary(ComboBox.this);


                    }

                } else if (event.getTypeInt() == Event.ONMOUSEUP) {
                    toggleEl.removeClassName(appearance.css().comboBoxToggleActive());
                    DOM.releaseCapture(toggleEl);
                } else if (event.getTypeInt() == Event.ONCLICK) {
                    if (isEnabled()) {
                        if (isMenuOpen()) {
                            closeMenu();
                        } else {
                            openMenu();
                        }
                    }
                }
            }
        });

        DOM.sinkEvents(input, Event.ONPASTE | Event.ONKEYDOWN | Event.ONFOCUS | Event.ONBLUR);
        DOM.setEventListener(input, new EventListener() {
            @Override

            public void onBrowserEvent(Event event) {
                if (event.getTypeInt() == Event.ONKEYDOWN || event.getTypeInt() == Event.ONPASTE) {

                    if (event.getKeyCode() == KeyCodes.KEY_DOWN) {
                        event.preventDefault();
                        if (dropDownMenu != null) {
                            openMenu();
                            dropDownMenu.getElement().focus();
                            dropDownMenu.setActiveMenuItem(dropDownMenu.getNextMenuItem(-1));
                        }
                    }


                    final String oldValue = getValue();

                    Scheduler.ScheduledCommand command = new Scheduler.ScheduledCommand() {
                        @Override
                        public void execute() {
                            if (!oldValue.equals(getValue())) {
                                ValueChangeEvent.fire(ComboBox.this, getValue());
                            }
                        }
                    };

                    Scheduler.get().scheduleDeferred(command);


                } else if (event.getTypeInt() == Event.ONFOCUS) {

                    getElement().addClassName(appearance.css().comboBoxFocusClass());
                    input.addClassName(appearance.css().activeClass());
                    FocusEvent.fireNativeEvent(event, ComboBox.this);
                } else if (event.getTypeInt() == Event.ONBLUR) {

                    input.removeClassName(appearance.css().activeClass());

                    if (!dropDownMenu.isVisible()) {
                        getElement().removeClassName(appearance.css().comboBoxFocusClass());
                    }

                    BlurEvent.fireNativeEvent(event, ComboBox.this);
                }

            }
        });


        getElement().appendChild(toggleEl);


        addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                if (isAutocomplete()) {
                    doAutocomplete();
                }

                if (dropDownMenu != null) {
                    String text = getText().trim();
                    if (text.length() > 0) {
                        int idx = dropDownMenu.indexOfItemEqualToString(text);
                        setSelectedIndex(idx);
                    } else {
                        setSelectedIndex(-1);
                    }
                }

            }
        });
    }

    private void doAutocomplete() {

        if (dropDownMenu != null) {
            String toMatch = input.getValue().toUpperCase().trim();

            if (toMatch.length() > 0) {
                int count = dropDownMenu.getMenuItemCount(),
                        i = 0;

                boolean found = false;
                for (; i < count; i++) {
                    AbstractMenuItem item = dropDownMenu.getMenuItem(i);
                    if (item instanceof MenuItem) {
                        String title = ((MenuItem) item).getText().toUpperCase();
                        if (!title.startsWith(toMatch)) {
                            item.setVisible(false);
                        } else {
                            found = true;
                            item.setVisible(true);
                        }
                    }
                }

                if (found)
                    openMenu();
            } else {
                int count = dropDownMenu.getMenuItemCount(),
                        i = 0;
                for (; i < count; i++) {
                    AbstractMenuItem item = dropDownMenu.getMenuItem(i);
                    item.setVisible(true);
                }
            }
        }
    }

    public void closeMenu() {
        if (dropDownMenu != null) {
            dropDownMenu.close();
        }
    }

    public void openMenu() {
        if (dropDownMenu != null) {

            if (!dropDownMenu.isVisible()) {
                dropDownMenu.getElement().getStyle().setWidth(
                        getElement().getOffsetWidth(), Style.Unit.PX
                );

                dropDownMenu.getElement().getStyle().setProperty("borderTopWidth", "1px");
                dropDownMenu.getElement().getStyle().setProperty("borderRadius", "5px");

                dropDownMenu.open();

                int yPos = getAbsoluteTop() + getOffsetHeight();
                int menuHeight = dropDownMenu.getOffsetHeight();

                if (yPos + menuHeight > RootPanel.get().getOffsetHeight()) {
                    yPos = Math.max(0, getElement().getAbsoluteTop() - menuHeight);

                } else {
                    dropDownMenu.getElement().getStyle().setProperty("borderTopWidth", "0");
                    dropDownMenu.getElement().getStyle().setProperty("borderRadius", "0 0 5px 5px");
                }

                dropDownMenu.setPosition(
                        getAbsoluteLeft(),
                        yPos
                );

                int idx = this.getSelectedIndex();
                if (idx > -1) {
                    dropDownMenu.setActiveMenuItem(idx);
                    dropDownMenu.getActiveMenuItem().getElement().scrollIntoView();

                } else {
                    dropDownMenu.getElement().setScrollTop(0);
                }

                dropDownMenu.getElement().focus();

            }
        }

    }

    public boolean isMenuOpen() {
        return dropDownMenu.isVisible();
    }


    public void setSelectedIndex(int index) {
        if (dropDownMenu != null) {
            dropDownMenu.setSelectedIndex(index);
        }
    }

    public int getSelectedIndex() {
        if (dropDownMenu != null) {
            return dropDownMenu.getLastSelectedIndex();
        }

        return -1;
    }

    public int getNumberOfItems() {
        if (dropDownMenu != null) {
            return dropDownMenu.getMenuItemCount();
        }

        return 0;
    }

    public void setMenu(final Menu dropDownMenu) {

        if (dropDownSelectionHandler != null) {
            dropDownSelectionHandler.removeHandler();
        }

        if (closeMenuHandler != null) {
            closeMenuHandler.removeHandler();
        }

        this.dropDownMenu = dropDownMenu;

        if (this.dropDownMenu != null) {
            this.dropDownMenu.getElement().addClassName(
                    appearance.css().menuComboBoxClass());
            dropDownSelectionHandler = this.dropDownMenu.addSelectionHandler(new SelectionHandler() {
                @Override
                public void onSelection(SelectionEvent event) {
                    setValue(dropDownMenu.getLastSelectedItem().getText());
                    SelectionEvent.fire(ComboBox.this, ComboBox.this);
                }
            });

            closeMenuHandler = this.dropDownMenu.addCloseHandler(new CloseHandler() {
                @Override
                public void onClose(CloseEvent event) {
                    input.removeClassName(appearance.css().activeClass());
                    getElement().removeClassName(appearance.css().comboBoxFocusClass());
                }
            });

        }
    }

    public Menu getMenu() {
        return dropDownMenu;
    }

    public void setAutocomplete(boolean autocomplete) {
        this.autocomplete = autocomplete;
    }

    public boolean isAutocomplete() {
        return autocomplete;
    }


    @Override
    public HandlerRegistration addSelectionHandler(SelectionHandler<ComboBox> handler) {
        return addHandler(handler, SelectionEvent.getType());
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
        return null;
    }

    @Override
    public boolean remove(Widget w) {
        if (w.equals(getMenu())) {
            setMenu(null);
            return true;
        }

        return false;
    }
}
