package org.nhsrc.referenceDataImport;

import org.nhsrc.domain.AreaOfConcern;
import org.nhsrc.domain.AssessmentTool;
import org.nhsrc.domain.Checklist;
import org.nhsrc.domain.Checkpoint;
import org.nhsrc.visitor.GunakChecklistVisitor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GunakExcelFile {
    private final List<Checklist> checklists = new ArrayList<>();
    private final AssessmentTool assessmentTool;
    private final Set<AreaOfConcern> areaOfConcerns = new HashSet<>();
    private ExcelImportReport excelImportReport;

    public GunakExcelFile(AssessmentTool assessmentTool) {
        this.assessmentTool = assessmentTool;
    }

    public void addChecklist(Checklist checklist) {
        checklists.add(checklist);
    }

    public List<Checklist> getChecklists() {
        return this.checklists;
    }

    public AssessmentTool getAssessmentTool() {
        return assessmentTool;
    }

    public AreaOfConcern findAreaOfConcern(String ref) {
        return areaOfConcerns.stream().filter(aoc -> aoc.getReference().equals(ref)).findAny().orElse(null);
    }

    public void add(AreaOfConcern areaOfConcern) {
        areaOfConcerns.add(areaOfConcern);
    }

    public Set<AreaOfConcern> getAreaOfConcerns() {
        return areaOfConcerns;
    }

    public void accept(GunakChecklistVisitor visitor) {
        visitor.visit(this);
        checklists.forEach(checklist -> checklist.accept(visitor));
    }

    public void validate() {
        Stream<Checkpoint> checkpointStream = this.assessmentTool.getChecklists().stream().flatMap(checklist -> checklist.getCheckpoints().parallelStream());
        List<Checkpoint> checkpointsExceedColumnSizes = checkpointStream.filter(checkpoint -> checkpoint.getName().length() > 1023 || (checkpoint.getMeansOfVerification() != null && checkpoint.getMeansOfVerification().length() > 5000)).collect(Collectors.toList());
        if (checkpointsExceedColumnSizes.size() > 0) {
            String message = checkpointsExceedColumnSizes.stream().map(Checkpoint::getChecklistMeasurableElementKey)
                    .collect(Collectors.joining("\n"));
            throw new CheckpointExceedingColumnSizeException(message);
        }

        List<Checkpoint> checkpoints = this.assessmentTool.getChecklists().stream().flatMap(checklist -> checklist.getCheckpoints().parallelStream()).collect(Collectors.toList());
        Map<String, Long> uniqueCheckpoints = checkpoints.parallelStream().collect(Collectors.groupingBy(Checkpoint::getChecklistMeasurableElementKey, Collectors.counting()));
        Map<String, Long> duplicateCheckpoints = uniqueCheckpoints.entrySet().stream().filter(stringLongEntry -> stringLongEntry.getValue() > 1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (duplicateCheckpoints.size() > 0) {
            String duplicateCheckpointsString = String.join("\n", uniqueCheckpoints.keySet());
            throw new DuplicateCheckpointException(duplicateCheckpointsString);
        }

        List<Checklist> emptyChecklists = this.assessmentTool.getChecklists().parallelStream().filter(checklist -> checklist.getApplicableAreasOfConcern().size() == 0).collect(Collectors.toList());
        if (emptyChecklists.size() != 0) {
            String emptyChecklistNames = emptyChecklists.stream().map(Checklist::getName).collect(Collectors.joining(","));
            throw new EmptyChecklistException(emptyChecklistNames);
        }

        List<Checklist> checklistWithoutCheckpoints = this.assessmentTool.getChecklists().parallelStream().filter(checklist -> checklist.getCheckpoints().size() == 0).collect(Collectors.toList());
        if (checklistWithoutCheckpoints.size() > 0) {
            String emptyChecklistNames = checklistWithoutCheckpoints.stream().map(Checklist::getName).collect(Collectors.joining(","));
            throw new EmptyChecklistException(emptyChecklistNames);
        }
    }

    public int getColumnNumber(int columnNumber) {
        return assessmentTool.isThemed() ? columnNumber + 1 : columnNumber;
    }

    public static class EmptyChecklistException extends RuntimeException {
        public EmptyChecklistException(String message) {
            super(message);
        }
    }

    public static class DuplicateCheckpointException extends RuntimeException {
        public DuplicateCheckpointException(String message) {
            super(message);
        }
    }

    public static class CheckpointExceedingColumnSizeException extends RuntimeException {
        public CheckpointExceedingColumnSizeException(String message) {
            super(message);
        }
    }

    public void setReport(ExcelImportReport excelImportReport) {
        this.excelImportReport = excelImportReport;
    }
}
