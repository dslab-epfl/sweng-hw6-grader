(ns sweng-hw6-grader.core
  (:require [clj-http.client :as client]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))


(defn tidbits []
  ; Getting an OAuth2 token:
  (client/post "https://api.github.com/authorizations"
               {:basic-auth ["Sjlver" "2wXVf3EBuySH"]
                :body (client/json-encode {:note "SwEng HW6 Grader"
                                           :scopes ["repo"]})
                :debug true})

  ; Using the OAuth token
  (client/get "https://api.github.com/user"
              {:oauth-token "72ba628e6c0e29ecb5a8c36e61d611048bcbb442" :debug true})

  ; Helper for easier getting; adds Github prefix and OAuth token automatically
  ; Create a github helper
  (defn github-helper [method]
    (fn helper 
      ([url] (helper url {}))
      ([url params]
       (let [full-url (str "https://api.github.com" url)
             user-agent {"User-Agent" "SwEng HW6 Grader"}
             full-params (conj {:oauth-token "c51e6e73fe7bf706942ef41eda1a10435d130027"
                                :as :json
                                :headers user-agent} params)
             response (method full-url full-params)]
         (pprint response)
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
    (with-open [rdr (reader "/home/jowagner/phd/05sweng/sweng-quizapp/Homeworks/Homework6/scripts/sonar_credentials.txt")]
      (doseq [line (line-seq rdr)]
        (let [[name-nice name-lower password] (split line #"\s+")]
          (if (not= name-lower "betatest")
            (f (str "sweng-2013-team-" name-lower)))))))

  (forall-repos #(p (str "/repos/sweng-epfl/" % "/labels")
                    {:body (client/json-encode {:name "Homework6Contest"
                                                :color "FFFF00"})}))

)
