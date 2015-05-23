package test.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class Application implements EntryPoint {

    private CustomDialogController customDialogController = new CustomDialogController();
    private MainWindow mainWindow = new MainWindow();

    private PopoverUI popoverUI = new PopoverUI();


    public void onModuleLoad() {


        customDialogController.showWindow();


        RootLayoutPanel.get().add(mainWindow);

    }


}
