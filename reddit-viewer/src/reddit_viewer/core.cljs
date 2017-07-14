(ns reddit-viewer.core
  (:require
   [ajax.core :as ajax]
   [baking-soda.core :as b]
   [reagent.core :as r]
   [reddit-viewer.chart :as chart]))

(defonce posts (r/atom nil))

(defn load-posts []
  (ajax/GET "http://www.reddit.com/r/Catloaf.json?sort=new&limit=9"
            {:handler #(->> (get-in % [:data :children])
                            (map :data)
                            (reset! posts))
             :response-format :json
             :keywords? true}))

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
  (when-not (empty? @posts)
    [b/Button
     {:on-click #(swap! posts (partial sort-by sort-key))}
     (str "sort posts by " title)]))

;; -------------------------
;; Views

(defn display-post [{:keys [permalink subreddit title score url] :as post}]
  [b/Card
   {:class "m-2"}
   [b/CardBlock
    [b/CardTitle
     [:a {:href (str "https://reddit.com" permalink)} title " "]]
    [:div [b/Badge {:color "info"} subreddit " score " score]]
    [:img {:width "300px" :src url}]
    [inspect post]]])

(defn find-posts-with-preview [posts]
  (filter #(= (:post_hint %) "image") posts))

(defn display-posts [posts]
  (when-not (empty? posts)
    [:div
     (for [posts-row (->> posts (find-posts-with-preview) (partition-all 3))]
       ^{:key posts-row}
       [:div.row
        (for [post posts-row]
          ^{:key post}
          [:div.col-4 [display-post post]])])]))

(defn navitem [title view id]
  [b/NavItem
   {:className (when (= id @view) "active")}
   [b/NavLink
    {:href "#"
     :on-click #(reset! view id)}
    title]])

(defn navbar [view]
  [b/Navbar
   {:className "navbar-toggle-md navbar-light bg-faded"}
   [b/Nav
    {:className "navbar-nav mr-auto"}
    [navitem "Posts" view :posts]
    [navitem "Chart" view :chart]]])

(defn home-page []
  (r/with-let [view (r/atom :posts)]
    [:div
     [navbar view]
      [b/Card
       [b/CardBlock
        [b/ButtonGroup
         [sort-posts "score" :score]
         [sort-posts "comments" :num_comments]]
        (case @view
          :chart [chart/chart-posts-by-votes posts]
          :posts [display-posts @posts])]]]))

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
