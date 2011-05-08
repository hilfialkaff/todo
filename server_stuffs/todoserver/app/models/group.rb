class Group < ActiveRecord::Base
  has_and_belongs_to_many :users
  has_many :tododetails, :dependent => :destroy

  validates :name, :presence => true, :uniqueness => true

  attr_accessible :name => true
  attr_accessible :description => true
end
