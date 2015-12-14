require 'rubygems'
require 'liquid'

module RelevantCodes
	module Model
		class TestAttribute < Liquid::Drop
			attr_reader :name
			
			def initialize(name)
				@name = name
			end
		end
	end
end
