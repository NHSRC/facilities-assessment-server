package org.nhsrc.referenceDataImport;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.nhsrc.domain.*;

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
        String ref = cell.replace(replacement, "").trim().replaceAll(" +", " ");
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

    public boolean importRow(Row currentRow, Checklist checklist, boolean hasScores, FacilityAssessment facilityAssessment) {
        if (isEmpty(currentRow, 0) && isEmpty(currentRow, 1) && isEmpty(currentRow, 2)) {
        } else if (!isAreaOfConcernRow(currentRow) && currAOC == null) {
        } else if (isAreaOfConcernRow(currentRow)) {
            this.aoc(currentRow, checklist, getAreaOfConcernCellNum(currentRow));
        } else if (getText(currentRow, 0).startsWith("Standard ") && getText(currentRow, 0).length() <= 15) {
            this.standard(getText(currentRow, 0), getText(currentRow, 1));
        } else if (getText(currentRow, 0).startsWith("ME") && isEmpty(currentRow, 2)) {
        } else if (getText(currentRow, 0).startsWith("ME")) {
            this.me(currentRow, checklist, hasScores, facilityAssessment);
        } else if (getText(currentRow, 0).replace(" ", "").startsWith(this.currStandard.getReference()) && checklist.getName().equals("Kayakalp")) {
            this.kayakalpME(currentRow, checklist);
        } else if (!isEmpty(currentRow, 2) && !checklist.getName().equals("Kayakalp")) {
            this.checkpoint(currentRow, checklist, hasScores, facilityAssessment);
        } else if (getText(currentRow, 0).toLowerCase().contains("score") || getText(currentRow, 1).toLowerCase().contains("score")) {
            System.out.println(String.format("Found row containing text as score, assuming it to be end of file. %s      %s", getText(currentRow, 0), getText(currentRow, 1)));
            return true;
        }
        return false;
    }

    private int getAreaOfConcernCellNum(Row currentRow) {
        String cell0Text = getText(currentRow, 0);
        String cell1Text = getText(currentRow, 1);
        if (cell1Text.startsWith("Area of Concern -") || cell1Text.startsWith("Area of Concern –")) return 1;
        else if (cell0Text.startsWith("Area of Concern -") || cell0Text.startsWith("Area of Concern –")) return 0;
        throw new RuntimeException("Could not find AOC in the row");
    }

    private boolean isAreaOfConcernRow(Row currentRow) {
        String cell0Text = getText(currentRow, 0);
        String cell1Text = getText(currentRow, 1);
        return cell1Text.startsWith("Area of Concern -") || cell1Text.startsWith("Area of Concern –") || cell0Text.startsWith("Area of Concern -") || cell0Text.startsWith("Area of Concern –");
    }

    private void kayakalpME(Row currentRow, Checklist checklist) {
        String ref = getText(currentRow, 0).replace(" ", "");
        String name = getText(currentRow, 1).trim().replaceAll(" +", " ");
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
        if (stringCellValue == null || stringCellValue.trim().equals("") || stringCellValue.trim().equals(".")) return true;

        return false;
    }

    private String getText(Row row, int cellnum) {
        Cell cell = row.getCell(cellnum);
        if (cell == null) return "";
        String stringCellValue = getCellValue(cell);
        return stringCellValue.trim().replaceAll(" +", " ");
    }

    private void checkpoint(Row row, Checklist checklist, boolean hasScores, FacilityAssessment facilityAssessment) {
        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setName(getText(row, 2));
        String am = getText(row, 4);
        checkpoint.setAssessmentMethodObservation(am.toLowerCase().contains("ob"));
        checkpoint.setAssessmentMethodPatientInterview(am.toLowerCase().contains("pi"));
        checkpoint.setAssessmentMethodRecordReview(am.toLowerCase().contains("rr"));
        checkpoint.setAssessmentMethodStaffInterview(am.toLowerCase().contains("si"));
        checkpoint.setDefault(true);
        checkpoint.setMeasurableElement(currME);
        if (!getText(row, 5).equals("")) {
            checkpoint.setMeansOfVerification(getText(row, 5));
        }
        if (!getText(row, 3).equals("") && hasScores) {
            try {
                CheckpointScore score = new CheckpointScore();
                score.setCheckpoint(checkpoint);
                score.setChecklist(checklist);
                score.setRemarks(getText(row, 6));
                score.setScore(((int) Double.parseDouble(getText(row, 3))));
                score.setFacilityAssessment(facilityAssessment);
                this.data.addScore(score);
            } catch (NumberFormatException e) {
                System.out.println(String.format("(ErrorInSheet) Found non-number in score column: %s", getText(row, 3)));
            }
        }
        currME.addCheckpoint(checkpoint);
        checklist.addCheckpoint(checkpoint);
    }

    private void me(Row row, Checklist checklist, boolean hasScores, FacilityAssessment facilityAssessment) {
        String ref = getMERef(getText(row, 0));
        String name = getText(row, 1).trim().replaceAll(" +", " ");
        MeasurableElement me = currStandard.getMeasurableElement(ref);
        if (me == null) {
            me = new MeasurableElement();
            me.setName(name);
            me.setReference(ref);
            currStandard.addMeasurableElement(me);
        }
        currME = me;
        checkpoint(row, checklist, hasScores, facilityAssessment);
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

    private void aoc(Row row, Checklist checklist, int cellnum) {
        String aocRefName = row.getCell(cellnum).getStringCellValue().replace("Area of Concern - ", "").replace("Area of Concern – ", "").replace("Area of Concern -", "").replace("Area of Concern –", "");
        String ref = aocRefName.substring(0, 1);
        String name = aocRefName.substring(2).trim().replaceAll(" +", " ");

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
