class RecvInvitation < ActiveRecord::Base
  belongs_to :user

  validates :group, :presence => true
  validates :from, :presence => true

end
