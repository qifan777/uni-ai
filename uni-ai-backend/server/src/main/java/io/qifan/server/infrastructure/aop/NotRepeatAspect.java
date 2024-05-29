package io.qifan.server.infrastructure.aop;


import cn.dev33.satoken.stp.StpUtil;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Slf4j
@Component
@AllArgsConstructor
public class NotRepeatAspect {

    private final StringRedisTemplate redisTemplate;
    public static final String USER_INVOKE = "user:invoke:";

    @Pointcut("@annotation(notRepeat)")
    private void notRepeatPoints(NotRepeat notRepeat) {
    }

    @Around(value = "notRepeatPoints(notRepeat)", argNames = "joinPoint,notRepeat")
    public Object forbidRepeat(ProceedingJoinPoint joinPoint, NotRepeat notRepeat) throws Throwable {
        Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent(USER_INVOKE + StpUtil.getLoginIdAsString(), "", 10, TimeUnit.SECONDS);
        if (ifAbsent == null || !ifAbsent) {
            throw new BusinessException(ResultCode.StatusHasInvalid, "请勿重复操作");
        }
        try {
            return joinPoint.proceed();
        } finally {
            redisTemplate.delete(USER_INVOKE + StpUtil.getLoginIdAsString());
        }

    }
}
