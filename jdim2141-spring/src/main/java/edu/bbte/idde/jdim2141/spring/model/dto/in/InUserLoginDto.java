package edu.bbte.idde.jdim2141.spring.model.dto.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InUserLoginDto {

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

}
