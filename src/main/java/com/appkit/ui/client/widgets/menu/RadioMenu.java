package com.appkit.ui.client.widgets.menu;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.view.client.SelectionChangeEvent;

public class RadioMenu extends Menu implements SelectionChangeEvent.HasSelectionChangedHandlers {

    @Override
    public void addMenuItem(AbstractMenuItem menuItem) {
        if (menuItem instanceof MenuItem) {
            ((MenuItem) menuItem).setCheckable(true);
        }

        super.addMenuItem(menuItem);
    }

    @Override
    public void setLastSelectedItem(MenuItem menuItem) {
        if (getLastSelectedItem() != null) {
            getLastSelectedItem().setChecked(false);
        }

        if (menuItem != null) {
            menuItem.setChecked(true);
            if (!menuItem.equals(getLastSelectedItem())) {
                super.setLastSelectedItem(menuItem);
                SelectionChangeEvent.fire(this);

                return;
            }
        }

        super.setLastSelectedItem(menuItem);

    }

    @Override
    public HandlerRegistration addSelectionChangeHandler(SelectionChangeEvent.Handler handler) {
        return addHandler(handler, SelectionChangeEvent.getType());
    }


}
