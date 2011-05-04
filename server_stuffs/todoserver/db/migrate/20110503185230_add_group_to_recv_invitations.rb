class AddGroupToRecvInvitations < ActiveRecord::Migration
  def self.up
    add_column :recv_invitations, :group, :string
  end

  def self.down
    remove_column :recv_invitations, :group
  end
end
