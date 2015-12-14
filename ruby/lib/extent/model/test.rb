require 'liquid'

require_relative '../date_formats'
require_relative '../icon'
require_relative 'log'
require_relative 'test_attribute'
require_relative 'author'
require_relative 'category'

module RelevantCodes
	module Model
		class Test < Liquid::Drop
			attr_reader :name, :description
			attr_reader :status
			attr_reader :start_time, :end_time, :run_duration
			attr_reader :logs, :children
			attr_reader :authors, :categories
		
			attr_writer :is_child
			
			def initialize(name, description)
				@name = name.strip
				@description = description.nil? || description == '' ? '' : description.strip
				@status = 'unknown'
			
				@child_node = false
				@has_ended = false
				@has_children = false
				
				@categories = []
				@authors = []
				
				@start_time = Time.new.strftime(DATE_TIME_FORMAT)
				@end_time = nil
				
				@children = []
				
				@logs = []
				
				@screen_captures = []
				@screen_casts = []
				
				@internal_warning = nil
			end
			
			def run_duration
				Time.parse(@end_time) - Time.parse(@start_time)
			end
			
			def end
				@end_time = Time.new.strftime(DATE_TIME_FORMAT)
				@arr = []

				finalize self
			end
			
			def category=(category)
				@categories << category
			end
			
			def author=(auth)
				authors << auth
			end

			def log=(l)
				@logs << l
			end
			
			def child=(c)
				@children << c
				@has_children = true
			end
			
			def is_child?
				@is_child
			end
			
			def has_children?
				@has_children
			end
					
			private

			def finalize(test)
				test.logs.each do |log|
					@arr.push(log.status)
				end
				
				if test.has_children?
					#puts '-----------------------------------------------------'
					#puts 'in node'
					#puts '-----------------------------------------------------'
					test.children.each do |node|
						#puts '-----------------------------------------------------'
						#puts 'in node'
						#puts '-----------------------------------------------------'
						finalize(node)
					end
				end
				
				if @arr.include?('fatal') 
					ret_val = 'fatal'
				elsif @arr.include?('fail') 
					ret_val = 'fail'
				elsif @arr.include?('error') 
					ret_val = 'error'
				elsif @arr.include?('warning') 
					ret_val = 'warning'
				elsif @arr.include?('pass')
					ret_val = 'pass'
				elsif @arr.include?('skip') 
					ret_val = 'skip'
				elsif @arr.include?('info') 
					ret_val = 'pass'
				else 
					ret_val = 'unknown'
				end
				
				@status = ret_val
			end
			
			def has_categories?
				return @categories.size > 0
			end
			
			def has_authors?
				return @authors.size > 0
			end
		end
	end
end
