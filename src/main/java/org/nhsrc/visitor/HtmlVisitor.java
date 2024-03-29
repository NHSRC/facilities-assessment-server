package org.nhsrc.visitor;

import org.nhsrc.domain.*;
import org.nhsrc.referenceDataImport.GunakExcelFile;
import org.nhsrc.utils.StringUtil;
import org.thymeleaf.context.Context;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HtmlVisitor implements GunakChecklistVisitor {
    private Context context;
    private Checklist currentChecklist;

    @Override
    public void visit(GunakExcelFile gunakExcelFile) {
        context = new Context();
        context.setVariable("assessmentToolName", gunakExcelFile.getAssessmentTool().getName());
        context.setVariable("tables", new ArrayList<Table>());
    }

    @Override
    public void visit(Checklist checklist) {
        this.currentChecklist = checklist;
        List<Table> tables = getTables();
        Table table = new Table();
        table.setChecklistName(checklist.getName());
        tables.add(table);
    }

    private List<Table> getTables() {
        return (List<Table>) context.getVariable("tables");
    }

    @Override
    public void visit(AreaOfConcern areaOfConcern) {
        Table table = getCurrentTable();
        TableRow tableRow = new TableRow(areaOfConcern.getReference(), areaOfConcern.getName(), "", "");
        tableRow.setFontColor("White");
        tableRow.setBackgroundColor("dimgrey");
        table.addRow(tableRow);
    }

    private Table getCurrentTable() {
        List<Table> tables = getTables();
        return tables.get(tables.size() - 1);
    }

    @Override
    public void visit(Standard standard) {
        Table currentTable = getCurrentTable();
        TableRow tableRow = new TableRow(standard.getReference(), standard.getName(), "", "");
        tableRow.setBackgroundColor("yellow");
        currentTable.addRow(tableRow);
    }

    @Override
    public void visit(MeasurableElement measurableElement) {
        Table currentTable = getCurrentTable();
        TableRow tableRow = new TableRow(measurableElement.getReference(), measurableElement.getName(), "", "");
        tableRow.setFontColor("White");
        tableRow.setBackgroundColor("SteelBlue");
        currentTable.addRow(tableRow);
    }

    @Override
    public void visit(Checkpoint checkpoint) {
        Table currentTable = getCurrentTable();
        StringBuilder stringBuilder = new StringBuilder();
        if (checkpoint.getAssessmentMethodStaffInterview()) stringBuilder.append("SI").append("/");
        if (checkpoint.getAssessmentMethodObservation()) stringBuilder.append("OB").append("/");
        if (checkpoint.getAssessmentMethodRecordReview()) stringBuilder.append("RR").append("/");
        if (checkpoint.getAssessmentMethodPatientInterview()) stringBuilder.append("PI");
        currentTable.addRow(new TableRow("", checkpoint.getName(), stringBuilder.toString(), checkpoint.getMeansOfVerification(), checkpoint.getThemeNames()));
    }

    @Override
    public Checklist getCurrentChecklist() {
        return currentChecklist;
    }

    public Context getContext() {
        return context;
    }

    public class Table {
        private String checklistName;
        private final List<TableRow> tableRows = new ArrayList<>();

        public String getChecklistName() {
            return checklistName;
        }

        public void setChecklistName(String checklistName) {
            this.checklistName = checklistName;
        }

        public void addRow(TableRow tableRow) {
            tableRows.add(tableRow);
        }

        public List<TableRow> getRows() {
            return tableRows;
        }
    }

    public class TableRow {
        private final List<TableCell> tableCells = new ArrayList<>();
        private String backgroundColor;
        private String fontColor;

        public String getStyle() {
            return String.format("background-color:%s;color:%s", backgroundColor, fontColor);
        }

        public void setBackgroundColor(String backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public TableRow(String... cells) {
            Arrays.stream(cells).forEach(this::addCell);
        }

        public void addCell(String cellValue) {
            TableCell tableCell = new TableCell(cellValue);
            tableCells.add(tableCell);
        }

        public void setFontColor(String fontColor) {
            this.fontColor = fontColor;
        }

        public List<TableCell> getCells() {
            return tableCells;
        }
    }

    public class TableCell {
        private final String value;

        public TableCell(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
