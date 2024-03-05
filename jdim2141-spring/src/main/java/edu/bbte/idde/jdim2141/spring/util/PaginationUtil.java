package edu.bbte.idde.jdim2141.spring.util;

import edu.bbte.idde.jdim2141.spring.model.dto.out.Pagination;
import edu.bbte.idde.jdim2141.spring.model.dto.request.SortCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

@Slf4j
public final class PaginationUtil {

    private PaginationUtil() {
    }

    public static <T> Pagination extractPageData(Page<T> data, String url, SortCriteria sort) {
        var limit = data.getSize();

        var nextPage = data.getNumber() + 1;
        var prevPage = data.getNumber() - 1;

        var pagination = new Pagination();
        pagination.setLimit(limit);
        pagination.setTotalPages(data.getTotalPages());
        pagination.setPageNum(data.getNumber());
        pagination.setNext(data.hasNext() ? buildPageUrl(nextPage, limit, sort, url) : "");
        pagination.setPrev(data.hasPrevious() ? buildPageUrl(prevPage, limit, sort, url) : "");

        return pagination;
    }

    private static String buildPageUrl(Integer offset, Integer limit, SortCriteria sort,
        String url) {
        return String.format("%s?page=%d&limit=%d&sortBy=%s&order=%s", url, offset, limit,
            sort.getSortBy(), sort.getOrder());
    }

}
