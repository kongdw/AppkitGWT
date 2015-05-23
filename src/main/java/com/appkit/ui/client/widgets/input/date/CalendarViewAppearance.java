package com.appkit.ui.client.widgets.input.date;

import com.google.gwt.resources.client.CssResource;


public interface CalendarViewAppearance {

    public interface CalendarViewCss extends CssResource {

        @ClassName("appkit-Calendar")
        String calendarClass();

        @ClassName("appkit-Calendar-MonthDayTable")
        String monthDayTableClass();

        @ClassName("appkit-Calendar-MonthLabel")
        String monthLabelClass();

        @ClassName("appkit-Calendar-DayCell")
        String dayCellClass();

        @ClassName("appkit-Calendar-DayLabel")
        String dayLabelClass();

        @ClassName("appkit-Calendar-TodayDayCell")
        String todayDayCellClass();

        @ClassName("appkit-Calendar-DisabledDayCell")
        String disabledDayCellClass();

        @ClassName("appkit-Calendar-SelectedDayCell")
        String selectedDayCellClass();

        @ClassName("appkit-Calendar-PrevMonthBtn")
        String prevMonthBtnClass();

        @ClassName("appkit-Calendar-NextMonthBtn")
        String nextMonthBtnClass();

        @ClassName("appkit-Calendar-PrevYearBtn")
        String prevYearBtnClass();

        @ClassName("appkit-Calendar-NextYearBtn")
        String nextYearBtnClass();
    }

    CalendarViewCss css();
}
