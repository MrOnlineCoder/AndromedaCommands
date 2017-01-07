package com.mronlinecoder;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter{
	
	@Override
	public String format(LogRecord record){
	     return "["+getTime()+"] "+record.getMessage()+"\r\n";
	}

	public String getTime() {
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	    return (sdf.format(cal.getTime()));
	}

}
