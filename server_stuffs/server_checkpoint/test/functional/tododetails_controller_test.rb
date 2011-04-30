require 'test_helper'

class TododetailsControllerTest < ActionController::TestCase
  setup do
    @tododetail = tododetails(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:tododetails)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create tododetail" do
    assert_difference('Tododetail.count') do
      post :create, :tododetail => @tododetail.attributes
    end

    assert_redirected_to tododetail_path(assigns(:tododetail))
  end

  test "should show tododetail" do
    get :show, :id => @tododetail.to_param
    assert_response :success
  end

  test "should get edit" do
    get :edit, :id => @tododetail.to_param
    assert_response :success
  end

  test "should update tododetail" do
    put :update, :id => @tododetail.to_param, :tododetail => @tododetail.attributes
    assert_redirected_to tododetail_path(assigns(:tododetail))
  end

  test "should destroy tododetail" do
    assert_difference('Tododetail.count', -1) do
      delete :destroy, :id => @tododetail.to_param
    end

    assert_redirected_to tododetails_path
  end
end
