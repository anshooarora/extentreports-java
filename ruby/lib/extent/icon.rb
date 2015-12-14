require 'rubygems'
require 'liquid'

module RelevantCodes
	class Icon < Liquid::Drop
		def self.icon(status)
			case status
			when 'pass' 
				ret_val = 'mdi-action-check-circle'
			when 'fail' 
				ret_val = 'mdi-navigation-cancel'
			when 'fatal' 
				ret_val = 'mdi-navigation-cancel'
			when 'error' 
				ret_val = 'mdi-alert-error'
			when 'warning' 
				ret_val = 'mdi-alert-warning'
			when 'skip' 
				ret_val = 'mdi-content-redo'
			when 'info' 
				ret_val = 'mdi-action-info-outline'
			else 
				ret_val = 'mdi-action-help'
			end

			ret_val
		end
	end
end
