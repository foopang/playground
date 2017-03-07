(ns modern-cljs.shopping.validators
  (:require [valip.core :refer [validate]]
            [valip.predicates :refer [present?
                                      integer-string?
                                      decimal-string?
                                      gt]]))

(defn validate-shopping-form [quantity price tax discount]
  (validate {:quantity quantity :price price :tax tax :discount discount}

            ;; validate presence

            [:quantity present? "Quantity can't be empty"]
            [:price present? "Price can't be empty"]
            [:tax present? "Tax can't be empty"]
            [:discount present? "Discount can't be empty"]

            ;; Validate type

            [:quantity integer-string? "Quantity has to be an integer number"]
            [:price decimal-string? "Price has to be a number"]
            [:tax decimal-string? "Tax has to be a number"]
            [:discount decimal-string? "Discount has to be a number"]

            ;; validate range
            [:quantity (gt 0) "Quantity can't be negative"]))

(defn validate-shopping-field [field value]
  (case field
    :quantity (first (:quantity (validate-shopping-form value "0" "0" "0")))
    :price (first (:price (validate-shopping-form "1" value "0" "0")))
    :tax (first (:tax (validate-shopping-form "1" "0" value "0")))
    :discount (first (:discount (validate-shopping-form "1" "0" "0" value)))))
