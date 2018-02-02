package org.nhsrc.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ServerFilter implements Filter {
    private static int requestCount;
    private static Map<String, Integer> map = new HashMap<>();
    private static File responsesDir = new File("responses");
    private static List<String> stringsToRemove = new ArrayList<>();
    private static String[] resources = new String[] {"facilityType", "checkpoint", "checklist", "measurableElement", "standard", "areaOfConcern", "assessmentType", "tag", "department", "assessmentTool", "facility", "district", "state", "assessmentType"};

    private static Logger logger = LoggerFactory.getLogger(ServerFilter.class);

    @Value("${recording.mode}")
    private boolean recordingMode;

    static {
        responsesDir.mkdir();
        for (String resource : resources) {
            stringsToRemove.add(String.format("http://192.168.73.1:6001/api/%s/search/lastModified?lastModifiedDate=", resource));
            stringsToRemove.add(String.format("http://192.168.73.1:6001/api/%s", resource));
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!recordingMode) {
            try {
                chain.doFilter(request, response);
            } catch (Exception e) {
                logger.error("Uncaught exception", e);
                throw e;
            }
            return;
        }

        HttpServletResponseCopier responseCopier = new HttpServletResponseCopier((HttpServletResponse) response);
        FileWriter fileWriter = null;
        boolean isStateSpecificRequest = ((HttpServletRequest) request).getRequestURI().endsWith("ByState");

        try {
            chain.doFilter(request, responseCopier);
            responseCopier.flushBuffer();
            byte[] copy = responseCopier.getCopy();
            String str = new String(copy, response.getCharacterEncoding());
            for (String s : stringsToRemove) {
                str = str.replace(s, "");
            }
            File file;
            if (isStateSpecificRequest) {
                String stateName = request.getParameter("name");
                String stateDirectoryName = stateName.replace(" ", "");
                File stateResponseDir = new File(responsesDir, stateDirectoryName);
                stateResponseDir.mkdir();
                if (map.containsKey(stateName)) {
                    Integer stateSpecificRequestCount = map.get(stateName);
                    map.put(stateName, stateSpecificRequestCount + 1);
                } else {
                    map.put(stateName, 0);
                }
                file = new File(stateResponseDir, String.format("%d.json", map.get(stateName)));
            } else {
                file = new File(responsesDir, String.format("%d.json", requestCount++));
            }

            fileWriter = new FileWriter(file);
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