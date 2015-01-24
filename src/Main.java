import com.relevantcodes.cubereports.*;

public class Main {
	// *REQUIRED
	static CubeReports cube = CubeReports.get(Main.class); 
	
	public static void main(String[] args) {
		// *REQUIRED
        // init( filePath, replaceExisting )
        //     initializes the reporter at a given path
        //    filePath - path of new report filePath
        //    replaceExisting - 
        //        true - overwrite existing file (if any)
        //        false - use existing file to create reports, tests will be appended at the very top
        cube.init("C:\\Users\\Sai Baba\\Documents\\workspace\\CubeReports\\Cube.htm", true);
 
        // *REQUIRED
        // startTest( testName )
        //    creates a toggle for the given test, adds all log events under it    
        cube.startTest("Main");
 
        // this step does the logging
        // log(logStatus, stepName, details)
        cube.log(LogStatus.PASS, "StepName", "PASS Details");      
        cube.log(LogStatus.ERROR, "StepName", "Err details");
        cube.log(LogStatus.WARNING, "StepName", "Warning details");
        cube.log(LogStatus.FAIL, "StepName", "Fail details");
        cube.log(LogStatus.INFO, "StepName", "Info details");
        cube.log(LogStatus.FATAL, "StepName", "Info details");
 
        // *REQUIRED
        // endTest()
        //    use to end current toggle level
        cube.endTest();
	}
}
