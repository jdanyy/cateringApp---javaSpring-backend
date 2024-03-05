package edu.bbte.idde.jdim2141.spring.model.dto.in;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class InDishDto {

    @NotEmpty
    @Size(max = 64)
    private String name;
}
