package windowpanel.client;


import com.appkit.ui.client.widgets.button.Button;
import com.appkit.ui.client.widgets.windowpanel.WindowPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootPanel;

public class Application implements EntryPoint {

    @Override
    public void onModuleLoad() {

        final WindowPanel windowPanel = new WindowPanel();

        windowPanel.setFrameSize(600, 400);
        windowPanel.setResizable(true);
        windowPanel.setMaximizable(true);
        windowPanel.setMinimizable(true);
        windowPanel.setVisible(false);
        windowPanel.setTitle("Demo Window Demo Window Demo Window Demo Window Demo Window");

        // windowPanel.setModal(true);

        Button m = new Button("Button");
        m.setButtonStyle(Button.ButtonStyle.PRIMARY);
        m.getElement().getStyle().setPosition(Style.Position.ABSOLUTE);
        m.getElement().getStyle().setRight(15.0, Style.Unit.PX);
        m.getElement().getStyle().setBottom(15.0, Style.Unit.PX);
        m.setSize("100px", "28px");

        windowPanel.add(m);

        Button show = new Button("Show Window");

        show.getElement().getStyle().setMargin(30.0, Style.Unit.PX);
        show.getElement().getStyle().setWidth(120, Style.Unit.PX);

        show.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                windowPanel.center();
                windowPanel.open();
            }
        });

        RootPanel.get().add(show);
    }
}
