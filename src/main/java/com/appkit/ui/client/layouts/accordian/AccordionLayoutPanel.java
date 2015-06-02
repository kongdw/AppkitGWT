package com.appkit.ui.client.layouts.accordian;


import com.appkit.collection.client.JsLightArray;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.logical.shared.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import java.util.Iterator;

public class AccordionLayoutPanel extends Composite implements HasWidgets, HasSelectionHandlers<Integer> {

    com.google.gwt.user.client.ui.StackLayoutPanel panel;
    private JsLightArray<Element> expandBtns;

    static AccordionLayoutAppearance DEFAULT_APPEARANCE = GWT.create(DefaultAccordionLayoutAppearance.class);
    private AccordionLayoutAppearance appearance;

    public AccordionLayoutPanel(AccordionLayoutAppearance appearance) {
        this.appearance = appearance;
        render();
    }

    public AccordionLayoutPanel() {
        this(DEFAULT_APPEARANCE);
    }

    private void render() {
        panel = new com.google.gwt.user.client.ui.StackLayoutPanel(Style.Unit.PX);

        //add dummy last panel
        HTML hidden = new HTML();
        panel.add(hidden, "", 0);
        panel.addBeforeSelectionHandler(new BeforeSelectionHandler<Integer>() {
            @Override
            public void onBeforeSelection(BeforeSelectionEvent<Integer> event) {
                event.cancel();
            }
        });

        expandBtns = new JsLightArray<Element>();

        initWidget(panel);
    }

    public void add(Widget content, String header) {

        final Widget contentWidget = content;

        panel.insert(content, header, 28.0, panel.getWidgetCount() - 1);

        final Element btn = DOM.createDiv();
        btn.addClassName(appearance.css().expandButtonClass());
        btn.setAttribute("role", "button");
        btn.setTabIndex(0);

        DOM.sinkEvents(btn, Event.MOUSEEVENTS | Event.ONKEYDOWN);

        DOM.setEventListener(btn, new EventListener() {
            @Override
            public void onBrowserEvent(Event event) {
                switch (event.getTypeInt()) {
                    case Event.ONMOUSEDOWN: {
                        event.preventDefault();
                        DOM.setCapture(btn);
                        btn.addClassName("appkit-state-active");
                    }
                    break;
                    case Event.ONMOUSEUP: {
                        DOM.releaseCapture(btn);
                        btn.removeClassName("appkit-state-active");
                    }
                    case Event.ONCLICK: {
                        int idx = panel.getWidgetIndex(contentWidget);
                        doSelect(idx);
                    }
                    case Event.ONKEYDOWN: {
                        if (event.getKeyCode() == KeyCodes.KEY_ENTER) {
                            int idx = panel.getWidgetIndex(contentWidget);
                            doSelect(idx);
                        }
                    }

                }
            }
        });

        Widget w = panel.getHeaderWidget(content);
        w.getElement().appendChild(btn);

        expandBtns.push(btn);
    }

    private void doSelect(int idx) {

        int count = expandBtns.length();
        int i = 0;
        for (; i < count; i++) {
            Element expandBtn = expandBtns.get(i);
            expandBtn.removeClassName(appearance.css().collapseButtonClass());
        }

        if (idx == panel.getVisibleIndex()) {
            panel.showWidget(panel.getWidgetCount() - 1, false);
        } else if (idx > -1 && idx < getWidgetCount()) {
            panel.showWidget(idx, false);
            Element btn = expandBtns.get(idx);
            btn.addClassName(appearance.css().collapseButtonClass());
            SelectionEvent.fire(AccordionLayoutPanel.this, getVisibleWidget());
        }

    }

    public boolean removeAtIndex(int index) {

        if (index > -1 && index < getWidgetCount()) {
            expandBtns.remove(index);
            panel.remove(index);
            panel.forceLayout();
            return true;
        }

        return false;

    }

    public int getVisibleWidget() {
        if (panel.getVisibleIndex() == panel.getWidgetCount() - 1) {
            return -1;
        }

        return panel.getVisibleIndex();
    }

    public int getWidgetCount() {
        return panel.getWidgetCount() - 1;
    }

    public void showWidgetAtIndex(int index) {
        showWidgetAtIndex(index, false);
    }

    public void showWidgetAtIndex(int index, boolean fireEvent) {

        int tempIdx = index;

        if (index < 0 || index > getWidgetCount() - 1) {
            tempIdx = panel.getWidgetCount() - 1;
        }

        panel.showWidget(tempIdx, false);

        if (fireEvent && index > -1) {
            SelectionEvent.fire(this, getVisibleWidget());
        }
    }

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
        int idx = panel.getWidgetIndex(w);
        return removeAtIndex(idx);

    }

    @Override
    public HandlerRegistration addSelectionHandler(SelectionHandler<Integer> handler) {
        return addHandler(handler, SelectionEvent.getType());
    }
}
