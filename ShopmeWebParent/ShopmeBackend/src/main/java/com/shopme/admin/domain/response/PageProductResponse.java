package com.shopme.admin.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageProductResponse {
    private String status;
    private long recordCount;
    private Object data;
}
