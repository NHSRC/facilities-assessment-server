package org.nhsrc.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ResponseLogger implements Filter {
    private static int requestCount;
    private static File responsesDir = new File("responses");
    private static List<String> stringsToRemove = new ArrayList<>();
    private static String[] resources = new String[] {"facilityType", "checkpoint", "checklist", "measurableElement", "standard", "areaOfConcern", "assessmentType", "tag", "department", "assessmentTool", "facility", "district", "state", "assessmentType"};

    @Value("${fa.recording.enabled}")
    private boolean recordingEnabled;

    static {
        responsesDir.mkdir();
        for (String resource : resources) {
            stringsToRemove.add(String.format("http://192.168.73.1:5000/api/%s/search/lastModified?lastModifiedDate=", resource));
            stringsToRemove.add(String.format("http://192.168.73.1:5000/api/%s", resource));
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!recordingEnabled) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletResponseCopier responseCopier = new HttpServletResponseCopier((HttpServletResponse) response);
        FileWriter fileWriter = null;
        try {
            chain.doFilter(request, responseCopier);
            responseCopier.flushBuffer();
            byte[] copy = responseCopier.getCopy();
            String str = new String(copy, response.getCharacterEncoding());
            for (String s : stringsToRemove) {
                str = str.replace(s, "");
            }
            fileWriter = new FileWriter(new File(responsesDir, String.format("%d.json", requestCount++)));
            fileWriter.write(str);
        } finally {
            if (fileWriter != null)
                fileWriter.close();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    class HttpServletResponseCopier extends HttpServletResponseWrapper {

        private ServletOutputStream outputStream;
        private PrintWriter writer;
        private ServletOutputStreamCopier copier;

        public HttpServletResponseCopier(HttpServletResponse response) throws IOException {
            super(response);
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            if (writer != null) {
                throw new IllegalStateException("getWriter() has already been called on this response.");
            }

            if (outputStream == null) {
                outputStream = getResponse().getOutputStream();
                copier = new ServletOutputStreamCopier(outputStream);
            }

            return copier;
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            if (outputStream != null) {
                throw new IllegalStateException("getOutputStream() has already been called on this response.");
            }

            if (writer == null) {
                copier = new ServletOutputStreamCopier(getResponse().getOutputStream());
                writer = new PrintWriter(new OutputStreamWriter(copier, getResponse().getCharacterEncoding()), true);
            }

            return writer;
        }

        @Override
        public void flushBuffer() throws IOException {
            if (writer != null) {
                writer.flush();
            } else if (outputStream != null) {
                copier.flush();
            }
        }

        public byte[] getCopy() {
            if (copier != null) {
                return copier.getCopy();
            } else {
                return new byte[0];
            }
        }
    }

    class ServletOutputStreamCopier extends ServletOutputStream {
        private OutputStream outputStream;
        private ByteArrayOutputStream copy;

        public ServletOutputStreamCopier(OutputStream outputStream) {
            this.outputStream = outputStream;
            this.copy = new ByteArrayOutputStream(1024);
        }

        @Override
        public void write(int b) throws IOException {
            outputStream.write(b);
            copy.write(b);
        }

        public byte[] getCopy() {
            return copy.toByteArray();
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener listener) {
        }
    }
}