class DropInvitations < ActiveRecord::Migration
  def self.up
    drop_table :invitations
  end

  def self.down
    raise ActiveRecord::IrreversibleMigration 
  end
end
