package edu.bbte.idde.jdim2141.spring.constants;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

@Component
public class PaginationConstants {

    public static final String SORT_BY = "id";
    public static final Sort.Direction SORT_DIRECTION = Direction.ASC;
    public static final Integer PAGE = 0;
    public static final Integer LIMIT = 4;
}
