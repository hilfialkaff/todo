class RemoveNoteFromTodos < ActiveRecord::Migration
  def self.up
    remove_column :todos, :note
  end

  def self.down
    add_column :todos, :note, :string
  end
end
