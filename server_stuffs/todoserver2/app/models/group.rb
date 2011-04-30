class Group < ActiveRecord::Base
  validates :name, :presence => true
  validates :members, :presence => true
  validates_uniqueness_of :name
  belongs_to :user
end
