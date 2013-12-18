(ns sweng-hw6-grader.issues-test
  (:require [midje.sweet :refer :all]
            [clj-http.fake :as fake]
            [korma.core :refer :all]
            [korma.db :refer [transaction rollback]]
            [sweng-hw6-grader.db :as db]
            [sweng-hw6-grader.test-utils :refer :all]
            [sweng-hw6-grader.issues :refer :all]))

(with-fixtures
  (fact "It can fetch issues from GitHub"
    (fetch-issues-since) => 
    '({:id 24321855,
       :title "Homework6Contest: This is an issue title",
       :body "Some text...",
       :user "SomeStudent",
       :labels #{},
       :repository "sweng-epfl/sweng-2013-team-master"
       :created_at "2013-12-15T23:04:11Z"
       :updated_at "2013-12-15T23:04:11Z"}
      {:id 24293198,
       :title "Homework6Contest - wrong delimiter in editQuestion Tag",
       :body
       "when I put in the tag field dwrtçhtrf the tag is not dwrt,\n                      htrf but the tag dwrtçhtrf in offline mode.",
       :user "SomeStudent",
       :labels #{"Homework6Contest"},
       :repository "sweng-epfl/sweng-2013-team-master"
       :created_at "2013-12-14T14:42:48Z"
       :updated_at "2013-12-15T12:33:48Z"}))
  (fact "It can store issues in a database"
    (update-issues-database)
    (map :id (select db/issues (fields :id))) => [24321855, 24293198])
  (fact "Multiple updates only query for new issues"
    (update-issues-database)
    (fake/with-fake-routes-in-isolation
      {"https://api.github.com/orgs/sweng-epfl/issues?filter=all&since=2013-12-15T23:04:11Z"
       (fn [req] {:status 200 :body "[]"})}
      (update-issues-database)))
  )
