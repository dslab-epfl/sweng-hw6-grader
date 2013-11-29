(defproject sweng-hw6-grader "0.1.0-SNAPSHOT"
  :description "Computes scores for homework six of sweng.epfl.ch."
  :url "http://sonarqube.epfl.ch/"
  :license {:name "GNU Affero General Public License"
            :url "https://www.gnu.org/licenses/agpl.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [korma "0.3.0-RC5"]
                 [org.postgresql/postgresql "9.2-1002-jdbc4"]
                 [ring/ring-jetty-adapter "1.2.1"]
                 [compojure "1.1.3"]
                 [clj-http "0.7.7"]])
