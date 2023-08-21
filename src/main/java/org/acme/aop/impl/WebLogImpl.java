package org.acme.aop.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.http.HttpServerRequest;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import org.acme.aop.WebLog;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

@WebLog
@Interceptor
public class WebLogImpl {
    private static final Logger LOGGER = Logger.getLogger(WebLogImpl.class.getName());

    @Context
    HttpServerRequest request;
    @Context
    UriInfo uriInfo;

//    @AroundConstruct 拦截构造方法

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {

        LOGGER.info("=========================before================================");

        LOGGER.info("RequestMethod: " + request.method());
        LOGGER.info("URL: " + uriInfo.getPath());
        LOGGER.info("IP: " + request.remoteAddress());

        Method method = context.getMethod();
        Map<String, Object> contextData = context.getContextData();
        Object target = context.getTarget();
        LOGGER.info("请求类: " + target.getClass().getSimpleName() + ", 请求方法: " + method.getName());
        Set<String> strings = contextData.keySet();


        for (String string : strings) {
            LOGGER.info(contextData.get(string).toString());
        }
        Parameter[] parametersName = method.getParameters();
        Object[] parametersValue = context.getParameters();

        for (int i = 0; i < parametersName.length; i++) {
            LOGGER.info("请求参数===>" + parametersName[i].getName() + ": " + parametersValue[i]);
        }

        Object object = context.proceed();

        ObjectMapper objectMapper = new ObjectMapper();

        LOGGER.info("返回参数: " + objectMapper.writeValueAsString(object));

        LOGGER.info("=========================end===================================");

        return object;

    }
}
