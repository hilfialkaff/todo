class Tododetail < ActiveRecord::Base
  belongs_to :group

  validates :title, :presence => true 
end
