package cn.csw.java21;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static java.lang.StringTemplate.RAW;
import static java.lang.StringTemplate.STR;
import static java.util.FormatProcessor.FMT;


/**
 * @author Lier
 * @since 2023/10/9 17:14
 */
public class Main {


    @Test
    public void test1() {
        int x = 10;
        int y = 20;
        StringTemplate st = RAW. "\{ x } + \{ y } = \{ x + y }" ;
        List<String> fragments = st.fragments();
        List<Object> values = st.values();
        System.out.println("fragments:" + fragments);
        System.out.println("values:" + values);
        System.out.println("interpolate:" + st.interpolate());

        String s = STR. "\{ x } + \{ y } = \{ x + y }" ;
        System.out.println("str:" + s);


        String filePath = "tmp.dat";
        File file = new File(filePath);
        String msg = STR. "The file \{ filePath } \{ file.exists() ? "does" : "does not" } exist" ;
        System.out.println(msg);
    }

    @Test
    public void testFmt() {
        record Rectangle(String name, double width, double height) {
            double area() {
                return width * height;
            }
        }
        Rectangle[] zone = new Rectangle[]{
                new Rectangle("Alfa", 17.8, 31.4),
                new Rectangle("Bravo", 9.6, 12.4),
                new Rectangle("Charlie", 7.1, 11.23),
        };
        //-左对齐  +右对齐
        String table = FMT. """
    Description     Width    Height     Area
    %-12s\{ zone[0].name }  %7.2f\{ zone[0].width }  %7.2f\{ zone[0].height }     %7.2f\{ zone[0].area() }
    %-12s\{ zone[1].name }  %7.2f\{ zone[1].width }  %7.2f\{ zone[1].height }     %7.2f\{ zone[1].area() }
    %-12s\{ zone[2].name }  %7.2f\{ zone[2].width }  %7.2f\{ zone[2].height }     %7.2f\{ zone[2].area() }
    \{ " ".repeat(28) } Total %7.2f\{ zone[0].area() + zone[1].area() + zone[2].area() }
    """ ;
        System.out.println(table);

    }


    @Test
    public void testCustomProcessor() {
        StringTemplate.Processor<JSONObject, RuntimeException> MYJSON = StringTemplate.Processor.of(
                (StringTemplate st) -> JSON.parseObject(st.interpolate())
        );

        StringTemplate.Processor<JSONObject, RuntimeException> processor = st -> JSON.parseObject(st.interpolate());

        String name = "Joan Smith";
        String phone = "555-123-4567";
        String address = "1 Maple Drive, Anytown";
        JSONObject doc = processor. """
                                {
                                    "name":    "\{ name }",
                                    "phone":   "\{ phone }",
                                    "address": "\{ address }"
                                }
                                """ ;
        System.out.println(doc);
    }

    @Test
    public void testRecord() {
        record Point(int x, int y) {
            void display() {
                System.out.println(STR. "x:\{ x } y:\{ y }" );
            }
        }

        Point point = new Point(1, 2);
        point.display();
    }

    public static void main(String[] args) {
        int x = 10;
        int y = 20;
        StringTemplate stringTemplate = RAW. "\{ x } + \{ y } = \{ x + y }" ;
        System.out.println(stringTemplate.fragments());
        System.out.println(stringTemplate.values());
        System.out.println(stringTemplate.interpolate());

        System.out.println(STR. "\{ x } + \{ y } = \{ x + y }" );

        StringTemplate.Processor<JSONObject, RuntimeException> processor = st -> JSON.parseObject(st.interpolate());
        JSONObject jsonObject = processor."""
                {
                    "x":\{x},
                    "y":\{y}
                }
                """;
        System.out.println(jsonObject);
    }

}
