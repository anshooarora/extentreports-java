require 'liquid'

require_relative 'component_timers'

module RelevantCodes
	class Report < Liquid::Drop
		include ComponentTimers
		
		def initialize(file_path)
			@file_path = file_path
			@display_order = ''
			@terminates = false
			
			@test_id = 0
			
			@start_time = Time.new.strftime(DATE_TIME_FORMAT)
			@end_time = nil
			
			@executing_test = nil
			
			@current_report_status = :unknown
			
			@log_events_status_unique = []
			
			@tests = []
			
			@category_test_context = {}
			
			@system_variables = {}
			
			@configuration = {
				'encoding' => 'UTF-8',
				'document_title' => 'ExtentReports for Ruby',
				'report_name' => 'ExtentReports',
				'report_headline' => '',
				'scripts' => '',
				'styles' => ''
			}
			
			@test_runner_logs = []
		end
		
		def tests
			@tests
		end
		
		def start_time
			@start_time
		end
		
		def end_time
			Time.new.strftime(DATE_TIME_FORMAT)
		end
		
		def configuration
			@configuration
		end
		
		def category_tests_context
			@category_test_context
		end
		
		private
		
		def finalize_test(extent_test)
			test = extent_test.test
			
			if test.is_child?
				@tests.remove(test)
				return
			end
			
			test.end	

			test.categories.each do |category|
				@category_test_context[category.name] ||= []
				@category_test_context[category.name] << test
			end
		end
		
		def remove_child_tests
			@tests.each_with_index do |t, ix|
				if t.test.is_child?
					@tests.delete_at(ix)
				end
			end
		end
	end
end
