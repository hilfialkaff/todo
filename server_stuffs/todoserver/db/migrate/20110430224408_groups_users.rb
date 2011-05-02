class GroupsUsers < ActiveRecord::Migration
  def self.up
    create_table :groups_users, :id => false do |t|
      t.integer :user_id
      t.integer :group_id
    end
  end

  def self.down
    drop_table :groups_users
  end
end
