package com.relevantcodes.extentreports;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.relevantcodes.extentreports.model.RunInstance;
import com.relevantcodes.extentreports.model.ScreenCapture;
import com.relevantcodes.extentreports.source.ExtentFlag;
import com.relevantcodes.extentreports.support.FileReaderEx;
import com.relevantcodes.extentreports.support.Resources;

class ReportSource {
	//private static ReportSource instance = null;
	private AtomicInteger seed = new AtomicInteger();
	private volatile String src = null;
	private final Object lock = new Object();
	private volatile String testSrc = "";
	private volatile String filePath = "";
	private Map<String, String> systemInfo;	
	private ArrayList<ScreenCapture> screenCapture;
	
	public synchronized void setFilePath(String filePath) {
		synchronized (lock) {
			this.filePath = filePath;
		}
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public synchronized void setSource(String source) {
		synchronized (lock) {
			src = source;
		}
	}
	
	public synchronized String getSource() {
		synchronized (lock) {
			return src;
		}
	}
	
	public synchronized void addTest(String source, DisplayOrder displayOrder) {
		synchronized (lock) {
			if (displayOrder == DisplayOrder.OLDEST_FIRST) {
				testSrc = source + testSrc;
				//setSource(src.replace(ExtentFlag.getPlaceHolder("test"), testSrc + ExtentFlag.getPlaceHolder("test")));
			}
			else {
				testSrc += source;
				//setSource(src.replace(ExtentFlag.getPlaceHolder("test"), ExtentFlag.getPlaceHolder("test") + testSrc));
			}
			
			//System.out.println(testSrc);
			
			//testSrc = "";
			//if (displayOrder == DisplayOrder.NEWEST_FIRST) {
				//setSource(src.replace(ExtentFlag.getPlaceHolder("test"), ExtentFlag.getPlaceHolder("test") + source));
			//}
				
			//setSource(src.replace(ExtentFlag.getPlaceHolder("test"), source + ExtentFlag.getPlaceHolder("test")));
		}
	}
	
	public void updateSuiteExecutionTime(String startedAt, String endedAt) {
		String[] flags = { ExtentFlag.getPlaceHolder("suiteStartTime"), ExtentFlag.getPlaceHolder("suiteEndTime") };
		String[] values = { startedAt, endedAt };
		
		synchronized (lock) {
			src = SourceBuilder.build(src, flags, values);
		}
	}
	
	public void updateSystemInfo(Map<String, String> info) {
		if (info.equals(systemInfo)) {
			return;
		}
		
		if (systemInfo == null) {
			systemInfo = info;
		}
		
		String systemSrc = SystemInfoViewBuilder.getSource(info);
		
		String[] flags = new String[] { ExtentFlag.getPlaceHolder("systemInfoView") };
		String[] values = new String[] { systemSrc + ExtentFlag.getPlaceHolder("systemInfoView") };
		
		synchronized (lock) {
			src = SourceBuilder.build(src, flags, values);
		}
	}
	
	public void updateMediaInfo(ArrayList<ScreenCapture> screenCapture) {
		//if (screenCapture.equals(this.screenCapture)) {
			//return;
		//}
		
		if (this.screenCapture == null) {
			this.screenCapture = screenCapture;
			return;
		}

		for (ScreenCapture sc : screenCapture) {
			if (!this.screenCapture.contains(sc)) {
				this.screenCapture.add(sc);
			}
		}
	}
	
	private void updateMediaInfo() {
		if (this.screenCapture.size() == 0) {
			return;
		}
		
		String imageSrc = MediaViewBuilder.getSource(this.screenCapture);
		String[] flags = new String[] { ExtentFlag.getPlaceHolder("imagesView") };
		String[] values = new String[] { imageSrc + ExtentFlag.getPlaceHolder("imagesView") };
		
		synchronized (lock) {
			// build sources by replacing the flag with the values
			src = SourceBuilder.build(src, flags, values);
			
			// clear the screenCapture list so the same images are not written twice
			RunInstance.getInstance().screenCapture.clear();
		}
	}
	
	public void initialize(String filePath, Boolean replace) {
		synchronized (lock) {
			String sourceFile = "com/relevantcodes/extentreports/source/STANDARD.html";
						
			if (!new File(filePath).isFile()) {
				replace = true;
			}
					
			if (src != null) {
				return;
			}

			if (replace) {
				src = Resources.getText(sourceFile);
			} 
			else {
				src = FileReaderEx.readAllText(filePath);			
			}
		}
	}
	
	public void terminate() {
		if (testSrc == "")
			return;
		
		synchronized (lock) {
			updateMediaInfo();
			src = src.replace(ExtentFlag.getPlaceHolder("test"), ExtentFlag.getPlaceHolder("test") + testSrc);
			testSrc = "";
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
