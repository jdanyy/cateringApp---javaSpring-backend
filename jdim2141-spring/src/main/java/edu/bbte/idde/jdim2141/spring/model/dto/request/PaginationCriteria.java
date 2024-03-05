package edu.bbte.idde.jdim2141.spring.model.dto.request;

import static edu.bbte.idde.jdim2141.spring.constants.PaginationConstants.LIMIT;
import static edu.bbte.idde.jdim2141.spring.constants.PaginationConstants.PAGE;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class PaginationCriteria {

    private Integer page = PAGE;

    @Min(1)
    private Integer limit = LIMIT;

}
