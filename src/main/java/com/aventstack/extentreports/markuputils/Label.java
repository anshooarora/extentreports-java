package com.aventstack.extentreports.markuputils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
class Label implements Markup {
    private static final long serialVersionUID = 548763908072445261L;

    private String text;
    private ExtentColor color;

    public String getMarkup() {
        if (text == null || text.isEmpty())
            return "";
        String textColor = color != ExtentColor.WHITE ? "white-text" : "black-text";
        String lhs = "<span class='badge " + textColor + " " + String.valueOf(color).toLowerCase() + "'>";
        String rhs = "</span>";
        return lhs + text + rhs;
    }

    public static class LabelBuilder {
        private String text = "";
        private ExtentColor color = ExtentColor.TRANSPARENT;
    }
}
