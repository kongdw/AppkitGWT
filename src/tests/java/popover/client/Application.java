package popover.client;


import com.appkit.ui.client.widgets.button.Button;
import com.appkit.ui.client.widgets.popover.Popover;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class Application implements EntryPoint {

    @Override
    public void onModuleLoad() {

        final Popover popover = new Popover();

        popover.setTransient(true);
        popover.add(new HTML("Hello, World"));

        popover.setArrowDirection(Popover.PopoverArrowDirection.RIGHT);

        final Button popoverBtn = new Button("Show Popover");
        popoverBtn.setButtonStyle(Button.ButtonStyle.PRIMARY);
        popoverBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                popover.showAndPointToPosition(
                        popoverBtn.getAbsoluteLeft(),
                        popoverBtn.getAbsoluteTop() + popoverBtn.getOffsetHeight() / 2
                );

            }
        });

        popoverBtn.getElement().getStyle().setPosition(Style.Position.ABSOLUTE);
        popoverBtn.getElement().getStyle().setLeft(300.0, Style.Unit.PX);
        popoverBtn.getElement().getStyle().setTop(300.0, Style.Unit.PX);
        popoverBtn.setPixelSize(80, 44);


        RootPanel.get().add(popoverBtn);


    }
}
