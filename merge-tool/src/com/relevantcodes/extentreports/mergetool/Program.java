package com.relevantcodes.extentreports.mergetool;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Program {
	public static void main(String[] args) throws IOException {
		String[] inputFiles = Arrays.copyOfRange(args, 0, args.length - 1);
		String mergedFile = args[args.length - 1];
		
		Extractor extractor = new Extractor(inputFiles);

		Builder builder = new Builder(extractor);
		builder.build(new File(mergedFile));
	}
}
