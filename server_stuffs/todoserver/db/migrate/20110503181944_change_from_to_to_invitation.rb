class ChangeFromToToInvitation < ActiveRecord::Migration
  def self.up
    add_column :invitations, :to, :string
    remove_column :invitations, :from
  end

  def self.down
    remove_column :invitations, :to
    add_column :invitations, :from, :string
  end
end
