package edu.bbte.idde.jdim2141.spring.model.dto.request;

import static edu.bbte.idde.jdim2141.spring.constants.PaginationConstants.SORT_BY;
import static edu.bbte.idde.jdim2141.spring.constants.PaginationConstants.SORT_DIRECTION;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class SortCriteria {

    private Sort.Direction order = SORT_DIRECTION;

    private String sortBy = SORT_BY;

}
