class AddGroupToSentInvitations < ActiveRecord::Migration
  def self.up
    add_column :sent_invitations, :group, :string
  end

  def self.down
    remove_column :sent_invitations, :group
  end
end
