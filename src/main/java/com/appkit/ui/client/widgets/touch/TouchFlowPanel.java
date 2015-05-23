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
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * A simple panel that supports {@link TouchEvent}
 *
 * @author Daniel Kurka
 */
public class TouchFlowPanel extends FlowPanel implements HasTouchHandlers, HasTapHandlers, HasPinchHandlers, HasSwipeHandlers, HasLongTapHandlers {

    protected final GestureUtility gestureUtility;

    public TouchFlowPanel() {
        super();
        gestureUtility = new GestureUtility(this);
    }

    @Override
    public HandlerRegistration addTouchStartHandler(TouchStartHandler handler) {
        return addHandler(handler, TouchStartEvent.getType());
    }

    @Override
    public HandlerRegistration addTouchMoveHandler(TouchMoveHandler handler) {
        return addHandler(handler, TouchMoveEvent.getType());
    }

    @Override
    public HandlerRegistration addTouchCancelHandler(TouchCancelHandler handler) {
        return addHandler(handler, TouchCancelEvent.getType());
    }

    @Override
    public HandlerRegistration addTouchEndHandler(TouchEndHandler handler) {
        return addHandler(handler, TouchEndEvent.getType());
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
