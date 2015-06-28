package menubar.client;

import com.appkit.ui.client.widgets.menu.Menu;
import com.appkit.ui.client.widgets.menu.MenuBar;
import com.appkit.ui.client.widgets.menu.MenuBarItem;
import com.appkit.ui.client.widgets.menu.MenuItem;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class Application implements EntryPoint {

    @Override
    public void onModuleLoad() {

        MenuBar menuBar = new MenuBar();

        MenuBarItem file = new MenuBarItem("File");

        Menu fileMenu = new Menu();

        MenuItem newItem = new MenuItem("New...");
        fileMenu.addMenuItem(newItem);

        MenuItem openItem = new MenuItem("Open..");
        fileMenu.addMenuItem(openItem);


        file.setMenu(fileMenu);

        menuBar.addMenuItem(file);

        MenuBarItem edit = new MenuBarItem("Edit");

        Menu editMenu = new Menu();

        MenuItem undoItem = new MenuItem("Undo");
        editMenu.addMenuItem(undoItem);

        MenuItem redoItem = new MenuItem("Redo");
        editMenu.addMenuItem(redoItem);

        edit.setMenu(editMenu);

        menuBar.addMenuItem(edit);

        RootLayoutPanel.get().add(menuBar);


    }
}
