require 'rubygems'
require 'liquid'

require_relative 'report'
require_relative 'extent_test'
require_relative 'icon'

module RelevantCodes
	class ExtentReports < Report
		def initialize(file_path)
			super(file_path)
		end
		
		def start_test(test_name, description = nil)
			@tests << RelevantCodes::ExtentTest.new(test_name, description)
			@tests[-1]
		end
		
		def end_test(extent_test)
			finalize_test(extent_test)
		end
		
		def flush
			remove_child_tests
		
			markup = Liquid::Template.parse(File.read('view/extent.html.liquid')).render('report' => self)
			
			File.open(@file_path, 'w') {
				|file| file.write(markup)
			}
		end
	end
end
