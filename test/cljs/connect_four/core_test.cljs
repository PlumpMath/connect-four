(ns connect-four.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [connect-four.core :as core]))

(deftest fake-test
  (testing "fake description"
    (is (= 1 2))))
