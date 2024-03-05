package edu.bbte.idde.jdim2141.spring.model.jwt;

import java.util.Collection;
import lombok.Data;

@Data
public class JwtClaims {

    private String name;

    private String email;

    private Collection<String> roles;

}
