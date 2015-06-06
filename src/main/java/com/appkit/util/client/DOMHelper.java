package com.appkit.util.client;

import com.appkit.collection.client.JsLightArray;
import com.appkit.js.client.JavascriptLibraries;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class DOMHelper {

    static {
        JavascriptLibraries.INSTANCE.useJquery();
    }

    public static final JsLightArray<Element> getTabbables(Element el) {
        JavaScriptObject array = nativeGetTabbables(el);

        return new JsLightArray<Element>(array);
    }

    private static final native JavaScriptObject nativeGetTabbables(Element el)
    /*-{
        return $wnd.jQuery(el).find(":tabbable").toArray();

    }-*/;

    public static final boolean isMouseOverElement(MouseEvent event, Element el) {

        int x = event.getClientX();
        int y = event.getClientY();

        int top = el.getAbsoluteTop();
        int left = el.getAbsoluteLeft();
        int w = el.getOffsetWidth();
        int h = el.getOffsetHeight();

        return (x >= left && x <= left + w && y >= top && y <= top + h);
    }


    /**
     * Return the size of one <code>em</code> in the given DOM element.
     *
     * @param el The DOM element.
     * @return The size of one <code>em</code> in this DOM element.
     */
    public static final native double getEmSize(Element el) /*-{

        return Number(
            @com.appkit.util.client.DOMHelper::getComputedStyle(Lcom/google/gwt/dom/client/Element;)(el)
                .fontSize.match(/(\d+)px/)[1]);
    }-*/;


    /**
     * Return the computed metrics of the given DOM element.
     *
     * @param _elem The element to measure.
     * @return The effective style of the element.
     */
    public static final native Style getComputedStyle(Element _elem) /*-{

        if (_elem.currentStyle !== undefined) {
            return _elem.currentStyle;
        }
        else {
            return document.defaultView.getComputedStyle(_elem, null);
        }
    }-*/;

    /**
     * Convert a string representation of a length given in 'px'
     * or 'em' to a pixel size.
     *
     * @param el              The element used to convert 'em' units.
     * @param length          The length string ending with 'px' or 'em'.
     * @param applicableStyle Any style to use for determining a length of 'auto'.
     *                        For border widths/heights this is the border style, for other
     *                        widths/height, this should be <code>null</code>.
     * @return The length in pixels.
     */
    public static final double getPxSize(Element el, String length, String applicableStyle) {

        if (length == null || length.length() == 0) return 0.0;

        if (length.endsWith("px")) {
            return Double.parseDouble(length.substring(0, length.length() - 2));
        } else if (length.endsWith("em")) {
            return Double.parseDouble(length.substring(0, length.length() - 2)) * getEmSize(el);
        }
        // only MSIE return the constants below in eleemnt.currentStyle
        // And yes, the pixel values are different from 1,3,5 in other browser.
        else if ("none".equals(applicableStyle)) {
            return 0;
        } else if ("thin".equals(length)) {
            return 2;
        } else if ("medium".equals(length)) {
            return 4;
        } else if ("thick".equals(length)) {
            return 6;
        } else if ("auto".equals(length)) {

            if (applicableStyle == null)
                return 0;
            else
                return 4;
        } else
            throw new IllegalArgumentException("getPxSize() is unimplemented for units other than 'px' and 'em' (argument was [" + length + "]).");
    }

    /**
     * Set the width of a widget, so that the widget has a resulting
     * offset width equal to the given width.
     *
     * @param w     The widget to set the width of.
     * @param width The new offset width.
     */
    public static void setOffsetWidth(Widget w, double width) {

        if (width >= 0) {
            Element el = w.getElement();
            Style style = getComputedStyle(el);

            double delta =
                    getPxSize(el, style.getProperty("borderLeftWidth"), style.getBorderStyle()) +
                            getPxSize(el, style.getProperty("borderRightWidth"), style.getBorderStyle()) +
                            getPxSize(el, style.getMarginLeft(), null) +
                            getPxSize(el, style.getMarginRight(), null) +
                            getPxSize(el, style.getPaddingLeft(), null) +
                            getPxSize(el, style.getPaddingRight(), null);

            w.setWidth((width - delta) + "px");
        }

    }

    /**
     * Set the height of a widget, so that the widget has a resulting
     * offset height equal to the given height.
     *
     * @param w      The widget to set the height of.
     * @param height The new offset height.
     */
    public static void setOffsetHeight(Widget w, double height) {

        if (height >= 0) {
            Element el = w.getElement();
            Style style = getComputedStyle(el);

            double delta =
                    getPxSize(el, style.getProperty("borderTopWidth"), style.getBorderStyle()) +
                            getPxSize(el, style.getProperty("borderBottomWidth"), style.getBorderStyle()) +
                            getPxSize(el, style.getMarginTop(), null) +
                            getPxSize(el, style.getMarginBottom(), null) +
                            getPxSize(el, style.getPaddingTop(), null) +
                            getPxSize(el, style.getPaddingBottom(), null);

            w.setHeight((height - delta) + "px");
        }

    }

    public static void setTop(Widget w, double top) {
        if (w != null) {
            w.getElement().getStyle().setTop(top, Style.Unit.PX);
        }
    }


    public static void setLeft(Widget w, double left) {
        if (w != null) {
            w.getElement().getStyle().setLeft(left, Style.Unit.PX);
        }
    }


    public static native Element activeElement() /*-{
        return $wnd.document.activeElement;
    }-*/;


    public static void tabOrderWidgets(HasWidgets panel) {
        Iterator<Widget> it = panel.iterator();

        ArrayList<Widget> widgets = new ArrayList<Widget>();

        while (it.hasNext()) {
            widgets.add(it.next());
        }

        Collections.sort(widgets, new Comparator<Widget>() {
            @Override
            public int compare(Widget o1, Widget o2) {
                double dist1 = Math.sqrt(Math.pow(o1.getAbsoluteLeft(), 2) + Math.pow(o1.getAbsoluteTop(), 2));
                double dist2 = Math.sqrt(Math.pow(o2.getAbsoluteLeft(), 2) + Math.pow(o2.getAbsoluteTop(), 2));

                return (int) (dist1 - dist2);
            }
        });

        int count = widgets.size(),
                i = 0;

        for (; i < count; i++) {
            panel.add(widgets.get(i));
        }

    }

}
