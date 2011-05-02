Todoserver::Application.routes.draw do
  resources :users do
    resources :groups do
      resources :tododetails do
      end
    end
  end

  resources :invitations

  resources :groups do
    resources :tododetails
  end

  get "home/index"

  # You can have the root of your site routed with "root"
  # just remember to delete public/index.html.
  root :to => "home#index"

  # See how all your routes lay out with "rake routes"

end
