/*
 * Copyright 2012 Daniel Kurka
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
package com.appkit.ui.client.events.recognizer.swipe;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * A widget that implements this interface sources {@link SwipeStartEvent}s,
 * {@link SwipeMoveEvent}s and {@link SwipeEndEvent}s
 *
 * @author Daniel Kurka
 */
public interface HasSwipeHandlers {
    /**
     * register for a {@link SwipeStartEvent}
     *
     * @param handler the handler to register
     * @return the {@link HandlerRegistration}
     */
    HandlerRegistration addSwipeStartHandler(SwipeStartHandler handler);

    /**
     * register for a {@link SwipeMoveHandler}
     *
     * @param handler the handler to register
     * @return the {@link HandlerRegistration}
     */
    HandlerRegistration addSwipeMoveHandler(SwipeMoveHandler handler);

    /**
     * register for a {@link SwipeEndHandler}
     *
     * @param handler the handler to register
     * @return the {@link HandlerRegistration}
     */
    HandlerRegistration addSwipeEndHandler(SwipeEndHandler handler);
}
