package com.shopme.admin.utils.export.csv;

import com.shopme.admin.utils.AbstractExporter;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.ProductType;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProductTypeCsvExporter extends AbstractExporter {
    public void export(List<ProductType> list, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "text/csv", ".csv","type_product_");

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] headerWriter = {"ID", "Name", "Slug"};
        String[] fieldMapping = {"productTypeId", "name", "slug"};

        csvBeanWriter.writeHeader(headerWriter);
        for (ProductType productType: list ) {
            csvBeanWriter.write(productType, fieldMapping);
        }
        csvBeanWriter.close();
    }

}
