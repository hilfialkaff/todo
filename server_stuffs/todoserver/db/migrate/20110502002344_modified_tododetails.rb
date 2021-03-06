class ModifiedTododetails < ActiveRecord::Migration
  def self.up
    create_table :tododetails do |t|
      t.string :todo
      t.string :place
      t.text :note
      t.text :tag
      t.references :group
      t.string :status
      t.string :priority

      t.timestamps
    end
  end

  def self.down
    drop_table :tododetails
  end
end
