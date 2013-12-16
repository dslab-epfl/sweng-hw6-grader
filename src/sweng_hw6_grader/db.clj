(ns sweng-hw6-grader.db
  (require [korma.db :refer :all]
           [korma.core :refer :all]
           [clojure.java.jdbc :as sql]))

(let [[database_url database_host database_port database_name]
      (re-matches #"postgresql://([^:]+):(\d+)/(.*)" (System/getenv "DATABASE_URL"))]

  (defn migrate-database
    "Sets up the database schema.
     We store dates as varchars in ISO 8601 format. This is what GitHub uses,
     and converting it is cumbersome."
    []
    (sql/with-connection database_url
      (binding [sql/*as-str* (partial sql/as-quoted-str \")]
        (try (sql/drop-table :issues)
             (catch Exception _))
        (sql/create-table :issues
                          [:id         "integer"     "NOT NULL PRIMARY KEY"]
                          [:title      "text"        "NOT NULL"]
                          [:body       "text"        "NOT NULL"]
                          [:user       "text"        "NOT NULL"]
                          [:labels     "text"        "NOT NULL"]
                          [:repository "text"        "NOT NULL"]
                          [:created_at "varchar(20)" "NOT NULL"]
                          [:updated_at "varchar(20)" "NOT NULL"]))))

  (migrate-database)
  (defdb db (postgres {:db database_name})))

(declare issues)

(defentity issues)
