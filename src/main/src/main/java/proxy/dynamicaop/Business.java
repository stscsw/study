package proxy.dynamicaop;

public class Business implements IBusiness, IBusiness2 {

    @Override
    public void doSomeThing() {
        System.out.println("doSomeThing");
    }

    @Override
    public void doSomeThing2() {
        System.out.println("doSomeThing2");
    }
}
