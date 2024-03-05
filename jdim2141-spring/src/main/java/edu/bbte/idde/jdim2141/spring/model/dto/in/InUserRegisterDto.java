package edu.bbte.idde.jdim2141.spring.model.dto.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InUserRegisterDto {

    @NotNull
    @Email
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String password;

    private String theme;

    private String lang;
}
