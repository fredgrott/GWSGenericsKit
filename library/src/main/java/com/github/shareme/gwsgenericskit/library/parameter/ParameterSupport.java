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
package com.github.shareme.gwsgenericskit.library.parameter;

import java.util.List;
import java.util.Map;

/**
 * Original code author is TGardner:
 * https://blogs.oracle.com/terrygardner/entry/a_typesafe_heterogeneous_container_for
 *
 * Super Type Tokens has a limitation as the dynamic type can be different in a
 * variable so we must have some way to prevent that.
 *
 * <code>
 *     List<Parameter> parameters = Util.newList();
 *     ParameterSupport ldapServerHostnameParameter =
 *     ParameterSupport.
 *         getParameterSupport(String.class,
 *                   getLdapServerHostname(),
 *           new ParameterValidator() {
 *                public Boolean validate(Parameter parameter) {
 *                         Boolean result;
 *                  String hostname = parameter.getParameterValue(String.class);
 *                  if(hostname == null || hostname.length() == 0) {
 *           result = false;
 *             } else {
 *               try {
 *                    InetAddress inetAddress = InetAddress.getByName(hostname);
 *          result = true;
 *          } catch(UnknownHostException unknownHost) {
 *        result = false;
 *           }
 *        }
 *    return result;
 *      }
 * });
 *
 * parameters.add(ldapServerHostnameParameter);
 * The the client calls objectWithParameterListValidator.validateParameters(parameters);
 * </code>
 *
 * Created by fgrott on 1/31/2016.
 */
@SuppressWarnings("unused")
public final class ParameterSupport implements Parameter{

    private Map<Class<?>,Object> parameterMap = Util.newHashMap();

    private ParameterValidator parameterValidator;

    private String name;

    public static <T> ParameterSupport
    getParameterSupport(Class<T> type,
                        T value,
                        ParameterValidator parameterValidator) {
        ParameterSupport ps = new ParameterSupport();
        ps.setParameterValue(type,value);
        ps.setParameterValidator(parameterValidator);
        return ps;
    }



    @Override
    public String getParameterName() {
        return this.name;
    }

    @Override
    public <T> T getParameterValue(Class<T> type) {
        return type.cast(parameterMap.get(type));
    }

    @Override
    public <T> void setParameterValue(Class<T> type, T value) {

        if(type == null) {
            throw new NullPointerException("type cannot be null");
        }
        parameterMap.put(type,value);
    }

    @Override
    public ParameterValidator getParameterValidator() {
        return this.parameterValidator;
    }

    public void setParameterValidator(ParameterValidator parameterValidator) {
        this.parameterValidator = parameterValidator;
    }

    public Boolean validateParameters(List<Parameter> parameters)
    {
        Boolean result;
        for(Parameter parameter : parameters)
        {
            //if(logger.isDebugEnabled())
            //{
              //  logger.debug("validating parameter: " + parameter.getParameterName());
            //}
            ParameterValidator pv = parameter.getParameterValidator();
            pv.validate(parameter);
        }
        return true;
    }

}
