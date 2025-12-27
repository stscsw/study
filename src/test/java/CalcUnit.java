import cn.hutool.core.lang.Dict;
import cn.hutool.extra.expression.ExpressionEngine;
import cn.hutool.extra.expression.ExpressionUtil;
import org.apache.commons.jexl3.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lier
 * @since 2025/7/16 14:21
 */
public class CalcUnit {


    // 定义多个公式模板
    private static final Map<String, String> FORMULAS = new HashMap<>();

    static {
        // 示例公式集合，key是条件名称，value是公式字符串
        FORMULAS.put("area_rectangle", "width * height");
        FORMULAS.put("area_circle", "Math.PI * radius * radius");
        FORMULAS.put("hypotenuse", "Math.sqrt(a*a + b*b)");
    }

    // 根据条件选择公式并计算
    public static double calculateBasedOnCondition(String condition, Map<String, Double> variables) throws Exception {
        String formula = FORMULAS.get(condition);
        if (formula == null) {
            throw new IllegalArgumentException("未找到符合条件的公式: " + condition);
        }

        JexlEngine jexl = new JexlBuilder().create();
        JexlExpression expression = jexl.createExpression(formula);
        JexlContext context = new MapContext();
        context.set("Math", java.lang.Math.class);

        // 设置变量
        for (Map.Entry<String, Double> entry : variables.entrySet()) {
            context.set(entry.getKey(), entry.getValue());
        }

        Object result = expression.evaluate(context);
        return ((Number) result).doubleValue();
    }


    public static void main(String[] args) {


        try {
            // 示例1：矩形面积
            Map<String, Double> vars1 = new HashMap<>();
            vars1.put("width", 5.0);
            vars1.put("height", 3.0);
            double areaRect = calculateBasedOnCondition("area_rectangle", vars1);
            System.out.println("矩形面积: " + areaRect);

            // 示例2：圆面积
            Map<String, Double> vars2 = new HashMap<>();
            vars2.put("radius", 4.0);
            double areaCircle = calculateBasedOnCondition("area_circle", vars2);
            System.out.println("圆面积: " + areaCircle);

            // 示例3：勾股定理
            Map<String, Double> vars3 = new HashMap<>();
            vars3.put("a", 3.0);
            vars3.put("b", 4.0);
            double hypotenuse = calculateBasedOnCondition("hypotenuse", vars3);
            System.out.println("斜边长度: " + hypotenuse);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void test1(){

//        ExpressionEngine expressionEngine = new ExpressionEngine() {
//            @Override
//            public Object eval(String expression, Map<String, Object> context) {
//                System.out.println("自定义哦");
//                return 1;
//            }
//        };

        final Dict dict = Dict.create()
                .set("a", 100.3)
                .set("b", 45)
                .set("c", -199.100);

        // -143.8
        final Object eval = ExpressionUtil.eval("a-(b-c)", dict);
        System.out.println(eval);
    }

}
