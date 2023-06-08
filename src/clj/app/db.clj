(ns app.db
  (:require
   [clojure.java.jdbc :as j]
   [honey.sql :as sql]
   [honey.sql.helpers :as h]
   [app.env :refer [env]]))

(def mysql-db {:dbtype "mysql"
               :host (env :HOST)
               :dbname (env :DATABASE)
               :user (env :USERNAME)
               :password (env :PASSWORD)})

(defn query [q]
  (j/query mysql-db q))

(defn insert! [q]
  (j/db-do-prepared mysql-db q))

(defn insert-post! [title text]
  (insert!
   (-> (h/insert-into :post)
       (h/columns :title :text :date)
       (h/values [[title text (java.time.LocalDateTime/now)]])
       (sql/format))))

(defn get-posts []
  (query
   (-> (h/select :title :text :date)
       (h/from :post)
       (sql/format))))

(comment
  (insert-post! "Sixth post" "This is markdown text.\\n===fanc===\\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\no\\nono\\no\\no"))
