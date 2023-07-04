package com.shopme.admin.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseObject {
    private String status;
    private String message;
    private Object data;
}
