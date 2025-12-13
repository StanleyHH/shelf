package io.github.stanleyhh.backend.domain.specifications;

import io.github.stanleyhh.backend.domain.entities.Show;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.springframework.data.jpa.domain.Specification;

public class ShowSpecs {

    private ShowSpecs() {
    }

    public static Specification<Show> containsText(@NotBlank String search) {
        String pattern = "%" + search.toLowerCase().trim() + "%";

        return (root, query, cb) ->
                cb.like(cb.lower(root.get("title")), pattern);
    }

    public static Specification<Show> hasGenre(@NotBlank String genreName) {
        return (root, query, cb) ->
                cb.equal(cb.lower(root.join("genres").get("name")), genreName.trim().toLowerCase());
    }

    public static Specification<Show> hasCountry(@NotBlank String countryName) {
        return (root, query, cb) ->
                cb.equal(cb.lower(root.join("countries").get("name")), countryName.trim().toLowerCase());
    }

    public static Specification<Show> hasStatus(@NotBlank String status) {
        return (root, query, cb) ->
                cb.equal(root.get("status"), status.trim().toUpperCase());
    }

    public static Specification<Show> startedInYear(Integer year) {
        return (root, query, cb) -> {
            HibernateCriteriaBuilder hcb = (HibernateCriteriaBuilder) cb;
            return cb.equal(hcb.year(root.get("firstAirDate")), year);
        };
    }
}