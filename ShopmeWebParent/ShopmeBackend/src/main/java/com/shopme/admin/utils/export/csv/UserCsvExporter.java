package com.shopme.admin.utils.export.csv;

import com.shopme.admin.utils.AbstractExporter;
import com.shopme.common.entity.UserApp;
import javax.servlet.http.HttpServletResponse;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import java.io.IOException;
import java.util.List;

public class UserCsvExporter  extends AbstractExporter {
    public void export(List<UserApp> userAppList, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "text/csv", ".csv","users_");

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] headerWriter = {"User ID", "E-mail", "Firstname", "Lastname", "Roles", "Enabled"};
        String[] fieldMapping = {"userId", "email", "firstName", "lastName", "roles", "enabled"};

        csvBeanWriter.writeHeader(headerWriter);
        for (UserApp user: userAppList ) {
            csvBeanWriter.write(user, fieldMapping);
        }
        csvBeanWriter.close();
    }

}
