class AddDescriptionToInvitations < ActiveRecord::Migration
  def self.up
    add_column :invitations, :description, :string
  end

  def self.down
    remove_column :invitations, :description
  end
end
