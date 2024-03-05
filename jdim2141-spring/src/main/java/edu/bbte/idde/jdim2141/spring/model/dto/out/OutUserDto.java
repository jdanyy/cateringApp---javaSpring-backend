package edu.bbte.idde.jdim2141.spring.model.dto.out;

import java.util.List;
import lombok.Data;

@Data
public class OutUserDto {

    private Long id;

    private String name;

    private String email;

    private List<OutRoleDto> roles;

    private String theme;

    private String lang;
}
