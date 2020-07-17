package com.aventstack.extentreports.markuputils;

public class MarkupHelper {

    public static Markup createLabel(String text, ExtentColor color) {
        return Label.builder().text(text).color(color).build();
    }

    public static Markup createCodeBlock(String code) {
        return CodeBlock.builder().code(code).build();
    }

    public static Markup createCodeBlock(String code, CodeLanguage lang) {
        return CodeBlock.builder().code(code).lang(lang).build();
    }

    public static Markup createCodeBlock(Object o) {
        return CodeBlock.builder().jsonObject(o).lang(CodeLanguage.JSON).build();
    }

    /**
     * Creates an ordered list from one of the following underlying collections
     * 
     * <ul>
     * <li>List</li>
     * <li>Map</li>
     * <li>Set</li>
     * </ul>
     * 
     * @param o
     *            An iterable collection
     * @return {@link Markup}
     */
    public static Markup createOrderedList(Object o) {
        return OrderedList.builder().object(o).build();
    }

    /**
     * Creates an unordered list from one of the following underlying
     * collections
     * 
     * <ul>
     * <li>List</li>
     * <li>Map</li>
     * <li>Set</li>
     * </ul>
     * 
     * @param o
     *            An iterable collection
     * @return {@link Markup}
     */
    public static Markup createUnorderedList(Object o) {
        return UnorderedList.builder().object(o).build();
    }

    public static Markup createTable(String[][] data, String[] cssClass) {
        return Table.builder().data(data).cssClasses(cssClass).build();
    }

    public static Markup createTable(String[][] data, String cssClass) {
        return Table.builder().data(data).cssClass(cssClass).build();
    }

    public static Markup createTable(String[][] data) {
        return Table.builder().data(data).build();
    }

    public static Markup toTable(Object o, String[] cssClass) {
        return Table.builder().object(o).cssClasses(cssClass).build();
    }

    public static Markup toTable(Object o, String cssClass) {
        return Table.builder().object(o).cssClass(cssClass).build();
    }

    public static Markup toTable(Object o) {
        return Table.builder().object(o).build();
    }

}
