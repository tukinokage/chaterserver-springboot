package com.example.chaterserver.app;

import com.example.chaterserver.annotations.AuthToken;
import com.example.chaterserver.redisCache.RedisUtil;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;



public class AuthorizationInterceptor implements HandlerInterceptor {

    @Autowired
    RedisUtil redisUtil;


    private static final Logger log = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    //存放鉴权信息的Header名称，默认是Authorization
    private String httpHeaderName = "Authorization";


    //鉴权失败后返回的错误信息，默认为401 unauthorized
    private String unauthorizedErrorMessage = "401 unauthorized";

    //鉴权失败后返回的HTTP错误码，默认为401
    private int unauthorizedErrorCode = HttpServletResponse.SC_UNAUTHORIZED;

    //存放登录用户模型Key的Request Key
    public static final String REQUEST_CURRENT_KEY = "REQUEST_CURRENT_KEY";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

// 如果打上了AuthToken注解则需要验证token
        if (method.getAnnotation(AuthToken.class) != null || handlerMethod.getBeanType().getAnnotation(AuthToken.class) != null) {

            String token = request.getParameter(httpHeaderName);
            log.info("Get token from request is {} ", token);
            String username = "";
            if (token != null && token.length() != 0) {
                username = (String) redisUtil.get(token);
                log.info("Get username from Redis is {}", username);
            }
            if (username != null && !username.trim().equals("")) {
                Long tokeBirthTime = Long.valueOf((String) redisUtil.get(token + username));
                log.info("token Birth time is: {}", tokeBirthTime);
                Long diff = System.currentTimeMillis() - tokeBirthTime;
                log.info("token is exist : {} ms", diff);
                if (diff > ConstantKit.TOKEN_RESET_TIME) {
                    redisUtil.expire(username, ConstantKit.TOKEN_EXPIRE_TIME);
                    redisUtil.expire(token, ConstantKit.TOKEN_EXPIRE_TIME);
                    log.info("Reset expire time success!");
                    Long newBirthTime = System.currentTimeMillis();
                    redisUtil.set(token + username, newBirthTime.toString());
                }

                //用完关闭
                redisUtil.close();
                request.setAttribute(REQUEST_CURRENT_KEY, username);
                return true;
            } else {
                JSONObject jsonObject = new JSONObject();

                PrintWriter out = null;
                try {
                    response.setStatus(unauthorizedErrorCode);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                    jsonObject.put("code", ((HttpServletResponse) response).getStatus());
                    jsonObject.put("message", HttpStatus.UNAUTHORIZED);
                    out = response.getWriter();
                    out.println(jsonObject);

                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (null != out) {
                        out.flush();
                        out.close();
                    }
                }

            }

        }

        request.setAttribute(REQUEST_CURRENT_KEY, null);

        return true;



    }



}
