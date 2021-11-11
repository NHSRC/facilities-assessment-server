package org.nhsrc.referenceDataImport;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.nhsrc.config.excelMetaDataError.AssessmentExcelMetaDataErrors;
import org.nhsrc.domain.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SheetRowImporter {
    private Standard currStandard;
    private MeasurableElement currME;
    private AreaOfConcern currAOC;
    private AssessmentChecklistData data;
    private static final Pattern mePattern = Pattern.compile("([a-zA-Z][0-9]+\\.[0-9]+)(.*)");
    private static final Pattern standardPattern = Pattern.compile("([a-zA-Z][0-9]+)(.*)");

    public SheetRowImporter(AssessmentChecklistData data) {
        this.data = data;
    }

    private String getCleanedRef(String cellContent, Pattern pattern, String toReplace) {
        String ref = cellContent.replace(toReplace, "").trim().replaceAll(" +", " ");
        ref = ref.replaceAll("\\s", "");
        ref = ref.replaceAll("[,.]+", ".");
        Matcher matcher = pattern.matcher(ref);
        String cleanedRef = ref;
        if (matcher.find()) {
            cleanedRef = matcher.group(1);
        }
        return cleanedRef;
    }

    String getMERef(String cellText) {
        return getCleanedRef(cellText, mePattern, "ME");
    }

    private String getStandardRef(String cell) {
        return getCleanedRef(cell, standardPattern, "Standard");
    }

    public boolean importRow(Row currentRow, Checklist checklist) {
        if (currentRow.getZeroHeight()) {
            return false;
        }

        if (isEmpty(currentRow, 0) && isEmpty(currentRow, 1) && isEmpty(currentRow, 2)) {
        } else if (!isAreaOfConcernRow(currentRow) && currAOC == null) {
        } else if (isAreaOfConcernRow(currentRow)) {
            this.aoc(currentRow, checklist, getAreaOfConcernCellNum(currentRow));
        } else if (AssessmentExcelMetaDataErrors.getRealString(getText(currentRow, 0)).startsWith("Standard ") && AssessmentExcelMetaDataErrors.getRealString(getText(currentRow, 0)).length() <= 15) {
            this.standard(AssessmentExcelMetaDataErrors.getRealString(getText(currentRow, 0)), getText(currentRow, 1), checklist);
        } else if (getText(currentRow, 0).startsWith("ME") && isEmpty(currentRow, 2) && !getText(currentRow, 1).equals(getText(currentRow, 2))) {
        } else if (getText(currentRow, 0).startsWith("ME") && getText(currentRow, 1).equals(getText(currentRow, 2))) {
            this.meWithSameCheckpointName(currentRow, checklist);
        } else if (getText(currentRow, 0).startsWith("ME")) {
            this.me(currentRow, checklist);
        } else if (getText(currentRow, 0).replace(" ", "").startsWith(this.currStandard.getReference()) && checklist.getName().equals("Kayakalp")) {
            this.meWithSameCheckpointName(currentRow, checklist);
        } else if (!isEmpty(currentRow, 2) && !checklist.getName().equals("Kayakalp")) {
            this.checkpoint(currentRow, checklist);
        } else if (getText(currentRow, 0).toLowerCase().contains("score") || getText(currentRow, 1).toLowerCase().contains("score")) {
            System.out.println(String.format("Found row containing text as score, assuming it to be end of file. %s      %s", getText(currentRow, 0), getText(currentRow, 1)));
            return true;
        }
        return false;
    }

    private int getAreaOfConcernCellNum(Row currentRow) {
        String cell0Text = getText(currentRow, 0);
        String cell1Text = getText(currentRow, 1);
        String cell2Text = getText(currentRow, 2);
        if (cell1Text.startsWith("Area of Concern -") || cell1Text.startsWith("Area of Concern –")) return 1;
        else if (cell0Text.startsWith("Area of Concern -") || cell0Text.startsWith("Area of Concern –")) return 0;
        else if (cell2Text.startsWith("Area of Concern -") || cell2Text.startsWith("Area of Concern –")) return 2;
        throw new RuntimeException("Could not find AOC in the row");
    }

    private boolean isAreaOfConcernRow(Row currentRow) {
        String cell0Text = getText(currentRow, 0);
        String cell1Text = getText(currentRow, 1);
        String cell2Text = getText(currentRow, 2);
        return cell1Text.startsWith("Area of Concern -") || cell1Text.startsWith("Area of Concern –") ||
                cell0Text.startsWith("Area of Concern -") || cell0Text.startsWith("Area of Concern –") ||
                cell2Text.startsWith("Area of Concern -") || cell2Text.startsWith("Area of Concern –");
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
        if (stringCellValue == null || stringCellValue.trim().equals("") || stringCellValue.trim().equals(".") || "Maximum".equals(stringCellValue)) return true;

        return false;
    }

    private String getText(Row row, int cellnum) {
        Cell cell = row.getCell(cellnum);
        if (cell == null) return "";
        String stringCellValue = getCellValue(cell);
        return stringCellValue.trim().replaceAll(" +", " ");
    }

    private void checkpoint(Row row, Checklist checklist) {
        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setName(getText(row, 2));
        checklist.addCheckpoint(checkpoint);
        String am = getText(row, 4);
        String scoreLevels = getText(row, 7);
        String isOptional = getText(row, 8);
        try {
            checkpoint.setScoreLevels(scoreLevels != null && scoreLevels.length() != 0 ? (int) Double.parseDouble(scoreLevels) : 3);
        } catch (NumberFormatException e) {
            checkpoint.setScoreLevels(3);
        }
        checkpoint.setOptional(isOptional != null && isOptional.equalsIgnoreCase("yes"));
        checkpoint.setAssessmentMethodObservation(am.toLowerCase().contains("ob"));
        checkpoint.setAssessmentMethodPatientInterview(am.toLowerCase().contains("pi"));
        checkpoint.setAssessmentMethodRecordReview(am.toLowerCase().contains("rr"));
        checkpoint.setAssessmentMethodStaffInterview(am.toLowerCase().contains("si"));
        checkpoint.setMeasurableElement(currME);
        if (!getText(row, 5).equals("")) {
            checkpoint.setMeansOfVerification(getText(row, 5));
        }
        if (currME == null) {
            System.err.println(String.format("[ERROR] No measurable element created yet for checkpoint=%s", checkpoint.toString()));
        } else if (currME.containsCheckpoint(checkpoint)) {
            System.err.println(String.format("[WARN] Another checkpoint with the same name=%s in this ME exists. Skipping this one.", checkpoint.toString()));
            String meansOfVerification = checkpoint.getMeansOfVerification();
            if (meansOfVerification != null && !meansOfVerification.isEmpty()) {
                Checkpoint otherCheckpoint = currME.findCheckpoint(checkpoint.getName(), checklist);
                otherCheckpoint.setMeansOfVerification(meansOfVerification);
            }
        } else {
            currME.addCheckpoint(checkpoint);
        }
    }

    private void meWithSameCheckpointName(Row currentRow, Checklist checklist) {
        String ref = getMERef(getText(currentRow, 0));
        String name = getText(currentRow, 1).trim().replaceAll(" +", " ");
        MeasurableElement me = currStandard.getMeasurableElement(ref);
        if (me == null) {
            me = new MeasurableElement();
            me.setName(name);
            me.setReference(ref);
            currStandard.addMeasurableElement(me);
        }
        if (!currStandard.getReference().substring(0, 2).equals(me.getReference().substring(0, 2)) || me.getReference().length() < 4) {
            System.err.println(String.format("[ERROR] Found Measurable element with name=%s under standard=%s, in checklist=%s", me.getReference(), currStandard.getReference(), checklist.getName()));
        }
        currME = me;

        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setName(name);
        String am = getText(currentRow, 3);
        checkpoint.setAssessmentMethodObservation(am.toLowerCase().contains("ob"));
        checkpoint.setAssessmentMethodPatientInterview(am.toLowerCase().contains("pi"));
        checkpoint.setAssessmentMethodRecordReview(am.toLowerCase().contains("rr"));
        checkpoint.setAssessmentMethodStaffInterview(am.toLowerCase().contains("si"));
        checkpoint.setMeansOfVerification(getText(currentRow, 4));
        checkpoint.setMeasurableElement(currME);
        currME.addCheckpoint(checkpoint);
        checklist.addCheckpoint(checkpoint);
    }

    private void me(Row row, Checklist checklist) {
        String ref = getMERef(AssessmentExcelMetaDataErrors.getRealString(getText(row, 0)));
        String name = getText(row, 1).trim().replaceAll(" +", " ");
        MeasurableElement me = currStandard.getMeasurableElement(ref);
        if (me == null) {
            me = new MeasurableElement();
            me.setName(name);
            me.setReference(ref);
            currStandard.addMeasurableElement(me);
        }
        if (!currStandard.getReference().substring(0, 2).equals(me.getReference().substring(0, 2)) || me.getReference().length() < 4) {
            System.err.println(String.format("[ERROR] Found MeasurableElement with name=%s under standard=%s, in checklist=%s, but their prefix do not match. Most likely the standard was not defined with correct format e.g. --> Standard A4", me.getReference(), currStandard.getReference(), checklist.getName()));
        }
        currME = me;
        checkpoint(row, checklist);
    }

    private void standard(String standardRefCellText, String standardNameCellText, Checklist checklist) {
        String ref = getStandardRef(standardRefCellText);

        Standard standard = currAOC.getStandard(ref);
        if (standard == null) {
            standard = new Standard();
            standard.setReference(ref);
            standard.setName(standardNameCellText);
            currAOC.addStandard(standard);
        }
        if (!currAOC.getReference().substring(0, 1).equals(standard.getReference().substring(0, 1)) || standard.getReference().length() < 2) {
            System.err.println(String.format("[ERROR] Found Standard with name=%s under Area of Concern=%s, in Checklist=%s but their prefix do not match. Quite likely that the area concern was not defined with right format which is ---> Area of Concern - <NAME>", standard.getReference(), currAOC.getReference(), checklist.getName()));
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
