package com.relevantcodes.extentreports;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import com.relevantcodes.extentreports.source.ExtentFlag;
import com.relevantcodes.extentreports.support.FileReaderEx;
import com.relevantcodes.extentreports.support.Resources;

class ReportSource {
	//private static ReportSource instance = null;
	private AtomicInteger seed = new AtomicInteger();
	private volatile String src = null;
	private final Object lock = new Object();
	private volatile String testSrc = "";
	
	public void setSource(String source) {
		synchronized (lock) {
			src = source;
		}
	}
	
	public String getSource() {
		synchronized (lock) {
			return src;
		}
	}
	
	public void addTest(String source, DisplayOrder displayOrder) {
		synchronized (lock) {
			if (displayOrder == DisplayOrder.OLDEST_FIRST) {
				testSrc += source;
			}
			else {
				testSrc = source + testSrc;
			}
			
			//if (displayOrder == DisplayOrder.NEWEST_FIRST) {
				//setSource(src.replace(ExtentFlag.getPlaceHolder("test"), ExtentFlag.getPlaceHolder("test") + source));
			//}
				
			//setSource(src.replace(ExtentFlag.getPlaceHolder("test"), source + ExtentFlag.getPlaceHolder("test")));
		}
		
		//System.out.println(testSrc);
	}
	
	public void initialize(String filePath, Boolean replace) {
		synchronized (lock) {
			String sourceFile = "com/relevantcodes/extentreports/source/STANDARD.html";
			
			//System.out.println("1: " + replace);
			
			if (!new File(filePath).isFile()) {
				replace = true;
			}
			
			//System.out.println("2: " + replace);
			
			if (src != null) {
				return;
			}
			
			//System.out.println("3: success");
			
			if (replace) {
				src = Resources.getText(sourceFile);
			} 
			else {
				src = FileReaderEx.readAllText(filePath);			
			}
		}
	}
	
	public void terminate() {
		String s = getSource();
		
		synchronized (lock) {
			src = s.replace(ExtentFlag.getPlaceHolder("test"), ExtentFlag.getPlaceHolder("test") + testSrc);
		}
	}
	
	public int getIndex() {
		return seed.incrementAndGet();
	}
	
	/*
	protected ReportSource() {
	}
	*/
	
	private ReportSource() { }
	
	private static class Instance {
        static final ReportSource INSTANCE = new ReportSource();
    }
	
	public static ReportSource getInstance() {
		return Instance.INSTANCE;
	}

	/*
	 * public static ReportSource getInstance() {
		if (instance == null) {
			synchronized (ReportSource.class) {
				if (instance == null) {
					instance = new ReportSource();
				}
			}
		}
		
		return instance;
	} */
}
