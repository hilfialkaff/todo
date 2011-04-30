class User < ActiveRecord::Base
  validates :name, :presence => true
  validates_uniqueness_of :name
  has_many :groups
end
