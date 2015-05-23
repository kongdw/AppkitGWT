package com.appkit.ui.client.widgets.input.text;

import com.appkit.ui.client.events.cancel.CancelSearchEvent;
import com.appkit.ui.client.events.cancel.CancelSearchHandler;
import com.appkit.ui.client.events.cancel.HasCancelSearchHandlers;
import com.appkit.ui.client.events.search.HasPerformSearchHandlers;
import com.appkit.ui.client.events.search.PerformSearchEvent;
import com.appkit.ui.client.events.search.PerformSearchHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;

public class SearchField extends TextField implements HasPerformSearchHandlers<String>,
        HasCancelSearchHandlers {


    private Element searchEl;
    private Element cancelEl;

    private SearchFieldAppearance appearance;
    static SearchFieldAppearance DEFAULT_APPEARANCE = GWT.create(DefaultSearchFieldAppearance.class);

    public SearchField(SearchFieldAppearance appearance) {
        super(appearance);
    }

    public SearchField() {
        this(DEFAULT_APPEARANCE);
    }

    @Override
    protected void render() {

        super.render();

        appearance = (SearchFieldAppearance) getAppearance();

        getElement().addClassName(appearance.css().searchFieldClass());
        getElement().addClassName("appkit-searchfield");

        searchEl = DOM.createDiv();
        searchEl.setClassName(appearance.css().searchIconClass());

        getElement().appendChild(searchEl);

        cancelEl = DOM.createDiv();
        cancelEl.setClassName(appearance.css().searchCancelIconClass());


        final SearchField that = this;

        DOM.sinkEvents(cancelEl, Event.ONMOUSEDOWN | Event.ONMOUSEUP | Event.ONCLICK);
        DOM.setEventListener(cancelEl, new EventListener() {
            @Override
            public void onBrowserEvent(Event event) {

                if (event.getTypeInt() == Event.ONMOUSEDOWN) {
                    event.preventDefault();
                    event.stopPropagation();
                    cancelEl.addClassName(appearance.css().searchCancelActiveClass());
                    DOM.setCapture(cancelEl);
                } else if (event.getTypeInt() == Event.ONMOUSEUP) {

                    cancelEl.removeClassName(appearance.css().searchCancelActiveClass());
                    DOM.releaseCapture(cancelEl);
                } else if (event.getTypeInt() == Event.ONCLICK) {

                    setValue("");

                    Scheduler.ScheduledCommand command = new Scheduler.ScheduledCommand() {
                        @Override
                        public void execute() {
                            ValueChangeEvent.fire(that, getValue());
                            CancelSearchEvent.fire(that);
                        }
                    };

                    Scheduler.get().scheduleDeferred(command);
                }
            }
        });

        getElement().appendChild(cancelEl);

        addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                if (event.getValue().length() > 0) {
                    cancelEl.getStyle().setDisplay(Style.Display.BLOCK);
                } else {
                    cancelEl.getStyle().setDisplay(Style.Display.NONE);
                }
            }
        });

        addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    PerformSearchEvent.fire(that, getValue());
                }
            }
        });

        setPlaceholder("Search");

    }

    @Override
    public HandlerRegistration addPerformSearchHandler(PerformSearchHandler<String> handler) {
        return addHandler(handler, PerformSearchEvent.getType());
    }

    @Override
    public HandlerRegistration addCancelSearchHandler(CancelSearchHandler handler) {
        return addHandler(handler, CancelSearchEvent.getType());
    }


}
