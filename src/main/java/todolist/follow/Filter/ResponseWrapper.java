package todolist.follow.Filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

public class ResponseWrapper extends HttpServletResponseWrapper {

    private final ServletOutputStream servletOutputStream;
    private final ByteArrayOutputStream outputStream;
    private final PrintWriter writer;

    public ResponseWrapper(HttpServletResponse response)
    {
        super(response);
        this.outputStream = new ByteArrayOutputStream();
        this.servletOutputStream = new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException
            {
                outputStream.write(b);
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener listener) {
            }
        };
        this.writer = new PrintWriter(outputStream);
    }

    @Override
    public ServletOutputStream getOutputStream() 
    throws IOException
    {
        return servletOutputStream;
    }

    @Override
    public PrintWriter getWriter()
    throws IOException
    {
        return writer;
    }

    public byte[] getResponseData()
    {
        return outputStream.toByteArray();
    }
}
