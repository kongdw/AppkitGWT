package toolbar.client;


import com.appkit.ui.client.widgets.toolbar.Toolbar;
import com.appkit.ui.client.widgets.toolbar.ToolbarButton;
import com.appkit.ui.client.widgets.toolbar.ToolbarFlexibleSpace;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class Application implements EntryPoint {

    @Override
    public void onModuleLoad() {

        Toolbar tb = new Toolbar();

        tb.addToolbarItem(new ToolbarFlexibleSpace());

        ToolbarButton button1 = new ToolbarButton();
        button1.setText("Zoom In");
        button1.setIcon(Resources.INSTANCE.zoomIn());

        ToolbarButton button2 = new ToolbarButton();
        button2.setText("Zoom Out");
        button2.setIcon(Resources.INSTANCE.zoomOut());


        tb.addToolbarItem(button1);
        tb.addToolbarItem(button2);

        tb.addToolbarItem(new ToolbarFlexibleSpace());


        RootLayoutPanel.get().add(tb);


    }


}
