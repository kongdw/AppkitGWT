/*
 * Copyright 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.appkit.ui.client.widgets.input.date;

import com.google.gwt.core.client.GWT;

import java.util.Date;

/**
 * Useful utilities for creating views of a calendar.
 */
public class CalendarUtil {

    private static int firstDayOfWeekend;
    private static int lastDayOfWeekend;
    private static int startingDay;

    static {
        if (GWT.isClient()) {
            firstDayOfWeekend = 6;
            lastDayOfWeekend = 0;
            startingDay = 0;
        }
    }

    /**
     * Adds the given number of days to a date.
     *
     * @param date the datebox-sizing: border-box;
     *             vertical-align: middle;
     *             display: table-cell;
     *             text-align: left;
     *             padding-left: 4px;
     *             font-size:16px;
     * @param days number of days
     */
    @SuppressWarnings("deprecation") // GWT requires Date
    public static void addDaysToDate(Date date, int days) {
        date.setDate(date.getDate() + days);
    }

    /**
     * Adds the given number of months to a date.
     *
     * @param date   the date
     * @param months number of months
     */
    @SuppressWarnings("deprecation") // GWT requires Date
    public static void addMonthsToDate(Date date, int months) {
        if (months != 0) {
            int month = date.getMonth();
            int year = date.getYear();

            int resultMonthCount = year * 12 + month + months;
            int resultYear = (int) Math.floor(resultMonthCount / 12.0);
            int resultMonth = resultMonthCount - resultYear * 12;

            date.setMonth(resultMonth);
            date.setYear(resultYear);
        }
    }

    /**
     * Adds the given number of years to a date.
     *
     * @param date  the date
     * @param years number of years
     */
    @SuppressWarnings("deprecation") // GWT requires Date
    public static void addYearsToDate(Date date, int years) {
        if (years != 0) {

            int year = date.getYear();
            year += years;
            date.setYear(year);
        }
    }

    /**
     * Copies a date.
     *
     * @param date the date
     * @return the copy
     */
    public static Date copyDate(Date date) {
        if (date == null) {
            return null;
        }
        Date newDate = new Date();
        newDate.setTime(date.getTime());
        return newDate;
    }

    /**
     * Returns the date that occurs first
     *
     * @param date1
     * @param date2
     * @return Date
     */

    public static Date getEarliest(Date date1, Date date2) {
        if (CalendarUtil.getDaysBetween(date1, date2) < 0) {
            return date2;
        }

        return date1;
    }

    /**
     * Returns the date that occurs last
     *
     * @param date1
     * @param date2
     * @return Date
     */

    public static Date getLatest(Date date1, Date date2) {
        if (CalendarUtil.getDaysBetween(date1, date2) > 0) {
            return date2;
        }

        return date1;
    }

    /**
     * Returns the number of days between the two dates. Time is ignored.
     *
     * @param start  starting date
     * @param finish ending date
     * @return the different
     */
    public static int getDaysBetween(Date start, Date finish) {

        if (start == null || finish == null) {
            return 0;
        }

        // Convert the dates to the same time
        start = copyDate(start);
        resetTime(start);
        finish = copyDate(finish);
        resetTime(finish);

        long aTime = start.getTime();
        long bTime = finish.getTime();

        long adjust = 60 * 60 * 1000;
        adjust = (bTime > aTime) ? adjust : -adjust;

        return (int) ((bTime - aTime + adjust) / (24 * 60 * 60 * 1000));
    }


    /**
     * Returns the day of the week on which week starts in the current locale. The
     * range between 0 for Sunday and 6 for Saturday.
     *
     * @return the day of the week
     */
    public static int getStartingDayOfWeek() {
        return startingDay;
    }

    /**
     * Check if two dates represent the same date of the same year, even if they
     * have different times.
     *
     * @param date0 a date
     * @param date1 a second date
     * @return true if the dates are the same
     */
    @SuppressWarnings("deprecation") // GWT requires Date
    public static boolean isSameDate(Date date0, Date date1) {

        if (date0 == null || date1 == null) {
            return false;
        }

        return date0.getYear() == date1.getYear()
                && date0.getMonth() == date1.getMonth()
                && date0.getDate() == date1.getDate();
    }

    /**
     * Sets a date object to be at the beginning of the month and no time
     * specified.
     *
     * @param date the date
     */
    @SuppressWarnings("deprecation") // GWT requires Date
    public static void setToFirstDayOfMonth(Date date) {
        resetTime(date);
        date.setDate(1);
    }

    /**
     * Is a day in the week a weekend?
     *
     * @param dayOfWeek day of week
     * @return is the day of week a weekend?
     */
    static boolean isWeekend(int dayOfWeek) {
        return dayOfWeek == firstDayOfWeekend || dayOfWeek == lastDayOfWeekend;
    }

    /**
     * Resets the date to have no time modifiers. Note that the hour might not be zero if the time
     * hits a DST transition date.
     *
     * @param date the date
     */
    @SuppressWarnings("deprecation") // GWT requires Date
    public static void resetTime(Date date) {
        long msec = resetMilliseconds(date.getTime());
        date.setTime(msec);
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
    }

    private static long resetMilliseconds(long msec) {
        int offset = (int) (msec % 1000);
        // Normalize if time is before epoch
        if (offset < 0) {
            offset += 1000;
        }
        return msec - offset;
    }
}

