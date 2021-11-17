package org.nhsrc.visitor;

import org.nhsrc.domain.*;
import org.nhsrc.referenceDataImport.GunakExcelFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.StringWriter;

public class HtmlVisitor implements GunakChecklistVisitor {
    private final Context context;

    public HtmlVisitor() {
        context = new Context();
    }

    @Override
    public void visit(GunakExcelFile gunakExcelFile) {
        context.setVariable("assessmentToolName", gunakExcelFile.getAssessmentTool().getName());
    }

    @Override
    public void visit(Checklist checklist) {
    }

    @Override
    public void visit(AreaOfConcern areaOfConcern) {

    }

    @Override
    public void visit(Standard standard) {

    }

    @Override
    public void visit(MeasurableElement measurableElement) {

    }

    @Override
    public void visit(Checkpoint checkpoint) {

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
}
