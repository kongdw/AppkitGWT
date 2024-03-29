/*
 * Copyright 2010 Daniel Kurka
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.appkit.collection.client;

import com.appkit.collection.shared.LightArray;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * This is a the js implementation of {@link LightArray}, which makes direct use of native
 * js arrays
 *
 * @param <T> the Type of the object to put into the array
 * @author Daniel Kurka
 */
public class JsLightArray<T> implements LightArray<T> {

    private JavaScriptObject array;

    /**
     * Construct a {@link JsLightArray}
     */
    public JsLightArray() {
        this(JavaScriptObject.createArray());
    }

    /**
     * Construct a {@link JsLightArray} with a given js array
     *
     * @param array the array to use
     */
    public JsLightArray(JavaScriptObject array) {
        this.array = array;
    }

    @Override
    public native T get(int index) /*-{
        return this.@com.appkit.collection.client.JsLightArray::array[index];
    }-*/;

    /**
     * returns the underlying js array
     *
     * @return the js array
     */
    public JavaScriptObject getArray() {
        return array;
    }

    @Override
    public native int length()/*-{
        return this.@com.appkit.collection.client.JsLightArray::array.length;
    }-*/;

    @Override
    public native void push(T value)/*-{
        this.@com.appkit.collection.client.JsLightArray::array[this.@com.appkit.collection.client.JsLightArray::array.length] = value;
    }-*/;

    @Override
    public native void set(int index, T value) /*-{
        this.@com.appkit.collection.client.JsLightArray::array[index] = value;
    }-*/;

    @Override
    public native void remove(int index) /*-{
        if (index > -1 && index < this.@com.appkit.collection.client.JsLightArray::array.length) {
            this.@com.appkit.collection.client.JsLightArray::array.splice(index, 1);
        }
    }-*/;

    @Override
    public native int indexOf(T value) /*-{
        return this.@com.appkit.collection.client.JsLightArray::array.indexOf(value);
    }-*/;

    @Override
    public native T shift() /*-{
        return this.@com.appkit.collection.client.JsLightArray::array.shift();
    }-*/;

    @Override
    public native void unshift(T value)/*-{
        this.@com.appkit.collection.client.JsLightArray::array.unshift(value);
    }-*/;

}
