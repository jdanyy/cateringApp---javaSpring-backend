package edu.bbte.idde.jdim2141.spring.model.dto.in;

import edu.bbte.idde.jdim2141.spring.constants.UserRole;
import lombok.Data;

@Data
public class InRole {

    private Long id;

    private UserRole name;

}
