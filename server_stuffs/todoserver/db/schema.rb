# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended to check this file into your version control system.

ActiveRecord::Schema.define(:version => 20110505033427) do

  create_table "groups", :force => true do |t|
    t.string   "name"
    t.text     "description"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "groups_users", :id => false, :force => true do |t|
    t.integer "user_id"
    t.integer "group_id"
  end

  create_table "recv_invitations", :force => true do |t|
    t.integer  "user_id"
    t.string   "description"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "group"
    t.string   "sender"
  end

  create_table "sent_invitations", :force => true do |t|
    t.integer  "user_id"
    t.string   "description"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "group"
    t.string   "status"
    t.string   "recipient"
  end

  create_table "tododetails", :force => true do |t|
    t.string   "place"
    t.text     "note"
    t.text     "tag"
    t.integer  "group_id"
    t.string   "status"
    t.string   "priority"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "title"
    t.string   "deadline"
  end

  create_table "users", :force => true do |t|
    t.string   "name"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "number"
    t.string   "email"
  end

  create_table "users_groups", :id => false, :force => true do |t|
    t.integer "user_id"
    t.integer "group_id"
  end

end
