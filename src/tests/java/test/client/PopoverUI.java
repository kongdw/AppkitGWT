package test.client;

import com.appkit.ui.client.widgets.popover.Popover;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;

public class PopoverUI {

    interface PopoverUiBinder extends UiBinder<Popover, PopoverUI> {
    }

    private static PopoverUiBinder ourUiBinder = GWT.create(PopoverUiBinder.class);

    @UiField
    Popover popover;

    public PopoverUI() {
        ourUiBinder.createAndBindUi(this);
    }

    public void show(int x, int y) {
        popover.setPosition(x, y);
        popover.setVisible(true);
    }


}