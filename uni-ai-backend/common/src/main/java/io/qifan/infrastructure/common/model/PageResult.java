package io.qifan.infrastructure.common.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class PageResult<T> {
    private long totalElements;
    private int totalPages;
    private int size;
    private int number;
    private List<T> content;
}
