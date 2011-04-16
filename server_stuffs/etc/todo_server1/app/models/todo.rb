class Todo < ActiveRecord::Base
  validates :title, :presence => true
  validates :status, :numericality => {:greater_than => 0, :less_than => 3}  
  validates :priority, :numericality => {:greater_than => 0, :less_than => 3}

  belongs_to :group
end
