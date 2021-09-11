package com.api.print.api;

import java.util.Collection;

public interface Formatter {
    String formatCollection(Collection<?> collection);
    String formatBooleanOperation(boolean bool);
}
