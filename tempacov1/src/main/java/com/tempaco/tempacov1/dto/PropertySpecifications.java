package com.tempaco.tempacov1.dto;

import com.tempaco.tempacov1.model.Property;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class PropertySpecifications {

    public static Specification<Property> hasLocation(String location) {
        return (root, query, builder) ->
                location == null ? builder.conjunction() : builder.equal(root.get("address"), location);
    }

    public static Specification<Property> hasMinPrice(Double minPrice) {
        return (root, query, builder) ->
                minPrice == null ? builder.conjunction() : builder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Property> hasMaxPrice(Double maxPrice) {
        return (root, query, builder) ->
                maxPrice == null ? builder.conjunction() : builder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Property> hasBed(Integer bed) {
        return (root, query, builder) ->
                bed == null ? builder.conjunction() : builder.equal(root.get("bed"), bed);
    }

    public static Specification<Property> hasBath(Double bath) {
        return (root, query, builder) ->
                bath == null ? builder.conjunction() : builder.equal(root.get("bath"), bath);
    }

    public static Specification<Property> hasMoveInDate(Date moveInDate) {
        return (root, query, builder) ->
                moveInDate == null ? builder.conjunction() : builder.greaterThanOrEqualTo(root.get("moveInDate"), moveInDate);
    }

    public static Specification<Property> hasMoveOutDate(Date moveOutDate) {
        return (root, query, builder) ->
                moveOutDate == null ? builder.conjunction() : builder.lessThanOrEqualTo(root.get("moveOutDate"), moveOutDate);
    }
}
