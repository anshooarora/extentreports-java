package com.relevantcodes.extentreports.markuputils;

class Label implements Markup {

    private String text = "";
    private ExtentColor color = ExtentColor.TRANSPARENT;
    
    public void setText(String text) {
        this.text = text;
    }
    public String getText() { return text; }
    
    public void setColor(ExtentColor color) { 
        this.color = color; 
    }
    public ExtentColor getColor() { return color; }

    public String getMarkup() {
        String lhs = "<span class='label white-text " + String.valueOf(color).toLowerCase() + "'>";
        String rhs = "</span>";
        
        String s = lhs + text + rhs;
        return s;
    }
}
