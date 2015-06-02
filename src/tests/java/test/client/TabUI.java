package test.client;

import com.appkit.ui.client.layouts.tabpanel.TabPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ResizeComposite;

/**
 * Created by cbruno on 3/20/15.
 */
public class TabUI extends ResizeComposite {
    interface TabUIUiBinder extends UiBinder<TabPanel, TabUI> {
    }

    private static TabUIUiBinder ourUiBinder = GWT.create(TabUIUiBinder.class);

    @UiField
    TabPanel tabPanel;

    public TabUI() {

        initWidget(ourUiBinder.createAndBindUi(this));


    }
}