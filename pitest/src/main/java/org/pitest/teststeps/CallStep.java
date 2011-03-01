/*
 * Copyright 2010 Henry Coles
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */
package org.pitest.teststeps;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.pitest.Description;
import org.pitest.TestExecutionException;
import org.pitest.TestMethod;
import org.pitest.extension.TestStep;
import org.pitest.functional.Option;
import org.pitest.internal.IsolationUtils;

/**
 * @author henry
 * 
 */
public class CallStep implements TestStep {

  private final TestMethod m;

  public CallStep(final TestMethod m) {
    this.m = m;
  }

  public Object execute(final ClassLoader loader,
      final Description testDescription, final Object target) {
    try {

      final Method m2 = IsolationUtils.convertForClassLoader(loader,
          this.m.getMethod());

      if (!m2.isAccessible()) {
        m2.setAccessible(true);
      }
      m2.invoke(target);

      return target;
    } catch (final InvocationTargetException t) {
      throw new TestExecutionException(t.getCause());
    } catch (final Exception e) {
      throw new TestExecutionException(e);
    }
  }

  public Option<TestMethod> method() {
    return Option.some(this.m);
  }

}