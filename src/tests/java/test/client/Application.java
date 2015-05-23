package test.client;

import com.appkit.ui.client.widgets.input.date.DatePicker;
import com.appkit.ui.client.widgets.input.text.TextField;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class Application implements EntryPoint {

    private CustomDialogController customDialogController = new CustomDialogController();
    private MainWindow mainWindow = new MainWindow();

    private PopoverUI popoverUI = new PopoverUI();


    public void onModuleLoad() {




       // RootLayoutPanel.get().add(mainWindow);

    }


}
