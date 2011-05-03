class Tododetail < ActiveRecord::Base
  belongs_to :group

  validates :todo, :presence => true
  validates :place, :presence => true
end
