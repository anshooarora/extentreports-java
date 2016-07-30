package com.relevantcodes.extentreports;

@SuppressWarnings("serial")
public class InvalidFileException extends Exception {
    public InvalidFileException() {
        super();
    }
    
    public InvalidFileException(Throwable t) {
        super(t);
    }

    public InvalidFileException(String string) {
        super(string);
    }

    public InvalidFileException(String string, Throwable t) {
        super(string, t);
    }   

}
