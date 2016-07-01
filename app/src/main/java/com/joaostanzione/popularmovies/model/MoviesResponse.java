
package com.joaostanzione.popularmovies.model;

import java.util.ArrayList;
import java.util.List;

public class MoviesResponse {

    private Long page;
    private List<Movie> results = new ArrayList<Movie>();
    private Long totalResults;
    private Long totalPages;

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }
}
