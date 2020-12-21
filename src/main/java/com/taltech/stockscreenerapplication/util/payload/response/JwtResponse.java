package com.taltech.stockscreenerapplication.util.payload.response;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private List<String> roles;
    private Set<CompanyDimension> companyDimensions;

    public JwtResponse(final String accessToken, final Long id, final String username, final String firstName, final String lastName, final List<String> roles,
                       final Set<CompanyDimension> companyDimensions) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
        this.companyDimensions = companyDimensions;
    }

}
