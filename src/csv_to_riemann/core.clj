(ns csv-to-riemann.core
  (:require [riemann.client :as r]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [clojure.pprint]
            [clojure.tools.cli :refer [parse-opts]])
  (:gen-class))

(def cli-options
  [["-csv" "--csv CSV" "path to CSV file"]
   ["-r" "--riemann-server" "Riemann host/destination of parsed CSV files"]
   ["-h" "--help"]])

(defn csv-data->maps [csv-data]
  (map zipmap
       (->> (first csv-data) ;; First row is the header
            (map keyword)
            repeat)
       (rest csv-data)))

(defn -main [& args]
  (let [parsed-args (:arguments (parse-opts args cli-options))
        csv-path (first parsed-args)
        riemann-host (second parsed-args)
        c (r/tcp-client {:host (or riemann-host "127.0.0.1")})]
    (with-open [reader (io/reader (or csv-path "/tmp/archv/w38-ppsos-sample/s3-main.csv"))]
     (doall (for [el (csv-data->maps (csv/read-csv reader))]
              (do (println "sending: " el)
                  (-> c (r/send-event el)
                        (deref 5000 ::timeout))
                  @(r/query c "state = \"ok\"")))))))