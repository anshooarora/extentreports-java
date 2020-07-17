package com.aventstack.extentreports.markuputils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

abstract class HtmlList {
    @SuppressWarnings("unchecked")
    protected String getList(Object object, ListType listType) {
        final StringBuilder sb = new StringBuilder();
        sb.append("<" + listType.toString().toLowerCase() + ">");
        if (object instanceof Map) {
            for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) object).entrySet()) {
                sb.append("<li>");
                sb.append(entry.getKey());
                sb.append(":");
                sb.append(entry.getValue());
                sb.append("</li>");
            }
        } else if (object instanceof List || object instanceof Set || object.getClass().isArray()) {
            if (object.getClass().isArray())
                object = Arrays.asList((Object[])object);
            for (Object o : (Collection<Object>) object) {
                sb.append("<li>");
                sb.append(o.toString());
                sb.append("</li>");
            }
        } else {
            sb.append("<li>");
            sb.append(object.toString());
            sb.append("</li>");
        }
        sb.append("</" + listType.toString().toLowerCase() + ">");
        return sb.toString();
    }
}
