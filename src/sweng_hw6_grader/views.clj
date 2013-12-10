(ns sweng-hw6-grader.views
  (:require [hiccup.page :as hiccup]))


(defn layout [title body]
  (hiccup/html5
    [:head
     [:meta {:charset "utf-8"}]
     [:title title]]
    [:body
      [:h1 "SwEng 2013 Homework 6 Contest"]
      body]))


(defn index []
  (layout "Homework 6 Contest" [:p "Coming soon..."]))

(def issue-titles {:violations "Issues"
                   :blocker_violations "Blocker"
                   :critical_violations "Critical"
                   :major_violations "Major"
                   :minor_violations "Minor"
                   :info_violations "Info"
                   :false_positive_issues "False positives"})

(defn score-table [metrics initial final]
  [:table
   [:tr (map (fn [h] [:th h]) (concat [""] (map issue-titles metrics)))]
   [:tr [:th "Initial"]
        (map (fn [d] [:td d]) initial)]
   [:tr [:th "Final"]
        (map (fn [d] [:td d]) final)]])

(defn score-view [resource metrics initial final score]
  (layout (str "Scores for " resource)
          [:div
           [:h2 "Issues"]
           (score-table metrics initial final)
           [:h2 "Score"]
           [:p "Your current score is " (format "%.0f" score) " points."]]))
