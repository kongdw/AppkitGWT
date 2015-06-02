package com.appkit.ui.client.layouts.tabpanel;


import com.appkit.ui.client.events.tap.TapEvent;
import com.appkit.ui.client.events.tap.TapHandler;
import com.appkit.ui.client.events.touch.TouchHandler;
import com.appkit.ui.client.widgets.button.ImageButton;
import com.appkit.ui.client.widgets.touch.TouchFlowPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.*;

import java.util.Iterator;

public class TabItem extends Composite implements HasWidgets, HasText,
        MouseDownHandler, MouseUpHandler,
        ClickHandler, TouchHandler, TapHandler {

    private Panel panel = null;
    private TouchFlowPanel elPanel;

    private TabPanel tabPanel = null;
    private ImageButton closeButton = null;

    private TabItemAppearance appearance;
    static TabItemAppearance DEFAULT_APPEARANCE = GWT.create(DefaultTabItemAppearance.class);

    public TabItem(TabItemAppearance appearance, Panel panel, String title) {

        this.appearance = appearance;
        this.panel = panel;
        render();
        setTitle(title);

    }

    public TabItem() {
        this(new HTMLPanel(""), "");
    }

    public TabItem(String title) {
        this(new HTMLPanel(""), title);
    }

    public TabItem(Panel panel, String title) {
        this(DEFAULT_APPEARANCE, panel, title);
    }

    private void render() {

        elPanel = new TouchFlowPanel();

        elPanel.setStyleName(appearance.css().tabItemClass());
        elPanel.getElement().addClassName("appkit-tabpanel-item");

        elPanel.addDomHandler(this, MouseDownEvent.getType());
        elPanel.addDomHandler(this, MouseUpEvent.getType());
        elPanel.addDomHandler(this, ClickEvent.getType());
        elPanel.addDomHandler(this, TouchStartEvent.getType());
        elPanel.addDomHandler(this, TouchEndEvent.getType());
        elPanel.addTapHandler(this);

        initWidget(elPanel);

    }

    protected void setIsFirstTab(boolean isFirstTab) {
        if (isFirstTab) {
            getElement().addClassName(appearance.css().tabItemFirstClass());
        } else {
            getElement().removeClassName("appkit-state-active");
        }
    }

    protected void setTabPosition(TabPanel.TabPosition tabPosition) {
        if (tabPosition == TabPanel.TabPosition.TOP) {
            getElement().removeClassName(appearance.css().tabItemBottom());
            getElement().addClassName(appearance.css().tabItemTop());
        } else if (tabPosition == TabPanel.TabPosition.BOTTOM) {
            getElement().removeClassName(appearance.css().tabItemTop());
            getElement().addClassName(appearance.css().tabItemBottom());
        }
    }

    protected TabPanel getTabPanel() {
        return tabPanel;
    }

    protected void setTabPanel(TabPanel tabPanel) {
        this.tabPanel = tabPanel;
    }

    public void setSelected(boolean selected) {
        if (selected) {
            getElement().addClassName("appkit-state-selected");
            elPanel.getElement().setAttribute("aria-selected", "true");
        } else {
            getElement().removeClassName("appkit-state-selected");
            elPanel.getElement().blur();
            elPanel.getElement().setAttribute("aria-selected", "false");
        }
    }

    public boolean isSelected() {
        return getElement().hasClassName("appkit-state-selected");
    }

    public void setClosable(boolean closable) {

        if (closable != isClosable()) {

            if (closable) {
                if (closeButton == null) {

                    closeButton = new ImageButton();
                    closeButton.setCanFocus(false);
                    closeButton.setStyleName(appearance.css().tabItemCloseButtonClass());
                    closeButton.addClickHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            tabPanel.onCloseTab(TabItem.this);
                        }
                    });

                    closeButton.addTouchHandler(new TouchHandler() {
                        @Override
                        public void onTouchCancel(TouchCancelEvent event) {

                        }

                        @Override
                        public void onTouchEnd(TouchEndEvent event) {
                            DOM.releaseCapture(closeButton.getElement());
                        }

                        @Override
                        public void onTouchMove(TouchMoveEvent event) {

                        }

                        @Override
                        public void onTouchStart(TouchStartEvent event) {
                            event.stopPropagation();
                            DOM.setCapture(closeButton.getElement());
                        }
                    });

                    elPanel.add(closeButton);
                }

                closeButton.setVisible(true);
                getElement().getStyle().setPaddingRight(32.0, Style.Unit.PX);

            } else {

                if (closeButton != null) {
                    closeButton.setVisible(false);
                }

                getElement().getStyle().setPaddingRight(20.0, Style.Unit.PX);
            }

        }
    }


    public boolean isClosable() {

        if (closeButton != null) {
            return closeButton.isVisible();
        }

        return false;
    }

    public void close() {

        if (tabPanel != null) {
            tabPanel.getTabBar().removeTabItem(this);
        }
    }

    public void setTitle(String title) {
        getElement().setInnerText(title);
    }

    public String getTitle() {
        return getElement().getInnerText();
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    public Panel getPanel() {
        return panel;
    }

    @Override
    public void add(Widget w) {
        if (w instanceof Panel) {
            panel = (Panel) w;
        }

    }

    @Override
    public void clear() {
        panel = null;
    }

    @Override
    public Iterator<Widget> iterator() {
        return null;
    }

    @Override
    public boolean remove(Widget w) {
        return false;
    }

    @Override
    public void onMouseDown(MouseDownEvent event) {

        event.preventDefault();
        DOM.setCapture(getElement());
        getElement().addClassName("appkit-state-active");
    }

    @Override
    public void onClick(ClickEvent event) {
        if (tabPanel != null) {
            tabPanel.getTabBar().setSelectedTab(this);
        }

    }

    @Override
    public void onMouseUp(MouseUpEvent event) {
        event.preventDefault();
        DOM.releaseCapture(getElement());
        getElement().removeClassName("appkit-state-active");
    }


    @Override
    public void onTap(TapEvent event) {

        if (tabPanel != null) {
            tabPanel.getTabBar().setSelectedTab(this);
        }
    }

    @Override
    public void onTouchCancel(TouchCancelEvent event) {

    }

    @Override
    public void onTouchEnd(TouchEndEvent event) {
        getElement().removeClassName("appkit-state-active");
        DOM.releaseCapture(getElement());
    }

    @Override
    public void onTouchMove(TouchMoveEvent event) {

    }

    @Override
    public void onTouchStart(TouchStartEvent event) {
        event.preventDefault();
        DOM.setCapture(getElement());

        getElement().addClassName("appkit-state-active");
    }

    @Override
    public String getText() {
        return getTitle();
    }

    @Override
    public void setText(String text) {
        setTitle(text);
    }
}
