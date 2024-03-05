package edu.bbte.idde.jdim2141.spring.model.dto.out;

import lombok.Data;

@Data
public class Pagination {

    private Integer limit;

    private Integer totalPages;

    private Integer pageNum;

    private String next;

    private String prev;
    
}
