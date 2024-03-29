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

package com.appkit.ui.client.events.touch;

import com.google.gwt.event.dom.client.TouchCancelHandler;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

/**
 * This is a convenience interface that includes all touch handlers defined by
 * appkit.
 */
public interface HasTouchHandlers extends HasHandlers {

    HandlerRegistration addTouchStartHandler(TouchStartHandler handler);

    HandlerRegistration addTouchMoveHandler(TouchMoveHandler handler);

    HandlerRegistration addTouchCancelHandler(TouchCancelHandler handler);

    HandlerRegistration addTouchEndHandler(TouchEndHandler handler);

    HandlerRegistration addTouchHandler(TouchHandler handler);
}
