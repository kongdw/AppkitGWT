package com.appkit.ui.client.widgets.input.text;


public interface SearchFieldAppearance extends TextControlAppearance {

    public interface SearchFieldCss extends TextControlCss {

        @ClassName("appkit-SearchField")
        String searchFieldClass();

        @ClassName("appkit-search-icon")
        String searchIconClass();

        @ClassName("appkit-search-cancel-icon")
        String searchCancelIconClass();

        @ClassName("appkit-search-cancel-active")
        String searchCancelActiveClass();


    }

    @Override
    SearchFieldCss css();

}
