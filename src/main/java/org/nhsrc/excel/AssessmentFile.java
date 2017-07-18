package org.nhsrc.excel;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.nhsrc.domain.District;
import org.nhsrc.domain.State;

import java.io.File;
import java.util.StringTokenizer;

public class AssessmentFile {
    private final String facilityName;
    private final String facilityTypeShortName;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private File file;
    private String assessmentToolShortName;
    private static DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("dd-MM-yyyy");

    public AssessmentFile(File assessmentFile) {
        this.file = assessmentFile;
        StringTokenizer stringTokenizer = new StringTokenizer(this.file.getName(), "_.");
        this.assessmentToolShortName = stringTokenizer.nextToken();
        this.facilityTypeShortName = stringTokenizer.nextToken();
        this.facilityName = stringTokenizer.nextToken();
        this.startDate = LocalDate.parse(stringTokenizer.nextToken(), dateTimeFormat);
        this.endDate = LocalDate.parse(stringTokenizer.nextToken(), dateTimeFormat);
    }

    public File getFile() {
        return file;
    }

    public String getAssessmentToolShortName() {
        return assessmentToolShortName;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public String getFacilityTypeShortName() {
        return facilityTypeShortName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}