class RemoveGroupFromTododetails < ActiveRecord::Migration
  def self.up
    remove_column :tododetails, :group
  end

  def self.down
    add_column :tododetails, :group, :string
  end
end
