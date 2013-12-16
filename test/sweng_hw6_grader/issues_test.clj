(ns sweng-hw6-grader.issues-test
  (:require [midje.sweet :refer :all]
            [clj-http.fake :as fake]
            [sweng-hw6-grader.http-test :refer :all]
            [sweng-hw6-grader.issues :refer :all]))

(fake/with-fake-routes fake-routes
  (facts "About the issues"
    (fact "It can fetch issues from GitHub"
      (fetch-issues-since) => 
        '({:id 24321855,
          :title "Homework6Contest: This is an issue title",
          :body "Some text...",
          :user "SomeStudent",
          :labels (),
          :repository "sweng-epfl/sweng-2013-team-master"}
         {:id 24293198,
          :title "Homework6Contest - wrong delimiter in editQuestion Tag",
          :body
          "when I put in the tag field dwrtçhtrf the tag is not dwrt,\n                      htrf but the tag dwrtçhtrf in offline mode.",
          :user "SomeStudent",
          :labels ("Homework6Contest"),
          :repository "sweng-epfl/sweng-2013-team-master"}))))
