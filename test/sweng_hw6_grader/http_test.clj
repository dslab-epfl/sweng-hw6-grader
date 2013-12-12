(ns sweng-hw6-grader.http-test
  (:require [sweng-hw6-grader.core :refer [application]]))

(defn make-request
  ([uri] (make-request uri {:request-method :get}))
  ([uri opts]
   (let [request (conj {:uri uri} opts)]
     (application request))))


(def fake-routes {
   
   ; Requesting resources yields the master project, and team-rocket's               
   "https://jenkins.epfl.ch/sonarqube/api/resources"
   (fn [req] {:status 200 :body
     "[{\"id\":3310,\"key\":\"sweng-2013-team-master\",\"name\":\"Master\",\"scope\":\"PRJ\",\"qualifier\":\"TRK\",\"date\":\"2013-12-10T15:46:08+0100\",\"creationDate\":\"2013-12-05T10:16:56+0100\",\"lname\":\"Master\",\"lang\":\"java\",\"version\":\"1.0\"},{\"id\":1781,\"key\":\"sweng-2013-team-rocket\",\"name\":\"Rocket\",\"scope\":\"PRJ\",\"qualifier\":\"TRK\",\"date\":\"2013-12-12T15:35:32+0100\",\"creationDate\":\"2013-12-04T12:24:06+0100\",\"lname\":\"Rocket\",\"lang\":\"java\",\"version\":\"1.0\"}]"})

   "https://jenkins.epfl.ch/sonarqube/api/timemachine?metrics=violations,blocker_violations,critical_violations,major_violations,minor_violations,info_violations,false_positive_issues&resource=sweng-2013-team-master"
   (fn [req] {:status 200 :body
   "[{\"cols\":[{\"metric\":\"violations\"},{\"metric\":\"blocker_violations\"},{\"metric\":\"critical_violations\"},{\"metric\":\"major_violations\"},{\"metric\":\"minor_violations\"},{\"metric\":\"info_violations\"},{\"metric\":\"false_positive_issues\"}],\"cells\":[{\"d\":\"2013-12-05T10:40:39+0100\",\"v\":[1439,6,193,760,453,27,0]},{\"d\":\"2013-12-06T18:37:48+0100\",\"v\":[1419,0,179,760,453,27,0]},{\"d\":\"2013-12-10T15:19:32+0100\",\"v\":[1415,0,179,757,452,27,0]},{\"d\":\"2013-12-10T15:46:08+0100\",\"v\":[1414,0,178,757,452,27,1]}]}]"})

   ; Add a catch-all fake route to enjure tests don't access the network
   #".*" (fn [req] {:status 500 :body ""})})
