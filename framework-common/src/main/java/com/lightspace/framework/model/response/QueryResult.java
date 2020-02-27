package com.lightspace.framework.model.response;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class QueryResult<T> {
    private List<T> list;
    private long total;
}