package com.shopme.admin.utils.export.csv;

import com.shopme.admin.utils.AbstractExporter;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.UserApp;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CategoryCsvExporter extends AbstractExporter {
    public void export(List<Category> list, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "text/csv", ".csv","categories_");

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] headerWriter = {"Category ID", "Name", "Slug"};
        String[] fieldMapping = {"categoryId", "name", "slug"};

        csvBeanWriter.writeHeader(headerWriter);
        for (Category category: list ) {
            csvBeanWriter.write(category, fieldMapping);
        }
        csvBeanWriter.close();
    }

}
