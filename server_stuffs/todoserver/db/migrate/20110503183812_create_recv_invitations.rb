class CreateRecvInvitations < ActiveRecord::Migration
  def self.up
    create_table :recv_invitations do |t|
      t.string :from
      t.integer :user_id
      t.string :description

      t.timestamps
    end
  end

  def self.down
    drop_table :recv_invitations
  end
end
