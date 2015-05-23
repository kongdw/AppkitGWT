/*
 * Copyright 2010 Daniel Kurka
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.appkit.ui.client.widgets.touch;

import com.appkit.ui.client.events.mouse.HandlerRegistrationCollection;
import com.appkit.ui.client.events.recognizer.GestureUtility;
import com.appkit.ui.client.events.recognizer.longtap.HasLongTapHandlers;
import com.appkit.ui.client.events.recognizer.longtap.LongTapEvent;
import com.appkit.ui.client.events.recognizer.longtap.LongTapHandler;
import com.appkit.ui.client.events.recognizer.pinch.HasPinchHandlers;
import com.appkit.ui.client.events.recognizer.pinch.PinchEvent;
import com.appkit.ui.client.events.recognizer.pinch.PinchHandler;
import com.appkit.ui.client.events.recognizer.swipe.*;
import com.appkit.ui.client.events.tap.HasTapHandlers;
import com.appkit.ui.client.events.tap.TapEvent;
import com.appkit.ui.client.events.tap.TapHandler;
import com.appkit.ui.client.events.touch.HasTouchHandlers;
import com.appkit.ui.client.events.touch.TouchHandler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FocusWidget;

/**
 * Base class for all widgets that support touch events Childclasses are
 * responsible for setting the dom element
 *
 * @author Daniel Kurka
 */

public abstract class TouchFocusWidget extends FocusWidget implements HasTouchHandlers, HasTapHandlers, HasSwipeHandlers, HasPinchHandlers, HasLongTapHandlers {

    protected final GestureUtility gestureUtility;

    public TouchFocusWidget(Element element) {
        super(element);

        gestureUtility = new GestureUtility(this);
    }

    @Override
    public HandlerRegistration addTouchHandler(TouchHandler handler) {
        HandlerRegistrationCollection handlerRegistrationCollection = new HandlerRegistrationCollection();

        handlerRegistrationCollection.addHandlerRegistration(addTouchCancelHandler(handler));
        handlerRegistrationCollection.addHandlerRegistration(addTouchStartHandler(handler));
        handlerRegistrationCollection.addHandlerRegistration(addTouchEndHandler(handler));
        handlerRegistrationCollection.addHandlerRegistration(addTouchMoveHandler(handler));
        return handlerRegistrationCollection;
    }

    @Override
    public HandlerRegistration addTapHandler(TapHandler handler) {
        gestureUtility.ensureTapRecognizer();
        return addHandler(handler, TapEvent.getType());
    }

    @Override
    public HandlerRegistration addSwipeStartHandler(SwipeStartHandler handler) {
        gestureUtility.ensureSwipeRecognizer();
        return addHandler(handler, SwipeStartEvent.getType());
    }

    @Override
    public HandlerRegistration addSwipeMoveHandler(SwipeMoveHandler handler) {
        gestureUtility.ensureSwipeRecognizer();
        return addHandler(handler, SwipeMoveEvent.getType());
    }

    @Override
    public HandlerRegistration addSwipeEndHandler(SwipeEndHandler handler) {
        gestureUtility.ensureSwipeRecognizer();
        return addHandler(handler, SwipeEndEvent.getType());
    }

    @Override
    public HandlerRegistration addPinchHandler(PinchHandler handler) {
        gestureUtility.ensurePinchRecognizer(this);
        return addHandler(handler, PinchEvent.getType());
    }

    @Override
    public HandlerRegistration addLongTapHandler(LongTapHandler handler) {
        gestureUtility.ensureLongTapHandler();
        return addHandler(handler, LongTapEvent.getType());
    }


}
