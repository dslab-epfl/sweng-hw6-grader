(ns sweng-hw6-grader.db
  (require [korma.db :refer :all]
           [korma.core :refer :all]
           [clojure.java.jdbc :as sql]
           [clojure.string :as string]))

(if (string/blank? (System/getenv "DATABASE_URL"))
  (throw (IllegalStateException. "Set $DATABASE_URL and other environment vars before starting")))

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

  (defdb db (postgres {:db database_name})))

(declare issues)

(defentity issues
  ; labels are a set, let's convert it to a string on save
  (prepare (fn [issue]
             (update-in issue [:labels] (partial string/join ","))))

  ; labels are read as a string, let's convert it to a set
  (transform (fn [issue]
               (let [convert-to-set (fn [l] (set (filter #(not (string/blank? %)) (string/split l #","))))]
                 (if (contains? issue :labels)
                   (update-in issue [:labels] convert-to-set)
                   issue)))))
