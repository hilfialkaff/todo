class RemoveStatusFromTodos < ActiveRecord::Migration
  def self.up
    remove_column :todos, :status
  end

  def self.down
    add_column :todos, :status, :string
  end
end
