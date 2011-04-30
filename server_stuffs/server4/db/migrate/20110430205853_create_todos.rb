class CreateTodos < ActiveRecord::Migration
  def self.up
    create_table :todos do |t|
      t.string :todo
      t.string :place
      t.text :note
      t.text :tag
      t.text :group
      t.string :status

      t.timestamps
    end
  end

  def self.down
    drop_table :todos
  end
end
