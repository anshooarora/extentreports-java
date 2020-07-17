package com.aventstack.extentreports.markuputils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class OrderedList extends HtmlList implements Markup {
    private static final long serialVersionUID = -1763712576872406878L;

    private Object object;

    @Override
    public String getMarkup() {
        return getList(object, ListType.OL);
    }
}
