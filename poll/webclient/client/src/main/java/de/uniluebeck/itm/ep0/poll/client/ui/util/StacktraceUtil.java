package de.uniluebeck.itm.ep0.poll.client.ui.util;

public class StacktraceUtil { 
    
    private static final String DEFAULT = "No stacktrace";    
    private static final String LINE_SEPARATOR = "\n";
    private static final String DOTS = "...";
    private static final int MAX_VISIBLE_ELEMENTS = 20;
    
    public static String stacktraceToString(final Throwable throwable) {     
        if (throwable == null) return DEFAULT;
        
        String result = "";
        //result += throwable.toString(); 
        //result += LINE_SEPARATOR;
        
        int i = 0;
        for (StackTraceElement element : throwable.getStackTrace()) {
            if (i > MAX_VISIBLE_ELEMENTS) {
                result += DOTS;
                break;
            }
            result += element;
            result += LINE_SEPARATOR;
            i++;            
        }
        
        return result;
    }
}
