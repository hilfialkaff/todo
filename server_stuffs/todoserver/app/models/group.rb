class Group < ActiveRecord::Base
  has_and_belongs_to_many :users
  has_many :tododetails, :dependent => :destroy

  validates :name, :presence => true
  validates_uniqueness_of :name, :scope => :id

  attr_accessible :name
end
