class AddNoteToTodos < ActiveRecord::Migration
  def self.up
    add_column :todos, :note, :text
  end

  def self.down
    remove_column :todos, :note
  end
end
