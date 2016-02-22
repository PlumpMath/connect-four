(ns connect-four.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [connect-four.core-test]))

(doo-tests 'connect-four.core-test)
