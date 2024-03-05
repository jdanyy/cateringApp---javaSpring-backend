package edu.bbte.idde.jdim2141.spring.model.dto.in;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;

@Data
public class InMenuDto {

    @NotNull
    @Positive
    private Double price;

    @NotEmpty
    private List<InMenuDishDto> dishes;

    @NotNull
    @Size(max = 256)
    private String menuTitle;

    @Positive
    private Float rating;

    @Positive
    private Integer calories;

}
