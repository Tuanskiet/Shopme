package com.shopme.site;

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
