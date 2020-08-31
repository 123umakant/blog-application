package com.mountblue.blogpost.dto;

public class SearchAndSortFields {
    private int page;
    private String sort;
    private String publishDate;
    private String search;
    private String tagSearch;
    private String tagSearchId;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getTagSearch() {
        return tagSearch;
    }

    public void setTagSearch(String tagSearch) {
        this.tagSearch = tagSearch;
    }

    public String getTagSearchId() {
        return tagSearchId;
    }

    public void setTagSearchId(String tagSearchId) {
        this.tagSearchId = tagSearchId;
    }
}
