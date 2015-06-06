package com.appkit.ui.client.widgets.toolbar;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.*;

import java.util.Iterator;


public class ToolbarControl extends Composite implements ToolbarItem,
        HasWidgets, HasText, HasEnabled {

    private Widget control = null;
    private SimplePanel panel;
    private Grid grid;
    private Element label;

    public ToolbarControl() {

        panel = new SimplePanel();
        grid = new Grid(1, 1);
        grid.setCellPadding(0);
        grid.setCellSpacing(0);

        panel.getElement().addClassName("appkit-toolbar-item");

        panel.add(grid);

        initWidget(panel);


    }

    public void setWidget(Widget control) {

        if (this.control != null) {
            this.control.removeFromParent();
        }

        this.control = control;

        if (this.control != null) {
            grid.setWidget(0, 0, this.control);

        }
    }

    public Widget getWidget() {
        return control;
    }

    public void setEnabled(boolean enabled) {
        if (!enabled) {
            getElement().addClassName("appkit-toolbar-item-disabled");

        } else {
            getElement().removeClassName("appkit-toolbar-item-disabled");
        }

        if (control instanceof FocusWidget) {
            ((FocusWidget) control).setEnabled(enabled);
        }
    }

    public boolean isEnabled() {
        return !getElement().hasClassName("appkit-toolbar-item-disabled");
    }

    @Override
    public void add(Widget w) {

        if (grid.getWidget(0, 0) != null) {
            throw new IllegalStateException(
                    "ToolbarControl can only contain have one control");
        }

        setWidget(w);

    }

    @Override
    public void clear() {
        grid.clear();
    }

    @Override
    public Iterator<Widget> iterator() {
        return grid.iterator();
    }

    @Override
    public boolean remove(Widget w) {
        return grid.remove(w);
    }

    @Override
    public String getText() {
        if (label != null) {
            label.getInnerText();
        }

        return null;
    }

    @Override
    public void setText(String text) {

        if (text != null) {

            if (label == null) {
                label = DOM.createDiv();
                label.addClassName("appkit-toolbar-item-label");

                panel.getElement().appendChild(label);

            }
        } else {
            if (label != null) {
                label.removeFromParent();
                label = null;
            }
        }

        if (label != null) {
            label.setInnerHTML(text);
        }

    }
}
