package com.appkit.ui.client.layout.flow;

import com.appkit.ui.client.widgets.touch.TouchFlowPanel;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;

import java.util.Iterator;


public class FlowLayoutPanel extends TouchFlowPanel implements ProvidesResize, RequiresResize {
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
