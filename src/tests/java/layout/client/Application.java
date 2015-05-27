package layout.client;

import com.appkit.ui.client.layout.accordian.AccordionLayoutPanel;
import com.appkit.ui.client.widgets.button.Button;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class Application implements EntryPoint {

    @Override
    public void onModuleLoad() {

        FlowPanel pane = new FlowPanel();

        final AccordionLayoutPanel layout = new AccordionLayoutPanel();

        layout.add(new HTML("Test"), "Panel 1");
        layout.add(new HTML("Test 2"), "Panel 2");
        layout.add(new HTML("Test 3"), "Panel 3");
        layout.add(new HTML("Test 4"), "Panel 4");

        layout.setPixelSize(300, 300);
        layout.getElement().getStyle().setLeft(25.0, Style.Unit.PX);

        layout.addSelectionHandler(new SelectionHandler<Integer>() {
            @Override
            public void onSelection(SelectionEvent<Integer> event) {
                GWT.log("selected panel index: " + event.getSelectedItem());
            }
        });

        Button b = new Button("Click");
        b.setButtonStyle(Button.ButtonStyle.PRIMARY);
        b.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                layout.removeAtIndex(0);
            }
        });

        b.getElement().getStyle().setMargin(25.0, Style.Unit.PX);
        b.setPixelSize(100, 28);


        pane.add(layout);
        pane.add(b);

        pane.getElement().getStyle().setMargin(15.0, Style.Unit.PX);

        RootLayoutPanel.get().add(pane);


    }
}
