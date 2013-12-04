(ns sweng-hw6-grader.tidbits
  (:require [clj-http.client :as client]
            [clojure.pprint :as pprint]
            [clojure.string :as string]
            [clojure.java.io :as io]))

(defn get-oauth-token
  "Get an OAuth2 token from GitHub"
  []
  (client/post "https://api.github.com/authorizations"
               {:basic-auth ["Sjlver" "2wXVf3EBuySH"]
                :body (client/json-encode {:note "SwEng HW6 Grader"
                                           :scopes ["repo"]})
                :debug true}))


(defn github-helper
  "Helper for easier getting; adds Github prefix and OAuth token automatically
  Create a github helper"
  [method]
  (fn helper 
    ([url] (helper url {}))
    ([url params]
     (let [full-url (str "https://api.github.com" url)
           user-agent {"User-Agent" "SwEng HW6 Grader"}
           full-params (conj {:oauth-token "c51e6e73fe7bf706942ef41eda1a10435d130027"
                              :as :json
                              :headers user-agent} params)
           response (method full-url full-params)]
       (pprint/pprint response)
       response))))
(def g (github-helper client/get))
(def p (github-helper client/post))
(def d (github-helper client/delete))

; Interesting URLs:
; /repos/Sjlver/github-issues-tests/issues/3/events
; /repos/Sjlver/github-issues-tests/issues/3/comments
; /repos/Sjlver/github-issues-tests/issues/3
; /orgs/sweng-epfl/issues?filter=all&since=2013-11-29T08:59:59Z
; (map #(select-keys % [:title :comments_url]) (:body response))

; What do I need from issues?
; - Labels (a set of strings will do)
; - ID
; - Title
; - Creator
; - Repo ID

(defn parse-issue [issue]
  {:id (:id issue)
   :title (:title issue)
   :user (-> issue :user :login)
   :labels (map :name (:labels issue))
   :repository (-> issue :repository :full_name)
   })  

(defn forall-repos [f]
  (with-open [rdr (io/reader "/home/jowagner/phd/05sweng/sweng-quizapp/Homeworks/Homework6/scripts/sonar_credentials.txt")]
    (doseq [line (line-seq rdr)]
      (let [[name-nice name-lower password] (string/split line #"\s+")]
        (if (not= name-lower "betatest")
          (f (str "sweng-2013-team-" name-lower)))))))

(defn create-hw6-contest-labels
  "Creates Homework6Contest labels in all sweng repos' issue trackers"
  []
  (forall-repos #(p (str "/repos/sweng-epfl/" % "/labels")
                    {:body (client/json-encode {:name "Homework6Contest"
                                                :color "FFFF00"})})))
