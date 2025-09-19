package com.shop.api.aop;

import com.shop.api.annotation.AccessLog;
import com.shop.api.biz.system.service.ContactService;
import com.shop.core.entity.User;
import com.shop.core.enums.JwtSessionAttribute;
import com.shop.core.interfaces.RequestFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * <pre>
 * Description: 접속 로그 Aspect
 * Date: 2023/03/15 23:57 AM
 * Company: smart90
 * Author: luckeey
 * </pre>
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AccessLogAspect {


    private final ContactService contactService;


    /**
     * 컨트롤러에 AccessLog 어노테이션이 있을 경우 접속 로그 저장
     * 해당 메소드가 성공했을 경우에만 실행
     * @param joinPoint
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @AfterReturning(pointcut="execution(* com.shop.api.*.*.controller.*.*(..)) && @annotation(com.shop.api.annotation.AccessLog)", returning = "result")
//    @Before( "execution(* com.shop.api.biz.*.controller.*.*(..)) && @annotation(com.shop.api.annotation.AccessLog)")
    public void addContactLog(JoinPoint joinPoint, Object result)  {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            AccessLog accessLog = method.getAnnotation(AccessLog.class);
            
            User user = null;
            RequestFilter requestFilter = null;

            // 파라미터 매핑
            for (int i = 0; i < joinPoint.getArgs().length; i++) {
                if (user == null && joinPoint.getArgs()[i] instanceof User){
                    user = (User) joinPoint.getArgs()[i];
                }
                if (joinPoint.getArgs()[i] instanceof RequestFilter){
                    requestFilter = (RequestFilter) joinPoint.getArgs()[i];
                }
            }

            for (int i = 0; i < signature.getParameterTypes().length; i++) {
                if (signature.getParameterTypes()[i].getAnnotation(RequestBody.class) != null){
                    requestFilter = (RequestFilter) joinPoint.getArgs()[i];
                }
            }

            // JwtUser 어노테이션을 사용안한 경우 한번더 사용자 정보 맵핑
            if (user == null) {
                HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
                user = (User) request.getAttribute(JwtSessionAttribute.USER.name());
            }

            // 접속 이력 저장
            contactService.logging(user, accessLog.value(), requestFilter);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }


}
