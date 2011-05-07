class User < ActiveRecord::Base
  has_and_belongs_to_many :groups
  has_many :sent_invitations, :dependent => :destroy
  has_many :recv_invitations, :dependent => :destroy

  validates :name, :presence => true, :uniqueness => true
  validates :number, :presence => true, :uniqueness => true
  validates :email, :presence => true, :uniqueness => true

  attr_accessible :name, :number, :email
end
