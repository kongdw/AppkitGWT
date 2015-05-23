package com.appkit.ui.client.widgets.input.radio;


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

public class RadioButton extends Control implements HasValue<RadioButton.State> {

    private Element cell;
    private Element radioKnob;
    private Element radioKnobInner;
    private Element label;

    public enum State {ON, OFF}

    private State value = State.OFF;

    private RadioGroup radioGroup;

    private RadioButtonAppearance appearance;

    static RadioButtonAppearance DEFAULT_APPEARANCE = GWT.create(DefaultRadioButtonAppearance.class);


    public RadioButton(RadioButtonAppearance appearance) {
        super(DOM.createDiv());
        this.appearance = appearance;
        render();
    }

    public RadioButton() {
        this(DEFAULT_APPEARANCE);
    }

    private void render() {

        getElement().setClassName(appearance.css().radioButtonClass());
        getElement().setAttribute("role", "radio");
        getElement().addClassName("appkit-radio");

        cell = DOM.createDiv();
        cell.setClassName(appearance.css().radioButtonCellClass());

        Element radioCell = DOM.createDiv();
        radioCell.setClassName(appearance.css().radioCellClass());

        radioKnob = DOM.createDiv();
        radioKnob.setClassName(appearance.css().radioClass());

        radioKnobInner = DOM.createDiv();
        radioKnobInner.setClassName(appearance.css().radioInnerClass());

        radioKnob.appendChild(radioKnobInner);

        cell.appendChild(radioKnob);

        label = DOM.createDiv();
        label.setClassName(appearance.css().radioLabelClass());

        cell.appendChild(label);

        getElement().appendChild(cell);


        DOM.sinkEvents(cell, Event.ONCLICK | Event.ONMOUSEDOWN);
        DOM.setEventListener(cell, new EventListener() {
            @Override
            public void onBrowserEvent(Event event) {
                if (event.getTypeInt() == Event.ONCLICK) {
                    if (isEnabled()) {
                        if (getValue() != State.ON) {
                            setValue(State.ON, true);
                        }
                    }
                } else if (event.getTypeInt() == Event.ONMOUSEDOWN) {
                    event.preventDefault();
                }
            }
        });


        sinkEvents(Event.ONBLUR | Event.ONFOCUS | Event.ONKEYDOWN | Event.ONMOUSEDOWN);
    }

    public RadioButtonAppearance getAppearance() {
        return appearance;
    }

    public void setRadioGroup(String groupName) {

        if (!RadioGroup.RADIO_GROUPS.containsKey(groupName)) {
            RadioGroup rg = new RadioGroup(groupName);
            RadioGroup.RADIO_GROUPS.put(groupName, rg);
        }


        RadioGroup.RADIO_GROUPS.get(groupName).add(this);

    }

    protected void setRadioGroup(RadioGroup rg) {
        radioGroup = rg;
    }

    protected RadioGroup getRadioGroup() {
        return radioGroup;
    }


    public void setTitle(String title) {
        label.setInnerHTML(title);
        radioKnob.getStyle().setMarginTop(
                Math.max(3.0, Math.round((getOffsetHeight() - radioKnob.getOffsetHeight()) / 2.0 - 5.5))
                , Style.Unit.PX);
    }


    public String getTitle() {
        return label.getInnerText();
    }


    public void setValue(State state) {

        if (value != state) {

            if (state == State.ON) {
                if (getRadioGroup() != null) {
                    radioGroup.unselectAll();
                }
            }

            value = state;

            if (value == State.ON) {
                getElement().addClassName(appearance.css().radioSelectedClass());

            } else {

                getElement().removeClassName(appearance.css().radioSelectedClass());

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
                    if (getValue() != State.ON) {
                        setValue(State.ON);
                        ValueChangeEvent.fire(this, getValue());
                    }
                }
            }
        } else if (event.getTypeInt() == Event.ONFOCUS) {
            getElement().addClassName(appearance.css().focusClass());
        } else if (event.getTypeInt() == Event.ONBLUR) {
            getElement().removeClassName(appearance.css().focusClass());
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
