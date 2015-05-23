package com.appkit.ui.client.widgets.windowpanel;

import com.google.gwt.resources.client.CssResource;

public interface WindowPanelAppearance {

    public interface WindowPanelCss extends CssResource {

        @ClassName("appkit-Window-panel")
        String windowPanelClass();

        @ClassName("appkit-Window-panel-title")
        String windowPanelTitleClass();

        @ClassName("draggable")
        String windowPanelDraggableClass();

        @ClassName("appkit-Window-panel-content")
        String windowPanelContentClass();

        @ClassName("appkit-Window-panel-close")
        String windowPanelCloseButtonClass();

        @ClassName("appkit-Window-panel-max")
        String windowPanelMaximizeButtonClass();

        @ClassName("appkit-Window-panel-min")
        String windowPanelMinimizeButtonClass();

        @ClassName("appkit-Window-panel-restore")
        String windowPanelRestoreButtonClass();

        @ClassName("appkit-Window-panel-button-pane")
        String windowPanelButtonPaneClass();

        @ClassName("appkit-Window-panel-title-text")
        String windowPanelTitleTextClass();

        @ClassName("appkit-state-active")
        String windowPanelActiveButtonClass();

        @ClassName("appkit-state-hover")
        String windowPanelHoverButtonClass();

        @ClassName("appkit-Window-panel-overlay")
        String windowPanelOverlayClass();
    }

    WindowPanelCss css();

}
