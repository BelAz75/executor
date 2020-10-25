package com.virtuallab.util.rest;

import java.util.ArrayList;
import java.util.List;

public class PageResponse<T> {
    private int currentPage;
    private int pageSize;
    private int totalPages;
    private long resultsCount;
    private List<T> content = new ArrayList<>();

    public PageResponse(int currentPage, int pageSize, int totalPages, long resultsCount, List<T> content) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.resultsCount = resultsCount;
        this.content = content;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getResultsCount() {
        return resultsCount;
    }

    public void setResultsCount(long resultsCount) {
        this.resultsCount = resultsCount;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
