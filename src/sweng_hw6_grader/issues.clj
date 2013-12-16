(ns sweng-hw6-grader.issues
  (:require [sweng-hw6-grader.tidbits :as tidbits]))

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
   :labels (map :name (:labels issue))
   :repository (-> issue :repository :full_name)
   })  

(defn fetch-issues-since
  "Retrieves a list of github issues in the sweng-epfl organization, starting
  from the given date
  
  param since: the date, as string in ISO 8601 format: 2013-11-29T08:59:59Z"
  ([] (fetch-issues-since "2013-12-13T18:00:00Z"))
  ([since]
  (let [query (format "/orgs/sweng-epfl/issues?filter=all&since=%s" since)
        raw-issues (:body (tidbits/gg query))
        parsed-issues (map parse-issue raw-issues)]
    parsed-issues)))

