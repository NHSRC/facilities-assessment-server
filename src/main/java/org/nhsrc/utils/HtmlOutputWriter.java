package org.nhsrc.utils;

import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.StringWriter;

@Component
public class HtmlOutputWriter {
    private final TemplateEngine templateEngine;

    public HtmlOutputWriter() {
        templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateEngine.setTemplateResolver(templateResolver);
    }

    public String generateReportHtml(Context context) {
        StringWriter stringWriter = new StringWriter();
        templateEngine.process("assessment-tool", context, stringWriter);
        return stringWriter.toString();
    }

    public String generateErrorHtml(Context context) {
        StringWriter stringWriter = new StringWriter();
        templateEngine.process("assessment-tool-error", context, stringWriter);
        return stringWriter.toString();
    }
}
