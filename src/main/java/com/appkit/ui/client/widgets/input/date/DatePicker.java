package com.appkit.ui.client.widgets.input.date;

import com.appkit.ui.client.widgets.input.text.TextField;
import com.appkit.ui.client.widgets.popover.Popover;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;

import java.util.Date;

public class DatePicker extends Composite implements HasValue<Date> {

    private TextField textField;

    private Element toggleEl;
    private Popover popover;
    private CalendarView calendarView;

    private String dateFormat = "MM/dd/yyyy";

    private DatePickerAppearance appearance;
    static DatePickerAppearance DEFAULT_APPEARANCE = GWT.create(DefaultDatePickerAppearance.class);


    public DatePicker(DatePickerAppearance appearance) {
        this.appearance = appearance;
        render();

    }

    public DatePicker() {
        this(DEFAULT_APPEARANCE);
    }

    protected void render() {

        textField = new TextField();

        initWidget(textField);

        getElement().addClassName(appearance.css().datePickerClass());
        getElement().setAttribute("aria-haspopup", "true");

        toggleEl = DOM.createDiv();
        toggleEl.setAttribute("role", "button");
        toggleEl.setClassName(appearance.css().datePickerToggleClass());

        DOM.sinkEvents(toggleEl, Event.ONMOUSEDOWN | Event.ONTOUCHSTART | Event.ONTOUCHEND
                | Event.ONMOUSEUP | Event.ONCLICK);

        DOM.setEventListener(toggleEl, new EventListener() {
            @Override
            public void onBrowserEvent(Event event) {
                if (event.getTypeInt() == Event.ONMOUSEDOWN ||
                        event.getTypeInt() == Event.ONTOUCHSTART) {
                    event.preventDefault();
                    if (isEnabled()) {
                        DOM.setCapture(toggleEl);
                        toggleEl.addClassName(appearance.css().datePickerToggleActive());
                    }


                } else if (event.getTypeInt() == Event.ONMOUSEUP ||
                        event.getTypeInt() == Event.ONTOUCHEND) {
                    toggleEl.removeClassName(appearance.css().datePickerToggleActive());
                    DOM.releaseCapture(toggleEl);
                } else if (event.getTypeInt() == Event.ONCLICK) {
                    if (textField.isEnabled()) {
                        showCalendar();
                    }
                }
            }
        });

        getElement().appendChild(toggleEl);

        textField.setMaxLength(10);

        popover = new Popover();
        popover.setTransient(true);

        calendarView = new CalendarView();
        calendarView.addValueChangeHandler(new ValueChangeHandler<Date>() {
            @Override
            public void onValueChange(ValueChangeEvent<Date> event) {
                setValue(calendarView.getValue(), true);
                popover.setVisible(false);
            }
        });

        popover.add(calendarView);

        textField.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {

                if (textField.getText().length() == 10) {
                    Date value = getValue();
                    if (value != null) {
                        setValue(value);
                    }

                    if (popover.isVisible()) {
                        updateValue();
                    }
                }
            }
        });

        textField.addMouseDownHandler(new MouseDownHandler() {
            @Override
            public void onMouseDown(MouseDownEvent event) {
                if (popover.isVisible()) {
                    event.stopPropagation();
                }
            }
        });

        setDateFormat(dateFormat);
    }


    private void updateValue() {

        Date value = getValue();
        setValue(value);

        if (value != null) {
            Date month = CalendarUtil.copyDate(value);
            CalendarUtil.setToFirstDayOfMonth(month);

            if (!value.equals(calendarView.getDisplayedMonth())) {
                calendarView.setDisplayedMonth(month);
                calendarView.displayMonth();
            }
        }
    }

    private void showCalendar() {

        updateValue();

        popover.setVisible(true);

        if (getAbsoluteTop() + 27 + popover.getOffsetHeight() <
                Window.getClientHeight()) {
            popover.setPosition(
                    getAbsoluteLeft(),
                    getAbsoluteTop() + 28
            );
        } else {
            popover.setPosition(
                    getAbsoluteLeft(),
                    getAbsoluteTop() - popover.getOffsetHeight() - 4
            );
        }
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;

        if (dateFormat != null) {
            String mask = this.dateFormat.replaceAll("[^/]", "9");
            textField.setMask(mask);
        }
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setMinimumDate(Date minDate) {
        calendarView.setMinimumDate(minDate);

    }

    public Date getMinimumDate() {
        return calendarView.getMinimumDate();
    }

    public void setMaximumDate(Date maxDate) {
        calendarView.setMaximumDate(maxDate);

    }

    public Date getMaximumDate() {
        return calendarView.getMaximumDate();
    }

    public void setPlaceholder(String placeholder) {
        textField.setPlaceholder(placeholder);
    }

    public String getPlaceholder() {
        return textField.getPlaceholder();
    }

    public void setEnabled(boolean enabled) {
        textField.setEnabled(enabled);
    }

    public boolean isEnabled() {
        return textField.isEnabled();
    }

    private Date dateFromString(String text) {

        try {
            return DateTimeFormat.getFormat(dateFormat).parse(text);
        } catch (Exception e) {
        }

        return null;
    }


    @Override
    public Date getValue() {
        Date value = dateFromString(textField.getText());

        if (value != null) {
            if (getMinimumDate() != null) {
                value = CalendarUtil.getLatest(value, getMinimumDate());
            }

            if (getMaximumDate() != null) {
                value = CalendarUtil.getEarliest(value, getMaximumDate());
            }
        }

        return value;

    }

    @Override
    public void setValue(Date value) {

        try {
            calendarView.setValue(value);
            textField.setText(DateTimeFormat.getFormat(dateFormat).format(calendarView.getValue()));
        } catch (Exception e) {
        }

    }

    @Override
    public void setValue(Date value, boolean fireEvents) {

        setValue(value);

        if (fireEvents) {
            ValueChangeEvent.fire(this, getValue());
        }
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Date> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

    @Override
    public void onLoad() {

        super.onLoad();
        calendarView.update();
    }
}
