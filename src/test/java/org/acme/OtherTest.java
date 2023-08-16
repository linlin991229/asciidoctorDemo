package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class OtherTest {

    @Test
    void test() {
        String name = "John";
        String birthday = "2000-01-01";

        String textBlock = """
                Person={name=%s,birthday=%s}
                """.formatted(name, birthday);

        System.out.println(textBlock);

// 打印结果:
// Person={name=John,birthday=2000-01-01}
    }
}
