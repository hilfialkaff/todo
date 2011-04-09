class CreateTodos < ActiveRecord::Migration
  def self.up
    create_table :todos do |t|
      t.string :title
      t.string :place
      t.string :note
      t.string :tag
      t.string :assembly
      t.string :status
      t.integer :priority

      t.timestamps
    end
  end

  def self.down
    drop_table :todos
  end
end
