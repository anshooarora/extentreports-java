require 'rubygems'
require 'liquid'

require_relative '../icon'

module RelevantCodes
	module Model
		class Log < Liquid::Drop
			attr_accessor :step_name, :details
			attr_writer :status
			attr_reader :timestamp
						
			def initialize
				@timestamp = Time.new.strftime(TIME_FORMAT)
			end
			
			def status
				return @status.to_s
			end
			
			def icon
				RelevantCodes::Icon.icon(status)
			end
		end
	end
end
