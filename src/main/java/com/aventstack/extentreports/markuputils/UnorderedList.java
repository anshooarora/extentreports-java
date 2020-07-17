package com.aventstack.extentreports.markuputils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
class UnorderedList extends HtmlList implements Markup {
    private static final long serialVersionUID = 2056301782889894819L;

    private Object object;

    @Override
    public String getMarkup() {
        return getList(object, ListType.UL);
    }

}
