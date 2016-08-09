package com.relevantcodes.extentreports.markuputils;

class Table implements Markup {

    private String[][] data;
    
    public void setData(String[][] data) {
        this.data = data;
    }
    public String[][] getData() { return data; }    
    
    @Override
    public String getMarkup() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table>");
        for(int row = 0; row < data.length; row++){
            sb.append("<tr>");
            for(int col = 0; col < data[0].length; col++){
                sb.append("<td>" + data[row][col] + "</td>");
            }
            sb.append("</tr>");
        }
        sb.append("</table>");
        return sb.toString();
    }

}
