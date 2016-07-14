/*
  Copyright (C) 2016 Fred Grott(aka shareme GrottWorkShop)

Licensed under the Apache License, Version 2.0 (the "License"); you
may not use this file except in compliance with the License. You may
obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
either express or implied. See the License for the specific language
governing permissions and limitations under License.
 */
package com.github.shareme.gwsgenericskit.library.typeref;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Borrowed from EffectiveJava, Reloaded..idea is from Neal Gafter
 *
 * Used for Strings and Integer Generics as they have the same object signature
 * due to type erasure.
 *
 * Joshua Bloch calls it a Typesafe Heterogeneous Container with Super Type Tokens
 *
 *
 * Created by fgrott on 12/21/2015.
 */
@SuppressWarnings("unused")
public abstract class TypeRef<T> {

    private final Type type;

    protected TypeRef(){
        ParameterizedType superclass = (ParameterizedType)getClass().getGenericSuperclass();
        type = superclass.getActualTypeArguments()[0];
    }

    @Override
    public boolean equals (Object o){
        return o instanceof  TypeRef && ((TypeRef)o).type.equals(type);
    }

    @Override
    public int hashCode(){
        return type.hashCode();
    }
}
