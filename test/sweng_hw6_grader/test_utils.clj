(ns sweng-hw6-grader.test-utils
  (:require [midje.sweet :refer :all]
            [clj-http.fake :as fake]
            [sweng-hw6-grader.core :refer [application]]
            [sweng-hw6-grader.db :as db]))

(defn make-request
  ([uri] (make-request uri {:request-method :get}))
  ([uri opts]
   (let [request (conj {:uri uri} opts)]
     (application request))))


(def fake-routes {

   ; Requesting resources yields the master project, and team-rocket's
   "https://jenkins.epfl.ch/sonarqube/api/resources"
   (fn [req] {:status 200 :body
     "[{\"id\":3310,
        \"key\":\"sweng-2013-team-master\",
        \"name\":\"Master\",
        \"scope\":\"PRJ\",
        \"qualifier\":\"TRK\",
        \"date\":\"2013-12-10T15:46:08+0100\",
        \"creationDate\":\"2013-12-05T10:16:56+0100\",
        \"lname\":\"Master\",
        \"lang\":\"java\",
        \"version\":\"1.0\"},
        {\"id\":1781,
        \"key\":\"sweng-2013-team-rocket\",
        \"name\":\"Rocket\",
        \"scope\":\"PRJ\",
        \"qualifier\":\"TRK\",
        \"date\":\"2013-12-12T15:35:32+0100\",
        \"creationDate\":\"2013-12-04T12:24:06+0100\",
        \"lname\":\"Rocket\",
        \"lang\":\"java\",
        \"version\":\"1.0\"}]"})

   "https://jenkins.epfl.ch/sonarqube/api/timemachine?metrics=violations,blocker_violations,critical_violations,major_violations,minor_violations,info_violations,false_positive_issues&resource=sweng-2013-team-master"
   (fn [req] {:status 200 :body
   "[{\"cols\":[{\"metric\":\"violations\"},
                {\"metric\":\"blocker_violations\"},
                {\"metric\":\"critical_violations\"},
                {\"metric\":\"major_violations\"},
                {\"metric\":\"minor_violations\"},
                {\"metric\":\"info_violations\"},
                {\"metric\":\"false_positive_issues\"}],
      \"cells\":[{\"d\":\"2013-12-05T10:40:39+0100\",\"v\":[1439,6,193,760,453,27,0]},
                {\"d\":\"2013-12-06T18:37:48+0100\",\"v\":[1419,0,179,760,453,27,0]},
                {\"d\":\"2013-12-10T15:19:32+0100\",\"v\":[1415,0,179,757,452,27,0]},
                {\"d\":\"2013-12-10T15:46:08+0100\",\"v\":[1414,0,178,757,452,27,1]}]}]"})

    "https://api.github.com/orgs/sweng-epfl/issues?filter=all&since=2013-12-15T23:04:11Z"
    (fn [req] {:status 200 :body
    "[
      {
        \"url\": \"https://api.github.com/repos/sweng-epfl/sweng-2013-team-master/issues/36\",
        \"id\": 24321855,
        \"number\": 36,
        \"title\": \"Homework6Contest: This is an updated issue title\",
        \"user\": {
          \"login\": \"SomeStudent\",
          \"id\": 5492975,
          \"url\": \"https://api.github.com/users/SomeStudent\",
          \"type\": \"User\",
          \"site_admin\": false
        },
        \"labels\": [

        ],
        \"state\": \"closed\",
        \"assignee\": null,
        \"milestone\": null,
        \"comments\": 0,
        \"created_at\": \"2013-12-15T23:04:11Z\",
        \"updated_at\": \"2013-12-19T17:00:00Z\",
        \"closed_at\": \"2013-12-19T17:00:00Z\",
        \"pull_request\": {
          \"html_url\": null,
          \"diff_url\": null,
          \"patch_url\": null
        },
        \"repository\": {
          \"id\": 12952457,
          \"name\": \"sweng-2013-team-master\",
          \"full_name\": \"sweng-epfl/sweng-2013-team-master\",
          \"owner\": {
            \"login\": \"sweng-epfl\",
            \"id\": 2575063,
            \"type\": \"Organization\",
            \"site_admin\": false
          },
          \"private\": true,
          \"description\": \"\",
          \"fork\": false,
          \"url\": \"https://api.github.com/repos/sweng-epfl/sweng-2013-team-master\",
          \"created_at\": \"2013-09-19T15:46:54Z\",
          \"updated_at\": \"2013-12-14T18:18:08Z\",
          \"pushed_at\": \"2013-12-14T18:18:05Z\",
          \"homepage\": \"\",
          \"size\": 2236,
          \"stargazers_count\": 0,
          \"watchers_count\": 0,
          \"language\": \"Java\",
          \"has_issues\": true,
          \"has_downloads\": true,
          \"has_wiki\": true,
          \"forks_count\": 0,
          \"mirror_url\": null,
          \"open_issues_count\": 9,
          \"forks\": 0,
          \"open_issues\": 9,
          \"watchers\": 0,
          \"default_branch\": \"master\",
          \"master_branch\": \"master\"
        },
        \"body\": \"The body has changed, too\"
      }]"})

    "https://api.github.com/orgs/sweng-epfl/issues?filter=all&since=2013-12-13T18:00:00Z"
    (fn [req] {:status 200 :body
    "[
      {
        \"url\": \"https://api.github.com/repos/sweng-epfl/sweng-2013-team-master/issues/36\",
        \"id\": 24321855,
        \"number\": 36,
        \"title\": \"Homework6Contest: This is an issue title\",
        \"user\": {
          \"login\": \"SomeStudent\",
          \"id\": 5492975,
          \"url\": \"https://api.github.com/users/SomeStudent\",
          \"type\": \"User\",
          \"site_admin\": false
        },
        \"labels\": [

        ],
        \"state\": \"open\",
        \"assignee\": null,
        \"milestone\": null,
        \"comments\": 0,
        \"created_at\": \"2013-12-15T23:04:11Z\",
        \"updated_at\": \"2013-12-15T23:04:11Z\",
        \"closed_at\": null,
        \"pull_request\": {
          \"html_url\": null,
          \"diff_url\": null,
          \"patch_url\": null
        },
        \"repository\": {
          \"id\": 12952457,
          \"name\": \"sweng-2013-team-master\",
          \"full_name\": \"sweng-epfl/sweng-2013-team-master\",
          \"owner\": {
            \"login\": \"sweng-epfl\",
            \"id\": 2575063,
            \"type\": \"Organization\",
            \"site_admin\": false
          },
          \"private\": true,
          \"description\": \"\",
          \"fork\": false,
          \"url\": \"https://api.github.com/repos/sweng-epfl/sweng-2013-team-master\",
          \"created_at\": \"2013-09-19T15:46:54Z\",
          \"updated_at\": \"2013-12-14T18:18:08Z\",
          \"pushed_at\": \"2013-12-14T18:18:05Z\",
          \"homepage\": \"\",
          \"size\": 2236,
          \"stargazers_count\": 0,
          \"watchers_count\": 0,
          \"language\": \"Java\",
          \"has_issues\": true,
          \"has_downloads\": true,
          \"has_wiki\": true,
          \"forks_count\": 0,
          \"mirror_url\": null,
          \"open_issues_count\": 9,
          \"forks\": 0,
          \"open_issues\": 9,
          \"watchers\": 0,
          \"default_branch\": \"master\",
          \"master_branch\": \"master\"
        },
        \"body\": \"Some text...\"
      },
      {
          \"url\": \"https://api.github.com/repos/sweng-epfl/sweng-2013-team-master/issues/35\",
          \"id\": 24293198,
          \"number\": 35,
          \"title\": \"Homework6Contest - wrong delimiter in editQuestion Tag\",
          \"user\": {
            \"login\": \"SomeStudent\",
            \"id\": 5485740,
            \"url\": \"https://api.github.com/users/SomeStudent\",
            \"type\": \"User\",
            \"site_admin\": false
          },
          \"labels\": [
            {
              \"url\": \"https://api.github.com/repos/sweng-epfl/sweng-2013-team-master/labels/Homework6Contest\",
              \"name\": \"Homework6Contest\",
              \"color\": \"FFFF00\"
            }
          ],
          \"state\": \"open\",
          \"assignee\": null,
          \"milestone\": null,
          \"comments\": 8,
          \"created_at\": \"2013-12-14T14:42:48Z\",
          \"updated_at\": \"2013-12-15T12:33:48Z\",
          \"closed_at\": null,
          \"pull_request\": {
            \"html_url\": null,
            \"diff_url\": null,
            \"patch_url\": null
          },
          \"repository\": {
            \"id\": 12952457,
            \"name\": \"sweng-2013-team-master\",
            \"full_name\": \"sweng-epfl/sweng-2013-team-master\",
            \"owner\": {
              \"login\": \"sweng-epfl\",
              \"id\": 2575063,
              \"type\": \"Organization\",
              \"site_admin\": false
            },
            \"private\": true,
            \"html_url\": \"https://github.com/sweng-epfl/sweng-2013-team-master\",
            \"description\": \"\",
            \"fork\": false,
            \"url\": \"https://api.github.com/repos/sweng-epfl/sweng-2013-team-master\",
            \"created_at\": \"2013-09-19T15:46:54Z\",
            \"updated_at\": \"2013-12-14T18:18:08Z\",
            \"pushed_at\": \"2013-12-14T18:18:05Z\",
            \"homepage\": \"\",
            \"size\": 2236,
            \"stargazers_count\": 0,
            \"watchers_count\": 0,
            \"language\": \"Java\",
            \"has_issues\": true,
            \"has_downloads\": true,
            \"has_wiki\": true,
            \"forks_count\": 0,
            \"mirror_url\": null,
            \"open_issues_count\": 9,
            \"forks\": 0,
            \"open_issues\": 9,
            \"watchers\": 0,
            \"default_branch\": \"master\",
            \"master_branch\": \"master\"
          },
          \"body\": \"when I put in the tag field dwrtçhtrf the tag is not dwrt,
                      htrf but the tag dwrtçhtrf in offline mode.\"
      }
    ]"})})

(defmacro with-fixtures
  "Runs a test with fake routes, and with a clean database"
  [ & body ]
  `(fake/with-fake-routes-in-isolation fake-routes
    (with-state-changes [(before :facts (db/migrate-database))]
      ~@body)))
