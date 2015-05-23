package com.appkit.ui.client.widgets.toolbar;


import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.ui.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class Toolbar extends Composite implements RequiresResize, HasWidgets {

    private FlowPanel panel;

    private ToolbarAppearance appearance = null;
    static ToolbarAppearance DEFAULT_APPEARANCE = GWT.create(DefaultToolbarAppearance.class);

    public Toolbar(ToolbarAppearance appearance) {
        this.appearance = appearance;
        render();

    }

    public Toolbar() {
        this(DEFAULT_APPEARANCE);
    }

    private void render() {
        panel = new FlowPanel();
        panel.setStyleName(appearance.css().toolbarClass());

        initWidget(panel);

    }

    public void addToolbarItem(ToolbarItem item) {
        panel.add(item.asWidget());
    }

    @Override
    public void add(Widget w) {
        if (w instanceof ToolbarItem) {
            addToolbarItem((ToolbarItem) w);
        }
    }

    @Override
    public void clear() {
        panel.clear();
    }

    @Override
    public Iterator<Widget> iterator() {
        return panel.iterator();
    }

    @Override
    public boolean remove(Widget w) {
        return panel.remove(w);
    }

    @Override
    public void onResize() {

        Iterator<Widget> it = iterator();

        double totW = getOffsetWidth();
        double itemTotW = 0;
        int flexSpaceCount = 0;

        Set<ToolbarFlexibleSpace> flexSpaceSet = new HashSet<ToolbarFlexibleSpace>();

        while (it.hasNext()) {
            Widget w = it.next();

            if (w instanceof ToolbarFlexibleSpace) {
                flexSpaceCount++;
                flexSpaceSet.add((ToolbarFlexibleSpace) w);
            } else {
                itemTotW += (w.getOffsetWidth() + 8);
            }
        }

        double flexWidth = (totW - itemTotW - 10) / flexSpaceCount;

        Iterator<ToolbarFlexibleSpace> it2 = flexSpaceSet.iterator();

        while (it2.hasNext()) {
            ToolbarFlexibleSpace flex = it2.next();
            flex.setWidth(flexWidth);
        }
    }

    @Override
    public void onLoad() {
        Scheduler.ScheduledCommand command = new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                onResize();
            }
        };

        Scheduler.get().scheduleDeferred(command);

    }
}
