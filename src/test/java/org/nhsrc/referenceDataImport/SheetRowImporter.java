package org.nhsrc.referenceDataImport;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.nhsrc.domain.*;
import org.nhsrc.repository.AreaOfConcernRepository;
import org.nhsrc.repository.MeasurableElementRepository;
import org.nhsrc.repository.StandardRepository;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SheetRowImporter {
    private Standard currStandard;
    private MeasurableElement currME;
    private AreaOfConcern currAOC;
    private AssessmentChecklistData data;
    private static Pattern mePattern = Pattern.compile("^([a-zA-Z][0-9]+\\.[0-9]+)(.*)");
    private static Pattern standardPattern = Pattern.compile("^([a-zA-Z][0-9]+)(.*)");

    public SheetRowImporter(AssessmentChecklistData data) {
        this.data = data;
    }

    private String getCleanedRef(String cell, Pattern pattern, String replacement) {
        String ref = cell.replace(replacement, "").trim();
        ref = ref.replaceAll("\\s", "");
        Matcher matcher = pattern.matcher(ref);
        String cleanedRef = ref;
        if (matcher.find()) {
            cleanedRef = matcher.group(1);
        }
        return cleanedRef;
    }

    private String getMERef(String cellText) {
        return getCleanedRef(cellText, mePattern, "ME");
    }

    private String getStandardRef(String cell) {
        return getCleanedRef(cell, standardPattern, "Standard");
    }

    public void importRow(Row currentRow, State state, Checklist checklist) {
        if (isEmpty(currentRow, 0) && isEmpty(currentRow, 1) && isEmpty(currentRow, 2)) {
        } else if (!getText(currentRow, 1).startsWith("Area of Concern - ") && currAOC == null) {
        } else if (getText(currentRow, 1).startsWith("Area of Concern - ")) {
            this.aoc(currentRow, checklist);
        } else if (getText(currentRow, 0).startsWith("Standard ") && getText(currentRow, 0).length() <= 15) {
            this.standard(getText(currentRow, 0), getText(currentRow, 1));
        } else if (getText(currentRow, 0).startsWith("ME") && isEmpty(currentRow, 2)) {
        } else if (getText(currentRow, 0).startsWith("ME")) {
            this.me(currentRow, state, checklist);
        } else if (getText(currentRow, 0).replace(" ", "").startsWith(this.currStandard.getReference()) && checklist.getName().equals("Kayakalp")) {
            this.kayakalpME(currentRow, state, checklist);
        } else if (!isEmpty(currentRow, 2) && !checklist.getName().equals("Kayakalp")) {
            this.checkpoint(currentRow, state, checklist);
        }
    }

    private void kayakalpME(Row currentRow, State state, Checklist checklist) {
        String ref = getText(currentRow, 0).replace(" ", "");
        String name = getText(currentRow, 1).trim();
        MeasurableElement me = currStandard.getMeasurableElement(ref);
        if (me == null) {
            me = new MeasurableElement();
            me.setName(name);
            me.setReference(ref);
            currStandard.addMeasurableElement(me);
        }
        currME = me;

        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setName(name);
        String am = getText(currentRow, 3);
        checkpoint.setAssessmentMethodObservation(am.toLowerCase().contains("ob"));
        checkpoint.setAssessmentMethodPatientInterview(am.toLowerCase().contains("pi"));
        checkpoint.setAssessmentMethodRecordReview(am.toLowerCase().contains("rr"));
        checkpoint.setAssessmentMethodStaffInterview(am.toLowerCase().contains("si"));
        checkpoint.setDefault(true);
        checkpoint.setState(state);
        checkpoint.setMeasurableElement(currME);
        currME.addCheckpoint(checkpoint);
        checklist.addCheckpoint(checkpoint);
    }

    private String getCellValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        if (cellType == CellType.NUMERIC) return "" + cell.getNumericCellValue();
        else if (cellType == CellType.FORMULA) return "";
        else return cell.getStringCellValue();
    }

    private boolean isEmpty(Row row, int cellnum) {
        Cell cell = row.getCell(cellnum);
        if (cell == null) return true;

        String stringCellValue = getCellValue(cell);
        if (stringCellValue == null || stringCellValue.trim().equals("")) return true;

        return false;
    }

    private String getText(Row row, int cellnum) {
        Cell cell = row.getCell(cellnum);
        if (cell == null) return "";
        String stringCellValue = getCellValue(cell);
        return stringCellValue.trim();
    }

    private void checkpoint(Row row, State state, Checklist checklist) {
        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setName(getText(row, 2));
        String am = getText(row, 4);
        checkpoint.setAssessmentMethodObservation(am.toLowerCase().contains("ob"));
        checkpoint.setAssessmentMethodPatientInterview(am.toLowerCase().contains("pi"));
        checkpoint.setAssessmentMethodRecordReview(am.toLowerCase().contains("rr"));
        checkpoint.setAssessmentMethodStaffInterview(am.toLowerCase().contains("si"));
        checkpoint.setDefault(true);
        checkpoint.setState(state);
        checkpoint.setMeasurableElement(currME);
        if (!getText(row, 5).equals("")) {
            checkpoint.setMeansOfVerification(getText(row, 5));
        }
        currME.addCheckpoint(checkpoint);
        checklist.addCheckpoint(checkpoint);
    }

    private void me(Row row, State state, Checklist checklist) {
        String ref = getMERef(getText(row, 0));
        String name = getText(row, 1).trim();
        MeasurableElement me = currStandard.getMeasurableElement(ref);
        if (me == null) {
            me = new MeasurableElement();
            me.setName(name);
            me.setReference(ref);
            currStandard.addMeasurableElement(me);
        }
        currME = me;
        checkpoint(row, state, checklist);
    }

    private void standard(String standardRefCellText, String standardNameCellText) {
        String ref = getStandardRef(standardRefCellText);

        Standard standard = currAOC.getStandard(ref);
        if (standard == null) {
            standard = new Standard();
            standard.setReference(ref);
            standard.setName(standardNameCellText);
            currAOC.addStandard(standard);
        }
        currStandard = standard;
    }

    private void aoc(Row row, Checklist checklist) {
        String aocRefName = row.getCell(1).getStringCellValue().replace("Area of Concern - ", "");
        String ref = aocRefName.substring(0, 1);
        String name = aocRefName.substring(2).trim();

        AreaOfConcern areaOfConcern = data.findAreaOfConcern(ref);
        if (areaOfConcern == null) {
            areaOfConcern = new AreaOfConcern();
            areaOfConcern.setName(name);
            areaOfConcern.setReference(ref);
            data.add(areaOfConcern);
        }
        checklist.addAreaOfConcern(areaOfConcern);
        currAOC = areaOfConcern;
    }
}