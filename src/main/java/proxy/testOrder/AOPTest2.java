package proxy.testOrder;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(2)
public class AOPTest2 {

    @Pointcut("execution( public void proxy.testOrder.SomeService.menthod())")
    public void pointCut() {
    }

    @After("pointCut()")
    public void after(JoinPoint joinPoint) {
        System.out.println("AOPTest2 after aspect executed");
    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        //如果需要这里可以取出参数进行处理
        //Object[] args = joinPoint.getArgs();
        System.out.println("AOPTest2 before aspect executing");
    }

    @AfterReturning(pointcut = "pointCut()", returning = "returnVal")
    public void afterReturning(JoinPoint joinPoint, Object returnVal) {
        System.out.println("AOPTest2 afterReturning executed, return result is "
                + returnVal);
    }

    @Around("pointCut()")
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("AOPTest2 around start..");
        try {
            pjp.proceed();
        } catch (Throwable ex) {
            System.out.println("AOPTest2 error in around");
            throw ex;
        }
        System.out.println("AOPTest2 around end");
    }

    @AfterThrowing(pointcut = "pointCut()", throwing = "error")
    public void afterThrowing(JoinPoint jp, Throwable error) {
        System.out.println("AOPTest2 error:" + error);
    }


}
