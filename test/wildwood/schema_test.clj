(ns wildwood.schema-test
  (:require [clojure.test :refer :all]
            [wildwood.schema :refer :all]))

(deftest required-keys-test
  (testing "Required keys are policed as specified"
    (is (= (required-keys :verb) :verb))
    (is (= (required-keys :subject) :subject))
    (is (= (required-keys :subject) :subject))
    (is (nil? (required-keys 7)))
    (is (nil? (required-keys "Froboz")))
    (is (nil? (required-keys 'Froboz)))
    (is (nil? (required-keys {:verb :kill :subject :brutus :object :caesar}))))
  (testing "Argument keys are not required"
    (map
      #(is (nil? (required-keys %)))
      argument-keys))
  (testing "Consensual keys are not required"
    (map
      #(is (nil? (required-keys %)))
      consensual-keys)))

(deftest proposition-test
  (testing "Propositions are identified correctly"
    (is (proposition? {:verb :kill :subject :brutus :object :caesar}))
    (is (proposition? {:verb :kill :subject :brutus :object :caesar} true)
        "Minimisation tested and found")
    (is (proposition? {:verb :kill :subject :brutus :object :caesar :truth false})
        "Non-required key is allowed")
    (is (= (proposition? {:verb :kill :subject :brutus}) false)
        "Should be false, no :object key")
    (is (proposition? {:verb :kill
               :subject {:id :brutus :name "Marcus Brutus"}
               :object {:id :caesar
                        :name "Gaius Julius Caesar"
                        :wife :drusila}})
        "Unminimised proposition, but minimisation not required")
    (is (= (proposition? {:verb :kill
               :subject {:id :brutus :name "Marcus Brutus"}
               :object {:id :caesar
                        :name "Gaius Julius Caesar"
                        :wife :drusila}} true)
           false)
        "Unminimised proposition, minimisation required")))

(deftest truth-test
  (testing "Correctly distinguishing between assertions and negations"
    (is (truth {:verb :kill :subject :brutus :object :caesar})
        "Implicit assertion")
    (is (truth {:verb :kill :subject :brutus :object :caesar :truth true})
        "Explicit assertion")
    (is (not (truth {:verb :kill :subject :brutus :object :caesar :truth false}))
        "Explicit negation")))

(deftest argument-test
  (testing "Arguments are correctly identified"
    (is (not (argument? {:verb :kill :subject :brutus :object :caesar}))
        "false, lacks :confidence, :authority")
    (is (argument? {:verb :kill
                    :subject :brutus
                    :object :caesar
                    :confidence 0.7
                    :authority :falco})
        "true, all required keys present and :confidence has valid value")
    (is (not (argument? {:verb :kill
                    :subject :brutus
                    :object :caesar
                    :confidence 1.7
                    :authority :falco}))
        "false, all required keys present but :confidence has invalid value")
    (is (argument? {:verb :kill
            :subject {:id :brutus :name "Marcus Brutus"}
            :object {:id :caesar :name "Gaius Julius Caesar" :wife :drusila}
            :confidence 1
            :authority :brutus})
        "true; not minimised but arguments are not required to be")
    (is (not (argument? [:verb :subject :object]))
        "false: not even close (sequence)")
    (is (not (argument? :verb))
        "false: not even close (keyword)")
    (is (not (argument? "Froboz"))
        "false: not even close (string)")
    (is (not (argument? 7))
        "false: not even close (number)")))

(deftest minimise-test
  (testing "Proposition minimisation"
    (is (=
          (minimise {:verb :kill
                     :subject {:id :brutus :name "Marcus Brutus"}
                     :object {:id :caesar :name "Gaius Julius Caesar" :wife :drusila}})
          {:verb :kill :subject :brutus :object :caesar}))
    (is (proposition?
          (minimise {:verb :kill
                     :subject {:id :brutus :name "Marcus Brutus"}
                     :object {:id :caesar :name "Gaius Julius Caesar" :wife :drusila}}))
        "A minimised proposition is still a proposition")
    (is (=
          (minimise {:verb :kill
                     :subject {:id :brutus :name "Marcus Brutus"}
                     :object {:id :caesar :name "Gaius Julius Caesar" :wife :drusila}
                     :confidence 1
                     :authority {:id :brutus :name "Marcus Brutus"}})
          {:verb :kill
           :subject :brutus
           :object :caesar
           :confidence 1
           :authority {:id :brutus :name "Marcus Brutus"}})
        "The value of the :authority key (and other argument keys, actually)
        should not be minimised")
    (is (argument?
          (minimise {:verb :kill
                     :subject {:id :brutus :name "Marcus Brutus"}
                     :object {:id :caesar :name "Gaius Julius Caesar" :wife :drusila}
                     :confidence 1
                     :authority {:id :brutus :name "Marcus Brutus"}}))
        "A minimised argument is still an argument")))



