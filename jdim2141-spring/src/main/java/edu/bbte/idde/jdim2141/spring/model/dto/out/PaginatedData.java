package edu.bbte.idde.jdim2141.spring.model.dto.out;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedData<T> {

    private Collection<T> content;

    private Pagination pagination;

}
