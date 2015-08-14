package com.relevantcodes.extentmerge;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Program {
	public static void main(String[] args) throws IOException {
		String[] files = Arrays.copyOfRange(args, 0, args.length - 1);
		String mergedFile = args[args.length - 1];
		
		Extractor extractor = new Extractor(files);
		
		String quickTestSummarySource = extractor.getQuickTestSummarySource();
		String testSource = extractor.getTestSource();
		
		Builder.build(new File(mergedFile), testSource, quickTestSummarySource);
	}
}
