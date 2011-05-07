class RecvInvitation < ActiveRecord::Base
  belongs_to :user

  validates :group, :presence => true
  validates :sender, :presence => true

end
