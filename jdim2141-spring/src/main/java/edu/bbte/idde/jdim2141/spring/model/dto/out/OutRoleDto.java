package edu.bbte.idde.jdim2141.spring.model.dto.out;

import edu.bbte.idde.jdim2141.spring.constants.UserRole;
import lombok.Data;

@Data
public class OutRoleDto {

    private Long id;

    private UserRole name;
}
