package edu.bbte.idde.jdim2141.spring.model.dto.out;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorConstraintViolationDto {

    private String message;

    private List<String> violations;

}
