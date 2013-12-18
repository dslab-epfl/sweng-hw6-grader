(ns sweng-hw6-grader.issues
  (:require [clojure.string :as string]
            [korma.core :refer :all]
            [sweng-hw6-grader.tidbits :as tidbits]
            [sweng-hw6-grader.db :as db]))

; What do I need from issues?
; - Labels (a set of strings will do)
; - ID
; - Title
; - Body
; - Creator
; - Repo ID

(defn parse-issue [issue]
  {:id (:id issue)
   :title (:title issue)
   :body (:body issue)
   :user (-> issue :user :login)
   :labels (set (map :name (:labels issue)))
   :repository (-> issue :repository :full_name)
   :created_at (:created_at issue)
   :updated_at (:updated_at issue)
   })

(def earliest-issue-timestamp "2013-12-13T18:00:00Z")

(defn fetch-issues-since
  "Retrieves a list of github issues in the sweng-epfl organization, starting
  from the given date

  param since: the date, as string in ISO 8601 format: 2013-11-29T08:59:59Z"
  ([] (fetch-issues-since earliest-issue-timestamp))
  ([since]
  (let [query (format "/orgs/sweng-epfl/issues?filter=all&since=%s" since)
        raw-issues (:body (tidbits/gg query))
        parsed-issues (map parse-issue raw-issues)]
    parsed-issues)))

(defn compute-last-update-timestamp
  "Retrieves from the database the time that issues were last updated"
  []
  (let [most-recent-issues (select db/issues
                            (fields :updated_at)
                            (order :updated_at :desc)
                            (limit 1))]
    (:updated_at (first most-recent-issues))))

(defn update-issues-database []
  (let [last-update (or (compute-last-update-timestamp) earliest-issue-timestamp)
        issues (fetch-issues-since last-update)]
    (if (seq issues)
      (insert db/issues (values issues)))))

