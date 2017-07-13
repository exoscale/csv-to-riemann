(defproject csv-to-riemann "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main csv-to-riemann.core
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [riemann-clojure-client "0.4.5"]
                 [org.clojure/data.csv "0.1.4"]
                 [org.clojure/tools.cli "0.3.5"]])
