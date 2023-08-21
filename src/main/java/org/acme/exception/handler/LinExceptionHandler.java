package org.acme.exception.handler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.acme.commont.LinResult;
import org.acme.exception.LinException;

import java.time.LocalDateTime;

@Provider
public class LinExceptionHandler implements ExceptionMapper<LinException> {
    @Override
    public Response toResponse(LinException exception) {
        LinResult linResult = new LinResult().setCode(exception.getCode())
                .setMessage("触发异常:" + exception.getMessage() + "::时间:" + LocalDateTime.now());
        return Response.ok().entity(linResult).build();
    }
}
