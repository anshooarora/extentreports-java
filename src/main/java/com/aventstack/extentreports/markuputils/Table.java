package com.aventstack.extentreports.markuputils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.aventstack.extentreports.annotations.MarkupIgnore;
import com.aventstack.extentreports.markuputils.util.ReflectionUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
class Table implements Markup {
    private static final long serialVersionUID = 7780527162407673294L;

    private String[][] data;
    private Object object;
    @Builder.Default
    private String cssClass = "";
    private String[] cssClasses;

    @Override
    public String getMarkup() {
        if (cssClasses != null && cssClasses.length > 0)
            Arrays.asList(cssClasses).forEach(x -> cssClass += " " + x);
        if (data != null)
            return fromData();
        try {
            return fromObject();
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            return "";
        }
    }

    private String fromData() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table class='markup-table table " + cssClass + "'>");
        for (int row = 0; row < data.length; row++) {
            sb.append("<tr>");
            for (int col = 0; col < data[row].length; col++) {
                sb.append("<td>" + data[row][col] + "</td>");
            }
            sb.append("</tr>");
        }
        sb.append("</table>");
        return sb.toString();
    }

    private String fromObject() throws IllegalArgumentException, IllegalAccessException {
        if (object == null)
            return "";
        final List<Field> fieldList = ReflectionUtil.getFieldsIgnoringAnnotation(object.getClass(), MarkupIgnore.class);
        final StringBuilder sb = new StringBuilder();
        final StringBuilder sb2 = new StringBuilder();
        sb.append("<table class='markup-table table " + cssClass + "'>");
        sb.append("<tbody>");
        final List<String> list = Stream.of("").collect(Collectors.toList());
        final List<Integer> columnRowCount = Stream.of(0).collect(Collectors.toList());
        if (object instanceof Map) {
            appendMapItems(object, list, sb2, columnRowCount);
        } else if (object instanceof List || object instanceof Set) {
            appendListItems(object, list, sb2, columnRowCount);
        } else if (object instanceof String) {
            list.set(0, "<td>" + object.toString() + "</td>");
        } else if (object.getClass().isArray()) {
            appendArrayItems(object, list, sb2, columnRowCount);
        } else if (!fieldList.isEmpty()) {
            sb.append("<thead><tr>");
            fieldList.forEach(x -> sb.append("<th>" + x.getName() + "</th>"));
            sb.append("</tr></thead>");
            for (Field f : fieldList) {
                if (f != null && !f.isAccessible()) {
                    f.setAccessible(true);
                }
                if (f.getType().isAssignableFrom(Map.class)) {
                    appendMapItems(f.get(object), list, sb2, columnRowCount);
                } else if (f.getType().isAssignableFrom(List.class) || f.getType().isAssignableFrom(Set.class)) {
                    appendListItems(f.get(object), list, sb2, columnRowCount);
                } else if (f.getType().isArray()) {
                    appendArrayItems(f.get(object), list, sb2, columnRowCount);
                } else {
                    list.set(0, list.get(0) + "<td>" + f.get(object).toString() + "</td>");
                    columnRowCount.set(columnRowCount.size() - 1, 1);
                }
                columnRowCount.add(0);
            }
        } else {
            list.set(0, "<td>" + object.toString() + "</td>");
        }
        list.forEach(x -> sb.append("<tr>" + x + "</tr>"));
        sb.append("</tbody>");
        sb.append("</table>");
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    private void appendMapItems(Object object, List<String> list, StringBuilder sb, List<Integer> columnRowCount) {
        Map<Object, Object> map = (Map<Object, Object>) object;
        for (int ix = map.size(); ix >= list.size(); ix--)
            list.add("");
        columnRowCount.set(columnRowCount.size() - 1, map.size());
        int row = 0;
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            sb.setLength(0);
            if (columnRowCount.size() > 1 && row + 1 > columnRowCount.get(columnRowCount.size() - 2))
                sb.append("<td></td>");
            sb.append("<td>" + entry.getKey() + ":" + entry.getValue() + "</td>");
            list.set(row, list.get(row) + sb.toString());
            row++;
        }
    }

    @SuppressWarnings("unchecked")
    private void appendListItems(Object object, List<String> list, StringBuilder sb, List<Integer> columnRowCount) {
        int row = 0;
        Collection<Object> obj = (Collection<Object>) object;
        for (int ix = obj.size(); ix >= list.size(); ix--)
            list.add("");
        columnRowCount.set(columnRowCount.size() - 1, obj.size());
        for (Object o : obj) {
            sb.setLength(0);
            if (columnRowCount.size() > 1 && row + 1 > columnRowCount.get(columnRowCount.size() - 2)) {
                sb.append("<td></td>");
            }
            sb.append("<td>" + o.toString() + "</td>");
            list.set(row, list.get(row) + sb.toString());
            row++;
        }
    }

    private void appendArrayItems(Object object, List<String> list, StringBuilder sb, List<Integer> columnRowCount) {
        Object[] array = toArray(object);
        List<Object> obj = Arrays.asList(array);
        appendListItems(obj, list, sb, columnRowCount);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private Object[] toArray(Object array) {
        Class clz = array.getClass().getComponentType();
        if (clz.isPrimitive()) {
            int length = Array.getLength(array);
            List list = new ArrayList(length);
            for (int i = 0; i < length; i++)
                list.add(Array.get(array, i));
            return list.toArray();
        }
        return (Object[]) array;
    }
}
