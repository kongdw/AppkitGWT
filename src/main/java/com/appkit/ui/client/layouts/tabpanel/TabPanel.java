package com.appkit.ui.client.layouts.tabpanel;


import com.google.gwt.animation.client.Animation;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.*;

import java.util.Iterator;

public class TabPanel extends Composite implements RequiresResize, HasWidgets, HasSelectionHandlers<TabItem> {

    public enum TabPosition {TOP, BOTTOM}

    private AbsolutePanel panel;
    private DeckPanel panelBody;
    private TabBar tabBar;

    private TabBarScrollButton scrollLeftButton = null;
    private TabBarScrollButton scrollRightButton = null;

    private TabPanelAppearance appearance;

    static TabPanelAppearance DEFAULT_APPEARANCE = GWT.create(DefaultTabPanelAppearance.class);

    public TabPanel(TabPanelAppearance appearance) {
        this.appearance = appearance;
        render();
    }

    @UiConstructor
    public TabPanel() {
        this(DEFAULT_APPEARANCE);
    }

    private void render() {

        panel = new AbsolutePanel();

        panel.addStyleName(appearance.css().tabPanelClass());

        initWidget(panel);

        tabBar = new TabBar();

        panel.add(tabBar);

        panelBody = new DeckPanel();
        panelBody.addStyleName(appearance.css().tabPanelBodyClass());
        panelBody.getElement().addClassName("appkit-tabpanel-body");

        panel.add(panelBody);

        scrollRightButton = new TabBarScrollButton();
        scrollRightButton.getElement().addClassName(appearance.css().tabPanelScrollRightBtnClass());
        scrollRightButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                tabBar.scrollIntoViewNextRightTab();
            }
        });

        panel.add(scrollRightButton);

        scrollLeftButton = new TabBarScrollButton();
        scrollLeftButton.getElement().addClassName(appearance.css().tabPanelScrollLeftBtnClass());

        scrollLeftButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                tabBar.scrollIntoViewNextLeftTab();
            }
        });

        panel.add(scrollLeftButton);

        setTabsPosition(TabPosition.TOP);

    }

    public void addTabItem(TabItem tabItem) {
        tabBar.addTabItem(tabItem);
    }

    public void removeTabItem(TabItem tabItem) {
        tabBar.removeTabItem(tabItem);
    }

    public void removeTab(int index) {

        if (index > -1 && index < tabBar.getWidgetCount()) {
            tabBar.removeTabItem((TabItem) tabBar.getWidget(index));
        }
    }

    public void setSelectedTab(TabItem item, boolean fireEvent) {

        tabBar.setSelectedTab(item);

        if (fireEvent) {
            SelectionEvent.fire(this, tabBar.getSelectedTab());
        }
    }

    public void setSelectedTabIndex(int index) {
        tabBar.setSelectedTabIndex(index);
    }

    public void setTabsPosition(TabPosition position) {

        TabPosition current = getTabsPosition();

        if (current != position) {
            tabBar.setPosition(position);

            if (position == TabPosition.BOTTOM) {

                getElement().removeClassName(appearance.css().tabPanelPositionTopClass());
                getElement().addClassName(appearance.css().tabPanelPositionBottomClass());

            } else {
                getElement().removeClassName(appearance.css().tabPanelPositionBottomClass());
                getElement().addClassName(appearance.css().tabPanelPositionTopClass());
            }
        }
    }

    public TabPosition getTabsPosition() {
        return tabBar.getPosition();
    }


    public TabBar getTabBar() {
        return tabBar;
    }

    public void onCloseTab(TabItem tabItem) {
        tabItem.close();
    }

    @Override
    public void onLoad() {

        super.onLoad();

        Scheduler.ScheduledCommand command = new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                tabBar.checkTabOverflow();

                if (tabBar.getSelectedTab() == null) {
                    tabBar.setSelectedTabIndex(0);
                }

                tabBar.setScrollLeft(0);
            }
        };

        Scheduler.get().scheduleDeferred(command);


    }

    @Override
    public void onResize() {

        Scheduler.ScheduledCommand cmd = new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {

                if (tabBar.checkTabOverflow()) {
                    tabBar.revealSelectedTab(false);
                }

                tabBar.adjustTabOverflow();
            }
        };

        Scheduler.get().scheduleDeferred(cmd);

    }

    @Override
    public void add(Widget w) {
        if (w instanceof TabItem) {
            addTabItem((TabItem) w);
        }
    }

    @Override
    public void clear() {
        tabBar.clear();
    }

    @Override
    public Iterator<Widget> iterator() {
        return panelBody.iterator();
    }

    @Override
    public boolean remove(Widget w) {
        if (w instanceof TabItem) {
            removeTabItem((TabItem) w);
            return true;
        }

        return false;
    }

    @Override
    public HandlerRegistration addSelectionHandler(SelectionHandler<TabItem> handler) {
        return addHandler(handler, SelectionEvent.getType());
    }


    protected class TabBar extends FlowPanel implements KeyDownHandler {

        private TabItem selectedTab = null;
        private TabPosition position = null;

        public TabBar() {
            getElement().addClassName(appearance.css().tabBarClass());
            getElement().setTabIndex(0);

            addDomHandler(this, KeyDownEvent.getType());

        }

        public void setPosition(TabPosition position) {
            if (this.position != position) {
                this.position = position;

                if (this.position == TabPosition.BOTTOM) {
                    getElement().removeClassName(appearance.css().tabBarPositionTopClass());
                    getElement().addClassName(appearance.css().tabBarPositionBottomClass());


                } else {

                    getElement().removeClassName(appearance.css().tabBarPositionBottomClass());
                    getElement().addClassName(appearance.css().tabBarPositionTopClass());
                }

                int count = getWidgetCount(),
                        i = 0;

                for (; i < count; i++) {
                    TabItem item = (TabItem) getWidget(i);
                    item.setTabPosition(getTabsPosition());
                }
            }
        }


        public TabPosition getPosition() {
            return position;
        }

        public void addTabItem(TabItem tabItem) {

            if (tabItem != null) {
                tabItem.setTabPanel(TabPanel.this);

                if (getWidgetCount() == 0) {
                    tabItem.setIsFirstTab(true);
                }

                tabItem.setTabPosition(getTabsPosition());

                add(tabItem);

                HasWidgets panel = tabItem.getPanel();
                if (panel instanceof Widget) {
                    panelBody.add((Widget) panel);
                }


            }

        }

        public void removeTabItem(TabItem tabItem) {

            if (tabItem != null) {

                int toSelect = -1;

                if (tabItem.isSelected()) {
                    toSelect = getWidgetIndex(tabItem);

                }

                setScrollLeft(Math.min(0, getScrollLeft() + tabItem.getElement().getOffsetWidth()));

                HasWidgets panel = tabItem.getPanel();
                if (panel instanceof Widget) {
                    panelBody.remove((Widget) panel);
                }

                remove(tabItem);

                if (getWidgetCount() > 0) {
                    TabItem first = (TabItem) getWidget(0);
                    first.setIsFirstTab(true);
                }

                if (toSelect > -1) {
                    while (toSelect >= getWidgetCount()) {
                        toSelect--;
                    }

                    if (toSelect > -1) {
                        setSelectedTabIndex(toSelect);
                    }
                }

                checkTabOverflow();
                adjustTabOverflow();

            }

        }

        public void setSelectedTab(TabItem tab) {

            if (selectedTab != null) {
                selectedTab.setSelected(false);
            }

            if (tab != null) {
                tab.setSelected(true);

                selectedTab = tab;
                revealTab(tab, true);

                HasWidgets panel = selectedTab.getPanel();
                if (panel instanceof Widget) {
                    panelBody.showWidget(panelBody.getWidgetIndex((Widget) panel));
                }
            }
        }

        public void setSelectedTabIndex(int index) {
            if (index > -1 && index < getWidgetCount()) {
                setSelectedTab((TabItem) getWidget(index));
            }
        }

        public TabItem getSelectedTab() {
            return selectedTab;
        }

        public int getSelectedTabIndex() {
            if (selectedTab != null)
                return getWidgetIndex(selectedTab);

            return -1;
        }

        public void setScrollLeft(double scrollLeft1) {

            getElement().getStyle().setLeft(scrollLeft1, Style.Unit.PX);
            checkTabOverflowButtonEnable();

        }

        public void scrollLeft(final double change, final boolean doAnimation) {

            if (checkTabOverflow()) {
                final double start = getScrollLeft();

                if (doAnimation) {
                    Animation animation = new Animation() {
                        @Override
                        protected void onUpdate(double progress) {
                            setScrollLeft(start + change * progress);
                        }
                    };

                    animation.run(200);
                } else {
                    setScrollLeft(start + change);
                }

            }

        }

        public int getScrollLeft() {
            return getElement().getOffsetLeft();
        }

        public void scrollIntoViewNextRightTab() {

            Iterator<Widget> tabItems = this.iterator();

            TabItem rightTab = null;

            while (tabItems.hasNext()) {
                Widget tabItem = tabItems.next();
                if (tabItem instanceof TabItem) {
                    int right = tabItem.getElement().getOffsetLeft() + tabItem.getOffsetWidth();
                    int max = getOffsetWidth() - getScrollLeft() - 21;
                    if (right > max) {
                        rightTab = (TabItem) tabItem;

                        if (tabItem.getElement().getOffsetLeft() + tabItem.getOffsetWidth() / 2 > max) {
                            break;
                        }
                    }
                }
            }

            if (rightTab != null) {
                revealTabRight(rightTab, true);
            }

        }

        public void scrollIntoViewNextLeftTab() {
            Iterator<Widget> tabItems = this.iterator();
            TabItem leftTab = null;
            while (tabItems.hasNext()) {
                Widget tabItem = tabItems.next();
                if (tabItem instanceof TabItem) {
                    int left = tabItem.getElement().getOffsetLeft() + getScrollLeft();
                    if (left < 21) {
                        leftTab = (TabItem) tabItem;
                    } else {
                        break;
                    }
                }
            }

            if (leftTab != null) {
                revealTabLeft(leftTab, true);
            }
        }

        public void revealTabRight(TabItem tabItem, boolean animate) {
            int right = tabItem.getElement().getOffsetLeft() + tabItem.getOffsetWidth();
            int left = getOffsetWidth() - getScrollLeft() - 21;
            int dx = right - left;
            if (dx >= 0) {
                scrollLeft(-dx, animate);
            }
        }

        public void revealTabLeft(TabItem tabItem, boolean animate) {
            int dx = tabItem.getElement().getOffsetLeft() + getScrollLeft() - 21;
            scrollLeft(-dx, animate);
        }

        public void revealTab(TabItem tabItem, boolean animate) {
            int right = tabItem.getElement().getOffsetLeft() + tabItem.getOffsetWidth();
            int left = tabItem.getElement().getOffsetLeft() + getScrollLeft();

            if (left < 21 && right > getOffsetWidth() - getScrollLeft() - 21) {
                return; //no space
            }

            if (left < 21) {
                revealTabLeft(tabItem, animate);
            } else if (right > getOffsetWidth() - getScrollLeft() - 21) {
                revealTabRight(tabItem, animate);
            }
        }

        public void revealSelectedTab(boolean animate) {

            if (selectedTab != null) {

                revealTab(selectedTab, animate);
            }
        }

        public void checkTabOverflowButtonEnable() {

            scrollLeftButton.setEnabled((getScrollLeft() < 0));

            TabItem farthestRightItem = (TabItem) getWidget(getWidgetCount() - 1);

            int right = farthestRightItem.getElement().getOffsetLeft()
                    + farthestRightItem.getOffsetWidth();
            double maxScrollLeft = right - getOffsetWidth();

            scrollRightButton.setEnabled((Math.abs(getScrollLeft()) < maxScrollLeft));

        }

        public boolean checkTabOverflow() {

            double dx = getElement().getScrollWidth() - getOffsetWidth();
            boolean overflow = (dx > 0);

            scrollRightButton.setVisible(overflow);
            scrollLeftButton.setVisible(overflow);

            checkTabOverflowButtonEnable();

            return overflow;
        }

        public void adjustTabOverflow() {

            TabItem farthestRightItem = (TabItem) getWidget(getWidgetCount() - 1);

            double sl = getScrollLeft();
            int right = farthestRightItem.getElement().getOffsetLeft()
                    + farthestRightItem.getOffsetWidth();

            int left = getOffsetWidth() - getScrollLeft() - 21;

            int dx = left - right;

            if (sl < 0 && dx > 0) {
                setScrollLeft(Math.min(0, sl + dx));
            }
        }

        @Override
        public void onKeyDown(KeyDownEvent event) {

            int keyCode = event.getNativeKeyCode();
            int selectedIndex = getSelectedTabIndex();
            switch (keyCode) {
                case KeyCodes.KEY_LEFT: {
                    event.preventDefault();
                    if (selectedIndex - 1 > -1) {
                        setSelectedTabIndex(selectedIndex - 1);
                    }

                }
                break;
                case KeyCodes.KEY_RIGHT: {
                    event.preventDefault();
                    if (selectedIndex + 1 < getWidgetCount()) {
                        setSelectedTabIndex(selectedIndex + 1);
                    }

                }
                break;
                case KeyCodes.KEY_X: {

                    event.preventDefault();
                    if (event.isControlKeyDown()) {
                        if (selectedTab != null) {
                            if (selectedTab.isClosable())
                                selectedTab.close();
                        }
                    }

                }
                break;
            }

        }
    }

}
