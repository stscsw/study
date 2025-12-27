package cn.csw.emoji;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Lier
 * @since 2024/8/22 10:56
 */
public class EmojiTest {


    @Test
    public void test1() {
        String messageWithEmoji = "Hello Java 21! 😍❤️";
        String messageWithoutEmoji = "Hello Java!";

        System.out.println("isEmoji");
        messageWithEmoji.codePoints()
                .forEach(item -> System.out.print(Character.isEmoji(item)+","));


        System.out.println("isEmojiPresentation");
        messageWithEmoji.codePoints()
                .forEach(item -> System.out.print(Character.isEmojiPresentation(item)+","));

        assertTrue(Character.isEmojiComponent(0x200D)); // 零宽连字
        assertTrue(Character.isEmojiComponent(0x1F3FD)); // 中性肤色
    }


    @Test
    public void test13211(){
        System.out.println(String.join("_","121",null));
        String string = System.currentTimeMillis() + "6942029322845" + "111";
        System.out.println(string.length());

    }

}
