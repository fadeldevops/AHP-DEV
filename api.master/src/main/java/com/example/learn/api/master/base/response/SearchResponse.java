package com.example.learn.api.master.base.response;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class SearchResponse<T> {
    @NonNull
    private Integer pageNo;
    @NonNull
    private Integer pageSize;
    @NonNull
    private Integer totalDataInPage;
    @NonNull
    private Long totalData;
    @NonNull
    private Integer totalPages;
    @NonNull
    private List<T> listData;

}
