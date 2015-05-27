package com.appkit.ui.client.widgets.input.date;

import com.appkit.ui.client.widgets.Control;
import com.appkit.ui.client.widgets.button.ImageButton;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CalendarView extends Composite implements HasValue<Date> {

    private FlowPanel panel;

    private DateTimeFormat monthFormat;

    private CalendarModel calendarModel;

    private Date displayedMonth;
    private Date currentDate;
    private Date value;
    private Date minDate = null;
    private Date maxDate = null;

    private boolean canSelectDate;

    private ImageButton nextMonthButton;
    private ImageButton nextYearButton;
    private ImageButton prevMonthButton;
    private ImageButton prevYearButton;

    private Grid monthDayTable;
    private TableElement dayLabelTable;
    private Label monthLabel;

    private Map<Date, DateCell> dateTableElements;
    private DateCell firstActive;

    private CalendarViewAppearance appearance;
    private static CalendarViewAppearance DEFAULT_APPEARANCE = GWT.create(DefaultCalendarViewAppearance.class);

    public CalendarView() {
        this(DEFAULT_APPEARANCE);
    }

    public CalendarView(CalendarViewAppearance appearance) {
        this.appearance = appearance;
        render();
    }

    private void render() {

        panel = new FlowPanel();

        panel.setStyleName(appearance.css().calendarClass());

        calendarModel = new CalendarModel();

        monthDayTable = new Grid(6, 7);
        monthDayTable.setCellSpacing(0);
        monthDayTable.setCellPadding(0);

        monthDayTable.addStyleName(appearance.css().monthDayTableClass());

        monthLabel = new Label();
        monthLabel.setStyleName(appearance.css().monthLabelClass());

        panel.add(monthLabel);

        dayLabelTable = TableElement.as(DOM.createTable());

        panel.getElement().appendChild(dayLabelTable);

        monthFormat = DateTimeFormat.getFormat("MMMM yyyy");

        dateTableElements = new HashMap<Date, DateCell>();

        prevYearButton = new ImageButton();
        prevYearButton.addStyleName(appearance.css().prevYearBtnClass());
        prevYearButton.setContinuous(true);
        prevYearButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                changeYear(-1);
            }
        });

        panel.add(prevYearButton);

        prevMonthButton = new ImageButton();
        prevMonthButton.addStyleName(appearance.css().prevMonthBtnClass());
        prevMonthButton.setContinuous(true);
        prevMonthButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                changeMonth(-1);
            }
        });

        panel.add(prevMonthButton);

        nextMonthButton = new ImageButton();
        nextMonthButton.addStyleName(appearance.css().nextMonthBtnClass());
        nextMonthButton.setContinuous(true);

        nextMonthButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                changeMonth(1);
            }
        });
        panel.add(nextMonthButton);

        nextYearButton = new ImageButton();
        nextYearButton.addStyleName(appearance.css().nextYearBtnClass());
        nextYearButton.setContinuous(true);

        nextYearButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                changeYear(1);
            }
        });

        panel.add(nextYearButton);

        panel.add(monthDayTable);

        setCanSelectDate(true);
        setCanChangeYear(true);
        setCanChangeMonth(true);

        initWidget(panel);

    }

    public void setCanSelectDate(boolean canSelectDate) {
        this.canSelectDate = canSelectDate;
    }

    public boolean getCanSelectDate() {
        return canSelectDate;
    }

    public void setCanChangeMonth(boolean canChangeMonth) {

        prevMonthButton.setVisible(canChangeMonth);
        nextMonthButton.setVisible(canChangeMonth);

    }

    public boolean getCanChangeMonth() {
        return prevMonthButton.isVisible();
    }

    public void setCanChangeYear(boolean canChangeYear) {

        prevYearButton.setVisible(canChangeYear);
        nextYearButton.setVisible(canChangeYear);

        if (canChangeYear) {
            prevMonthButton.getElement().getStyle().setLeft(30, Style.Unit.PX);
            nextMonthButton.getElement().getStyle().setRight(30, Style.Unit.PX);
        } else {
            prevMonthButton.getElement().getStyle().setLeft(10, Style.Unit.PX);
            nextMonthButton.getElement().getStyle().setRight(10, Style.Unit.PX);
        }
    }

    public boolean getCanChangeYear() {
        return prevYearButton.isVisible();
    }

    public void setDisplayedMonth(Date displayedMonth) {
        this.displayedMonth = displayedMonth;
        calendarModel.setCurrentMonth(this.displayedMonth);
    }

    public Date getDisplayedMonth() {
        return displayedMonth;
    }

    public void setMinimumDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getMinimumDate() {
        return minDate;
    }

    public void setMaximumDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public Date getMaximumDate() {
        return maxDate;
    }

    protected void changeMonth(int numMonths) {

        Date date = CalendarUtil.copyDate(displayedMonth);
        CalendarUtil.addMonthsToDate(date, numMonths);

        setDisplayedMonth(date);
        displayMonth();
    }

    protected void changeYear(int numYears) {

        Date date = CalendarUtil.copyDate(displayedMonth);
        CalendarUtil.addYearsToDate(date, numYears);

        setDisplayedMonth(date);
        displayMonth();
    }

    public void update() {
        displayMonth();
    }

    public void displayMonth() {

        if (displayedMonth == null) {
            displayedMonth = new Date();
            CalendarUtil.setToFirstDayOfMonth(displayedMonth);
            setDisplayedMonth(displayedMonth);
        }

        dateTableElements.clear();

        currentDate = new Date();
        firstActive = null;

        monthLabel.setText(monthFormat.format(displayedMonth));

        monthDayTable.clear();
        dayLabelTable.removeAllChildren();

        Element dayLabelRow = DOM.createTR();

        for (int i = 0; i < CalendarModel.DAYS_IN_WEEK; i++) {

            Element dayTd = DOM.createTD();

            dayTd.setInnerText(calendarModel.formatDayOfWeek(i));
            dayTd.addClassName(appearance.css().dayLabelClass());

            dayLabelRow.appendChild(dayTd);
        }

        dayLabelTable.appendChild(dayLabelRow);

        Date startDate = calendarModel.getCurrentFirstDayOfFirstWeek();

        for (int i = 0; i < CalendarModel.WEEKS_IN_MONTH; i++) {
            for (int j = 0; j < CalendarModel.DAYS_IN_WEEK; j++) {

                String dateNumber = "" + startDate.getDate();
                final Date thisDate = startDate;
                final DateCell dateCell = new DateCell();

                dateTableElements.put(startDate, dateCell);

                if (currentDate != null
                        && startDate.getMonth() == currentDate.getMonth()
                        && currentDate.getMonth() == displayedMonth.getMonth()
                        && startDate.getDate() == currentDate.getDate()
                        && startDate.getYear() == currentDate.getYear()) {
                    dateCell.setToday(true);
                }

                if (startDate.getMonth() != displayedMonth.getMonth()) {
                    dateCell.setEnabled(false);
                } else if ((minDate != null && CalendarUtil.getDaysBetween(startDate, minDate) > 0) ||
                        (maxDate != null && CalendarUtil.getDaysBetween(startDate, maxDate) < 0)) {
                    dateCell.setEnabled(false);
                } else {

                    if (firstActive != null) {
                        firstActive = dateCell;
                    }

                    if (canSelectDate) {
                        dateCell.addClickHandler(new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent event) {
                                event.preventDefault();
                                setValue(thisDate, true);
                            }
                        });

                        dateCell.addKeyDownHandler(new KeyDownHandler() {
                            @Override
                            public void onKeyDown(KeyDownEvent event) {
                                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                                    event.preventDefault();
                                    setValue(thisDate, true);
                                }
                            }
                        });
                    }
                }

                dateCell.setText(dateNumber);
                monthDayTable.setWidget(i, j, dateCell);
                startDate = CalendarUtil.copyDate(startDate);
                CalendarUtil.addDaysToDate(startDate, 1);
            }

        }


        setValue(getValue());

        if (minDate != null) {

            Date prevMonth = CalendarUtil.copyDate(displayedMonth);
            CalendarUtil.addDaysToDate(prevMonth, -1);
            boolean disablePrevMonth = CalendarUtil.getDaysBetween(prevMonth, minDate) > 0;

            Date prevYear = CalendarUtil.copyDate(displayedMonth);
            CalendarUtil.addYearsToDate(prevYear, -1);

            boolean disablePrevYear = CalendarUtil.getDaysBetween(prevYear, minDate) > 0;

            if (prevMonthButton != null)
                prevMonthButton.setEnabled(!disablePrevMonth);
            if (prevYearButton != null)
                prevYearButton.setEnabled(!disablePrevYear);
        }


        if (maxDate != null) {

            Date nextMonth = CalendarUtil.copyDate(displayedMonth);
            CalendarUtil.addMonthsToDate(nextMonth, 1);
            boolean disableNextMonth = CalendarUtil.getDaysBetween(nextMonth, maxDate) < 0;

            Date nextYear = CalendarUtil.copyDate(displayedMonth);
            CalendarUtil.addYearsToDate(nextYear, 1);
            boolean disableNextYear = CalendarUtil.getDaysBetween(nextYear, maxDate) < 0;

            if (nextMonthButton != null)
                nextMonthButton.setEnabled(!disableNextMonth);
            if (nextYearButton != null)
                nextYearButton.setEnabled(!disableNextYear);
        }

    }

    public CalendarModel getModel() {
        return calendarModel;
    }


    @Override
    public Date getValue() {
        return this.value;
    }

    @Override
    public void setValue(Date value) {

        if (this.value != null) {
            DateCell selected = dateTableElements.get(this.value);
            if (selected != null) {
                selected.setSelected(false);
            }
        }

        this.value = value;

        if (this.value != null) {
            DateCell cell = dateTableElements.get(this.value);

            if (cell != null) {
                cell.setSelected(true);
            }
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
        displayMonth();
    }


    private class DateCell extends Control {
        public DateCell() {
            super(DOM.createDiv());
            getElement().addClassName(appearance.css().dayCellClass());
            addMouseDownHandler(new MouseDownHandler() {
                @Override
                public void onMouseDown(MouseDownEvent event) {
                    event.preventDefault();
                }
            });
        }

        public void setEnabled(boolean enabled) {
            super.setEnabled(enabled);

            if (enabled) {
                getElement().removeClassName(appearance.css().disabledDayCellClass());
                setCanFocus(true);
            } else {
                getElement().addClassName(appearance.css().disabledDayCellClass());
                setCanFocus(false);
            }
        }


        public void setSelected(boolean selected) {
            if (selected) {
                getElement().addClassName(appearance.css().selectedDayCellClass());
            } else {
                getElement().removeClassName(appearance.css().selectedDayCellClass());
            }

        }

        public void setToday(boolean today) {
            if (today) {
                getElement().addClassName(appearance.css().todayDayCellClass());
            } else {
                getElement().removeClassName(appearance.css().todayDayCellClass());
            }
        }

        public void setText(String text) {
            getElement().setInnerText(text);
        }
    }

}
