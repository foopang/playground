(ns reddit-viewer.core
  (:require
   [ajax.core :as ajax]
   [baking-soda.core :as b]
   [reagent.core :as r]
   [reddit-viewer.chart :as chart]
   [reddit-viewer.controllers]
   [re-frame.core :as rf]))

(defn inspect [data]
  (r/with-let [open? (r/atom false)]
    [:div.m-2
     [b/Button
      {:color "link"
       :on-click #(swap! open? not)}
      (if @open? "collapse" "expand")]
     [b/Collapse
      {:isOpen @open?}
      [b/Card
       [b/CardBlock
        [:pre (-> @posts print with-out-str)]]]]]))

(defn sort-posts [title sort-key]
  [:button.btn.btn-secondary
   {:on-click #(rf/dispatch [:sort-posts sort-key])}
   (str "sort posts by " title)])

;; -------------------------
;; Views

(defn display-post [{:keys [permalink subreddit title score url] :as post}]
  [b/Card
   {:class "m-2"}
   [b/CardBlock
    [b/CardTitle
     [:a {:href (str "https://reddit.com" permalink)} title " "]]
    [:div [b/Badge {:color "info"} subreddit " score " score]]
    [:img {:width "300px" :src url}]]])

(defn display-posts [posts]
  (when-not (empty? posts)
    [:div
     (for [posts-row (partition-all 3 posts)]
       ^{:key posts-row}
       [:div.row
        (for [post posts-row]
          ^{:key post}
          [:div.col-4 [display-post post]])])]))

(defn navitem [title view id]
  [:li.nav-item
   {:class-name (when (= id view) "active")}
   [:a.nav-link
    {:href "#"
     :on-click #(rf/dispatch [:select id])}
    title]])

(defn navbar [view]
  [b/Navbar
   {:className "navbar-toggle-md navbar-light bg-faded"}
   [b/Nav
    {:className "navbar-nav mr-auto"}
    [navitem "Posts" view :posts]
    [navitem "Chart" view :chart]]])

(defn home-page []
  (let [view @(rf/subscribe [:view])]
    [:div
     [navbar view]
      [:div.cart>div.cart-block
        [:div.btn-group
         [sort-posts "score" :score]
         [sort-posts "comments" :num_comments]]
        (case view
          :chart [chart/chart-posts-by-votes]
          :posts [display-posts @(rf/subscribe [:posts])])]]))

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (rf/dispatch-sync [:initialize-db])
  (rf/dispatch [:load-posts])
  (mount-root))
