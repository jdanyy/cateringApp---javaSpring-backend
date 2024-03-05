package edu.bbte.idde.jdim2141.spring.model.dto.in;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InMenuDishDto {

    @NotNull
    private Long id;

}
