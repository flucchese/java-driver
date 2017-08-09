/*
 * Copyright (C) 2017-2017 DataStax Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.datastax.oss.driver.internal.core.metadata.schema;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.metadata.schema.Describable;
import com.google.common.base.Strings;

/**
 * A simple builder that is used internally for the queries of {@link Describable} schema elements.
 */
public class ScriptBuilder {
  private static final int INDENT_SIZE = 4;

  private final boolean pretty;
  private final StringBuilder builder = new StringBuilder();
  private int indent;
  private boolean isAtLineStart;

  public ScriptBuilder(boolean pretty) {
    this.pretty = pretty;
  }

  public ScriptBuilder append(String s) {
    if (pretty && isAtLineStart && indent > 0) {
      builder.append(Strings.repeat(" ", indent * INDENT_SIZE));
    }
    isAtLineStart = false;
    builder.append(s);
    return this;
  }

  public ScriptBuilder append(CqlIdentifier id) {
    append(id.asCql(pretty));
    return this;
  }

  public ScriptBuilder newLine() {
    if (pretty) {
      builder.append('\n');
    } else {
      builder.append(' ');
    }
    isAtLineStart = true;
    return this;
  }

  public ScriptBuilder increaseIndent() {
    indent += 1;
    return this;
  }

  public ScriptBuilder decreaseIndent() {
    if (indent > 0) {
      indent -= 1;
    }
    return this;
  }

  public String build() {
    return builder.toString();
  }
}
