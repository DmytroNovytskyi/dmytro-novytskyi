package com.epam.spring.homework6.util;

import org.springframework.data.domain.*;

import java.util.List;

public class CommonTestData {

    public static final int PAGE = 0;
    public static final int SIZE = 2;
    public static final String SORT_BY = "id";
    public static final String ORDER = "asc";

    public static Pageable createPageable() {
        return PageRequest.of(PAGE, SIZE, Sort.by(SORT_BY).ascending());
    }

    public static <T> Page<T> createPage(List<T> data) {
        return new PageImpl<T>(data, createPageable(), data.size());
    }

}
