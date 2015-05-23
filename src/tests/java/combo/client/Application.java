package combo.client;


import com.appkit.ui.client.widgets.input.combo.ComboBox;
import com.appkit.ui.client.widgets.menu.Menu;
import com.appkit.ui.client.widgets.menu.MenuItem;
import com.appkit.ui.client.widgets.menu.MenuItemSeparator;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.RootPanel;

public class Application implements EntryPoint {

    @Override
    public void onModuleLoad() {

        ComboBox comboBox = new ComboBox();

        Menu menu = new Menu();
        MenuItem item1 = new MenuItem("Cut");
        menu.addMenuItem(item1);

        MenuItem item2 = new MenuItem("Copy");
        menu.addMenuItem(item2);

        menu.addMenuItem(new MenuItemSeparator());

        MenuItem item3 = new MenuItem("Paste");
        menu.addMenuItem(item3);

        comboBox.setMenu(menu);


        comboBox.setSize("140px", "28px");
        comboBox.getElement().getStyle().setMargin(30.0, Style.Unit.PX);

        RootPanel.get().add(comboBox);


    }
}
