class CreateInvitations < ActiveRecord::Migration
  def self.up
    create_table :invitations do |t|
      t.string :group_name
      t.string :from
      t.integer :user_id

      t.timestamps
    end
  end

  def self.down
    drop_table :invitations
  end
end
