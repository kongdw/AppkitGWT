package progressbar.client;


import com.appkit.ui.client.widgets.progressbar.ProgressBar;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;

public class Application implements EntryPoint {

    @Override
    public void onModuleLoad() {

        final ProgressBar progressBar = new ProgressBar();

        progressBar.getElement().getStyle().setWidth(150.0, Style.Unit.PX);
        progressBar.getElement().getStyle().setMargin(25.0, Style.Unit.PX);

        RootPanel.get().add(progressBar);

        Timer timer = new Timer() {
            @Override
            public void run() {
                double v = progressBar.getValue();
                progressBar.setValue(v + 1.0);
            }
        };

        timer.scheduleRepeating(50);
    }
}
