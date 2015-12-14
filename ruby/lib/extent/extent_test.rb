require 'liquid'

require_relative 'model/test'
require_relative 'model/log'
require_relative 'model/test_attribute'
require_relative 'model/category'
require_relative 'model/author'

module RelevantCodes
	class ExtentTest < Liquid::Drop
		def initialize(name, description = nil)
			@test = Model::Test.new(name, description)
		end
		
		def log(status, step_name = nil, details)
			log = RelevantCodes::Model::Log.new
			log.status = status
			log.step_name = step_name
			log.details = details
			
			@test.log = log
		end
		
		def append_child(node)
			node = node.test
			node.is_child = true
			node.end

			@test.child = node
		end
		
		def assign_category(*names)
			names.each do |name|
				@test.category = Model::Category.new(name)
			end
			
			self
		end
		
		def assign_author(*names)
			names.each do |name|
				@test.author = Model::Author.new(name)
			end
			
			self
		end
		
		def test
			@test
		end
	end
end
