class AddStatusToSentInvitations < ActiveRecord::Migration
  def self.up
    add_column :sent_invitations, :status, :string
  end

  def self.down
    remove_column :sent_invitations, :status
  end
end
