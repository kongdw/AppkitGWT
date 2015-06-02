package com.appkit.ui.client.layouts.absolute;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;

import java.util.Iterator;


public class AbsoluteLayoutPanel extends AbsolutePanel implements ProvidesResize, RequiresResize {


    @Override
    public void onResize() {

        Iterator<Widget> subviews = this.iterator();

        while (subviews.hasNext()) {
            Widget widget = subviews.next();
            if (widget instanceof RequiresResize) {
                ((RequiresResize) widget).onResize();
            }
        }
    }
}
