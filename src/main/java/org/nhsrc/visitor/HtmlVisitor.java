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
    private final Context context;

    public HtmlVisitor() {
        context = new Context();
    }

    @Override
    public void visit(GunakExcelFile gunakExcelFile) {
        context.setVariable("assessmentToolName", gunakExcelFile.getAssessmentTool().getName());
        context.setVariable("tables", new ArrayList<Table>());
    }

    @Override
    public void visit(Checklist checklist) {
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
        table.addRow(new TableRow(areaOfConcern.getReference(), areaOfConcern.getName()));
    }

    private Table getCurrentTable() {
        List<Table> tables = getTables();
        return tables.get(tables.size() - 1);
    }

    @Override
    public void visit(Standard standard) {
        Table currentTable = getCurrentTable();
        currentTable.addRow(new TableRow(standard.getReference(), standard.getName()));
    }

    @Override
    public void visit(MeasurableElement measurableElement) {
        Table currentTable = getCurrentTable();
        currentTable.addRow(new TableRow(measurableElement.getReference(), measurableElement.getName()));
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

    public String generateHtml() {
        TemplateEngine templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateEngine.setTemplateResolver(templateResolver);

        StringWriter stringWriter = new StringWriter();
        templateEngine.process("assessment-tool", context, stringWriter);
        return stringWriter.toString();
    }

    public class Table {
        private String checklistName;
        private final List<TableRow> tableRows =  new ArrayList<>();

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

        public TableRow(String ... cells) {
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
