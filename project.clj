(defproject wildwood "0.1.0-SNAPSHOT"
  :description "A general inference library using a game theoretic inference mechanism."
  :url "http://example.com/FIXME"
  :license {:name "GNU General Public License,version 2.0 or (at your option) any later version"
            :url "https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html"}
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
