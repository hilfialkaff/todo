class ChangeFromToToInvitation < ActiveRecord::Migration
  def self.up
    add_column :invitations, :to, :string
    remove_column :invitations, :from, :string
  end

  def self.down
    remove_column :invitations, :to, :string
    add_column :invitations, :from, :string
  end
end
