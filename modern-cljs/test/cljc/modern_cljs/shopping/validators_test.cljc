(ns modern-cljs.shopping.validators-test
  (:require [modern-cljs.shopping.validators :refer [validate-shopping-form
                                                     validate-shopping-field]]
            #?(:clj [clojure.test :refer [deftest are testing]]
               :cljs [cljs.test :refer-macros [deftest are testing]])))

(deftest validate-shopping-form-test
  (testing "Shopping Form Validation"
    (testing "/ Happy Path"
      (are [expected actual] (= expected actual)
        nil (validate-shopping-form "1" "0" "0" "0")
        nil (validate-shopping-form "1" "0.0" "0.0" "0.0")
        nil (validate-shopping-form "100" "100.25" "8.25" "123.45")))

    (testing "/ No presence"
      (are [expected actual] (= expected actual)

        "Quantity can't be empty"
        (first (:quantity (validate-shopping-form "" "0" "0" "0")))

        "Price can't be empty"
        (first (:price (validate-shopping-form "1" "" "0" "0")))

        "Tax can't be empty"
        (first (:tax (validate-shopping-form "1" "0" "" "0")))

        "Discount can't be empty"
        (first (:discount (validate-shopping-form "1" "0" "0" "")))))

    (testing "/ Value Type"
      (are [expected actual] (= expected actual)

        "Quantity has to be an integer number"
        (first (:quantity (validate-shopping-form "foo" "0" "0" "0")))

        "Quantity has to be an integer number"
        (first (:quantity (validate-shopping-form "1.1" "0" "0" "0")))

        "Price has to be a number"
        (first (:price (validate-shopping-form "1" "foo" "0" "0")))

        "Tax has to be a number"
        (first (:tax (validate-shopping-form "1" "0" "foo" "0")))

        "Discount has to be a number"
        (first (:discount (validate-shopping-form "1" "0" "0" "foo")))))

    (testing "/Value Range"
      (are [expected actual] (= expected actual)

        "Quantity can't be negative"
        (first (:quantity (validate-shopping-form "-1" "0" "0" "0")))))))

(deftest validate-shopping-field-test
  (testing "Shopping Form Fields Validation"
    (testing "/ Happy Path"
      (are [expected actual] (= expected actual)
        nil (validate-shopping-field :quantity "1")
        nil (validate-shopping-field :price "1.0")
        nil (validate-shopping-field :tax "8.25")
        nil (validate-shopping-field :discount "0.9")))
    ;; presence
    (testing "/ Presence"
      (are [expected actual] (= expected actual)
        "Quantity can't be empty" (validate-shopping-field :quantity "")
        "Quantity can't be empty" (validate-shopping-field :quantity nil)
        "Price can't be empty" (validate-shopping-field :price "")
        "Price can't be empty" (validate-shopping-field :price nil)
        "Tax can't be empty" (validate-shopping-field :tax "")
        "Tax can't be empty" (validate-shopping-field :tax nil)
        "Discount can't be empty" (validate-shopping-field :discount "")
        "Discount can't be empty" (validate-shopping-field :discount nil)))
    ;; type
    (testing "/ Type"
      (are [expected actual] (= expected actual)
        "Quantity has to be an integer number" (validate-shopping-field :quantity "1.1")
        "Price has to be a number" (validate-shopping-field :price "foo")
        "Tax has to be a number" (validate-shopping-field :tax "foo")
        "Discount has to be a number" (validate-shopping-field :discount "foo")))
    ;; range
    (testing "/ Range"
      (are [expected actual] (= expected actual)
        "Quantity can't be negative" (validate-shopping-field :quantity "-1")))))
