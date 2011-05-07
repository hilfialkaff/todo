class ChangeFromToInvitations < ActiveRecord::Migration
  def self.up
    add_column :sent_invitations, :recipient, :string
    add_column :recv_invitations, :sender, :string

    remove_column :sent_invitations, :to
    remove_column :recv_invitations, :from
  end

  def self.down
    remove_column :sent_invitations, :recipient
    remove_column :recv_invitations, :sender

    add_column :sent_invitations, :to, :string
    add_column :recv_invitations, :from, :string
  end
end
