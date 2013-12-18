(defproject sweng-hw6-grader "0.1.0-SNAPSHOT"
  :description "Computes scores for homework six of sweng.epfl.ch."
  :url "http://sonarqube.epfl.ch/"
  :license {:name "GNU Affero General Public License"
            :url "https://www.gnu.org/licenses/agpl.html"}
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clj-http "0.7.7"]
                 [clj-http-fake "0.4.1"]
                 [compojure "1.1.3"]
                 [hiccup "1.0.4"]
                 [korma "0.3.0-RC6"]
                 [midje "1.6.0"]
                 [org.postgresql/postgresql "9.2-1002-jdbc4"]
                 [ring/ring-jetty-adapter "1.2.1"]]
  :profiles {:dev {:source-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.4"]]}}
  :plugins [[lein-ring "0.8.8"]]
  :ring {:handler sweng-hw6-grader.core/application}
  :main sweng-hw6-grader.core)
