package tabpanel.client;

import com.appkit.ui.client.layout.absolute.AbsoluteLayoutPanel;
import com.appkit.ui.client.layout.tabpanel.TabItem;
import com.appkit.ui.client.layout.tabpanel.TabPanel;
import com.appkit.ui.client.widgets.button.Button;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.RootPanel;

public class Application implements EntryPoint {

    @Override
    public void onModuleLoad() {

        TabPanel tabPanel = new TabPanel();

        AbsoluteLayoutPanel btnPanel = new AbsoluteLayoutPanel();
        Button aButton = new Button("Click Me");
        aButton.setButtonStyle(Button.ButtonStyle.PRIMARY);

        aButton.getElement().getStyle().setMargin(15, Style.Unit.PX);

        btnPanel.add(aButton);

        TabItem item1 = new TabItem(btnPanel, "Item 1");
        tabPanel.addTabItem(item1);

        for (int i = 2; i < 10; i++) {
            TabItem item2 = new TabItem("Item " + i);
            tabPanel.addTabItem(item2);
        }


        tabPanel.setSize("400px", "300px");
        tabPanel.getElement().getStyle().setMargin(50.0, Style.Unit.PX);


        RootPanel.get().add(tabPanel);


        TabPanel tabPanel2 = new TabPanel();
        tabPanel2.setTabsPosition(TabPanel.TabPosition.BOTTOM);

        AbsoluteLayoutPanel btnPanel2 = new AbsoluteLayoutPanel();
        Button aButton2 = new Button("Click Me");
        aButton2.setButtonStyle(Button.ButtonStyle.PRIMARY);

        aButton2.getElement().getStyle().setMargin(15, Style.Unit.PX);

        btnPanel2.add(aButton2);

        TabItem item12 = new TabItem(btnPanel2, "Item 1");
        tabPanel2.addTabItem(item12);

        TabItem item22 = new TabItem("Item 2");
        tabPanel2.addTabItem(item22);


        tabPanel2.setSize("400px", "300px");
        tabPanel2.getElement().getStyle().setMargin(50.0, Style.Unit.PX);

        RootPanel.get().add(tabPanel2);

    }
}