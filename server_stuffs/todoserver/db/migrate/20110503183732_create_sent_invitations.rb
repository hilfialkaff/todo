class CreateSentInvitations < ActiveRecord::Migration
  def self.up
    create_table :sent_invitations do |t|
      t.string :to
      t.integer :user_id
      t.string :description

      t.timestamps
    end
  end

  def self.down
    drop_table :sent_invitations
  end
end
