class Invitation < ActiveRecord::Base
  belongs_to :user

  validates :group_name, :presence => true

  def validate
    if Group.find_by_name(group_name).nil?:
      errors.add :group_name, "doesn't exist"
    end

    if User.find(user_id).nil?:
      errors.add :user_id, "doesn't exist"
    end
  end
end
