package proxy.testOrder;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1)
public class AOPTest1 {

    /*
    execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?
    name-pattern(param-pattern) throws-pattern?)
    modifiers-pattern表示方法的访问类型，public等；
    ret-type-pattern表示方法的返回值类型，如String表示返回类型是String，“*”表示所有的返回类型；
    declaring-type-pattern表示方法的声明类，如“com.elim..*”表示com.elim包及其子包下面的所有类型；
    name-pattern表示方法的名称，如“add*”表示所有以add开头的方法名；
    param-pattern表示方法参数的类型，name-pattern(param-pattern)其实是一起的表示的方法集对应的参数类型，如“add()”表示不带参数的add方法，“add(*)”表示带一个任意类型的参数的add方法，“add(*,String)”则表示带两个参数，且第二个参数是String类型的add方法；
    throws-pattern表示异常类型；
    其中以问号结束的部分都是可以省略的。
    1、“execution(* add())”匹配所有的不带参数的add()方法。
    2、“execution(public * com.elim..*.add*(..))”匹配所有com.elim包及其子包下所有类的以add开头的所有public方法。
    3、“execution(* *(..) throws Exception)”匹配所有抛出Exception的方法。
     */
    @Pointcut("execution( public void proxy.testOrder.SomeService.menthod())")
    public void pointCut() {
    }

    @After("pointCut()")
    public void after(JoinPoint joinPoint) {
        System.out.println("AOPTest1 after aspect executed");
    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        //如果需要这里可以取出参数进行处理
        //Object[] args = joinPoint.getArgs();
        System.out.println("AOPTest1 before aspect executing");
//        throw new RuntimeException(); 会影响业务运行
    }

    @AfterReturning(pointcut = "pointCut()", returning = "returnVal")
    public void afterReturning(JoinPoint joinPoint, Object returnVal) {
        System.out.println("AOPTest1 afterReturning executed, return result is "
                + returnVal);
    }

    @Around("pointCut()")
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("AOPTest1 around start..");
        try {
            pjp.proceed();
        } catch (Throwable ex) {
            System.out.println("AOPTest1 error in around");
            throw ex;
        }
        System.out.println("AOPTest1 around end");
    }

    @AfterThrowing(pointcut = "pointCut()", throwing = "error")
    public void afterThrowing(JoinPoint jp, Throwable error) {
        System.out.println("AOPTest1 error:" + error);
    }


}
