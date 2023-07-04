package com.shopme.admin.utils;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AbstractExporter {
    protected void setResponseHeader(HttpServletResponse response, String contentType, String extension, String type){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timestamp = dateFormat.format(new Date());
        String fileName = type + timestamp + extension;

        response.setContentType(contentType);
        response.setCharacterEncoding("UTF-8"); //
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey,  headerValue);
    }
}
