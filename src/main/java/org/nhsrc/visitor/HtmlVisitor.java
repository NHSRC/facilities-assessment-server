package org.nhsrc.visitor;

import org.nhsrc.domain.*;
import org.nhsrc.referenceDataImport.GunakExcelFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HtmlVisitor implements GunakChecklistVisitor {
    private Context context;
    private Checklist currentChecklist;
    private static final TemplateEngine templateEngine = new TemplateEngine();

    static {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateEngine.setTemplateResolver(templateResolver);
    }

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
        tableRow.setBackgroundColor("darkgrey");
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
        tableRow.setBackgroundColor("blue");
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
        currentTable.addRow(new TableRow("", checkpoint.getName(), stringBuilder.toString(), checkpoint.getMeansOfVerification()));
    }

    @Override
    public Checklist getCurrentChecklist() {
        return currentChecklist;
    }

    public String generateHtml() {
        StringWriter stringWriter = new StringWriter();
        templateEngine.process("assessment-tool", context, stringWriter);
        return stringWriter.toString();
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

        public String getBackgroundColor() {
            return String.format("background-color:%s;", backgroundColor);
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
