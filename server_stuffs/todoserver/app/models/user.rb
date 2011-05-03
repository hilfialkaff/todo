class User < ActiveRecord::Base
  has_and_belongs_to_many :groups
  has_many :invitations, :dependent => :destroy

  validates :name, :presence => true
  validates_uniqueness_of :name, :scope => :id

  attr_accessible :name
end
