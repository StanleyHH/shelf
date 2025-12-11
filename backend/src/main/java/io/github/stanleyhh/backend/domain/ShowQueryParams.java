package io.github.stanleyhh.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowQueryParams {
    String search;
    String genreName;
    String countryName;
    String status;
    Integer year;
}