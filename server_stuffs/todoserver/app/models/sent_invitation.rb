class SentInvitation < ActiveRecord::Base
  belongs_to :user

  validates :recipient, :presence => true
  validates :group, :presence => true

  def validate 
    if Group.find_by_name(group).nil?:
      errors.add :group, "Cannot find group"
    end

    if User.find_by_name(recipient).nil?:
      errors.add :recipient, "Cannot find user"
    end

    if status != "Pending" && status != "Accepted" && status != "Rejected":
      errors.add :status, "Status not valid"
    end

    if User.find_by_name(recipient).nil? == false:
      if User.find_by_name(recipient).equal?(User.find(user_id))
        errors.add :recipient, "Cannot invite yourself"
      end
    end

    if Group.find_by_name(group).nil? == false:
      if Group.find_by_name(group).users.find(user_id).nil?
        errors.add :group, "Sender not in group"
      end
    end
  end
end
