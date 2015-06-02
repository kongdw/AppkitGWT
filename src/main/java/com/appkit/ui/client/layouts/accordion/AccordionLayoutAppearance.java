package com.appkit.ui.client.layouts.accordion;


import com.google.gwt.resources.client.CssResource;

public interface AccordionLayoutAppearance {

    public interface AccordionLayoutCss extends CssResource {

        @ClassName("appkit-AccordionLayout-expandBtn")
        String expandButtonClass();

        @ClassName("appkit-AccordionLayout-collapse")
        String collapseButtonClass();


    }

    AccordionLayoutCss css();

}
