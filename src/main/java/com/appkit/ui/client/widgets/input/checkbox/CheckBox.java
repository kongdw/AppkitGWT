package com.appkit.ui.client.widgets.input.checkbox;

import com.appkit.ui.client.widgets.Control;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.HasValue;


public class CheckBox extends Control implements HasValue<CheckBox.State> {

    private Element cell;
    private Element checkKnob;
    private Element label;

    public enum State {ON, OFF}

    private State value = State.OFF;

    private CheckBoxAppearance appearance;

    static CheckBoxAppearance DEFAULT_APPEARANCE = GWT.create(DefaultCheckBoxAppearance.class);

    public CheckBox(String title) {
        this(DEFAULT_APPEARANCE);
        setTitle(title);
    }

    public CheckBox(CheckBoxAppearance appearance) {
        super(DOM.createDiv());
        this.appearance = appearance;
        render();
    }

    public CheckBox() {
        this(DEFAULT_APPEARANCE);
    }

    private void render() {

        getElement().setClassName(appearance.css().checkboxClass());
        getElement().addClassName("appkit-checkbox");
        getElement().setAttribute("role", "checkbox");

        cell = DOM.createDiv();
        cell.setClassName(appearance.css().checkboxCellClass());

        Element checkKnobCell = DOM.createDiv();
        checkKnobCell.setClassName(appearance.css().checkboxBezelCellClass());

        checkKnob = DOM.createDiv();
        checkKnob.setClassName(appearance.css().checkboxBezelClass());

        checkKnobCell.appendChild(checkKnob);

        cell.appendChild(checkKnobCell);

        label = DOM.createDiv();
        label.setClassName(appearance.css().checkboxLabelClass());

        cell.appendChild(label);

        getElement().appendChild(cell);

        DOM.sinkEvents(cell, Event.ONCLICK | Event.ONMOUSEDOWN);
        DOM.setEventListener(cell, new EventListener() {
            @Override
            public void onBrowserEvent(Event event) {
                if (event.getTypeInt() == Event.ONCLICK) {
                    if (isEnabled()) {
                        toggle(true);
                    }
                } else if (event.getTypeInt() == Event.ONMOUSEDOWN) {
                    event.preventDefault();
                }
            }
        });

        sinkEvents(Event.ONBLUR | Event.ONFOCUS | Event.ONKEYDOWN | Event.ONMOUSEDOWN);

    }

    public CheckBoxAppearance getAppearance() {
        return appearance;
    }

    public void setTitle(String title) {
        label.setInnerHTML(title);
        checkKnob.getStyle().setMarginTop(
                Math.max(3.0, Math.round((getOffsetHeight() - checkKnob.getOffsetHeight()) / 2.0 - 5.5))
                , Style.Unit.PX);
    }

    public String getTitle() {
        return label.getInnerText();
    }

    public void toggle(boolean fireEvent) {
        if (value == State.OFF) {
            setValue(State.ON, fireEvent);
        } else {
            setValue(State.OFF, fireEvent);
        }
    }

    public void setValue(State state) {

        if (value != state) {
            value = state;

            if (value == State.ON) {
                checkKnob.addClassName(appearance.css().iconCheckClass());
                getElement().setAttribute("aria-checked", "true");
            } else {
                checkKnob.removeClassName(appearance.css().iconCheckClass());
                getElement().setAttribute("aria-checked", "false");
            }


        }
    }

    @Override
    public void setValue(State value, boolean fireEvents) {

        State oldValue = getValue();

        setValue(value);

        if (fireEvents && oldValue != getValue()) {
            ValueChangeEvent.fire(this, getValue());
        }
    }

    public State getValue() {
        return value;
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<State> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

    @Override
    public void onBrowserEvent(Event event) {

        if (event.getTypeInt() == Event.ONMOUSEDOWN) {

            event.preventDefault();

        } else if (event.getTypeInt() == Event.ONKEYDOWN) {

            if (event.getKeyCode() == KeyCodes.KEY_ENTER) {
                if (isEnabled()) {
                    toggle(true);
                }
            }
        } else if (event.getTypeInt() == Event.ONFOCUS) {
            getElement().addClassName("appkit-state-focus");
        } else if (event.getTypeInt() == Event.ONBLUR) {
            getElement().removeClassName("appkit-state-focus");
        }
    }

    @Override
    public void setEnabled(boolean enabled) {

        super.setEnabled(enabled);

        if (!this.isEnabled()) {
            getElement().addClassName(appearance.css().disabledClass());
            getElement().setTabIndex(-2);
        } else {
            getElement().removeClassName(appearance.css().disabledClass());
            getElement().setTabIndex(0);
        }
    }


}
