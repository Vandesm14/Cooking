(defproject cooking "0.1.0-SNAPSHOT"
  :description "A new way to look at cooking, baking, and the related arts."
  :url "https://github.com/vandesm14/cooking"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"] [org.clojure/data.json "2.4.0"]]
  :repl-options {:init-ns cooking.core}
  :main cooking.core)