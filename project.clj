(defproject wildwood "0.1.0-SNAPSHOT"
  :description "A general inference library using a game theoretic inference mechanism."
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 [com.taoensso/timbre "4.10.0"]
                 [com.novemberain/monger "3.1.0"]
                 [prismatic/schema "1.1.12"]]
  :codox {:metadata {:doc "**TODO**: write docs"
                     :doc/format :markdown}
          :output-path "docs/codox"
          :source-uri "https://github.com/simon-brooke/the-great-game/blob/master/{filepath}#L{line}"}
  :plugins [[lein-cloverage "1.1.1"]
            [lein-codox "0.10.7"]
            [lein-cucumber "1.0.2"]
            [lein-gorilla "0.4.0"]]
  :repl-options {:init-ns wildwood.core})
