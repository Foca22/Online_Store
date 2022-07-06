package com.project.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    private Integer id;

    private String firstName;

    private String lastName;

    private String cnp;

    private String email;
}
