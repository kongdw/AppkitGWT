package toolbar.client;


import com.appkit.ui.client.layouts.absolute.AbsoluteLayoutPanel;
import com.appkit.ui.client.widgets.input.slider.Slider;
import com.appkit.ui.client.widgets.toolbar.Toolbar;
import com.appkit.ui.client.widgets.toolbar.ToolbarButton;
import com.appkit.ui.client.widgets.toolbar.ToolbarControl;
import com.appkit.ui.client.widgets.toolbar.ToolbarFlexibleSpace;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class Application implements EntryPoint {

    @Override
    public void onModuleLoad() {

        AbsoluteLayoutPanel p = new AbsoluteLayoutPanel();

        Toolbar tb = new Toolbar();

        ToolbarControl control = new ToolbarControl();
        Slider s = new Slider();
        s.setPixelSize(120, 5);
        control.setWidget(s);

        control.setText("Slider");

        tb.addToolbarItem(control);

        tb.addToolbarItem(new ToolbarFlexibleSpace());

        ToolbarButton button1 = new ToolbarButton();
        button1.setText("Zoom In");
        button1.setIcon(Resources.INSTANCE.zoomIn());
        button1.setActiveIcon(Resources.INSTANCE.zoomInActive());

        ToolbarButton button2 = new ToolbarButton();
        button2.setIcon(Resources.INSTANCE.zoomOut());
        button2.setText("Zoom Out");

        button1.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                GWT.log("CLICK!");
            }
        });

        tb.addToolbarItem(button1);
        tb.addToolbarItem(button2);


        p.add(tb);

        RootLayoutPanel.get().add(p);


    }


}
