package edu.bbte.idde.jdim2141.spring.model.dto.out;

import java.util.List;
import lombok.Data;

@Data
public class OutMenuDto {

    private Long id;

    private Double price;

    private List<OutDishDto> dishes;

    private String menuTitle;

    private Float rating;

    private Integer calories;
}
