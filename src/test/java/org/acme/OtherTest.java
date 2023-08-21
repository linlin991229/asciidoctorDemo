package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
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

    @Test
    void testMutiny() {
        Uni<String> uni = Uni.createFrom().item("我是");
        uni.onItem().transformToUni(item -> Uni.createFrom().item(item + "大傻逼"))
                .subscribe().with(System.out::println);
        // 将创建推迟到订阅
        Uni<String> uni1 = Uni.createFrom().deferred(()->Uni.createFrom().item("哈哈"));

        Uni.combine().all().unis(uni,uni1).asTuple().invoke(tuple-> System.out.println(tuple.getItem1()+":"+tuple.getItem2())).subscribe().with(System.out::println);
        // 转发第一个
        Uni.combine().any().of(uni1,uni).invoke(tuple-> System.out.println(tuple)).subscribe().with(System.out::println);



        Multi<Integer> multi = Multi.createFrom().items(1, 2, 3, 4);
        Multi<Integer> multi1 = Multi.createFrom().items(6, 7, 8, 9, 10);
        multi.onItem().transformToMultiAndMerge(item -> multi1).subscribe().with(x -> System.out.print(x + ", "));
        System.out.println();
        System.out.println("--------------华丽的分割线-----------------");
        multi.onItem().transformToMultiAndConcatenate(item -> multi1).subscribe().with(x -> System.out.print(x + ", "));
        System.out.println();
        System.out.println("--------------华丽的分割线-----------------");
        Multi.createBy().merging().streams(multi, multi1).subscribe().with((x) -> System.out.print(x + ", "));
        System.out.println();
        System.out.println("--------------华丽的分割线-----------------");
        Multi.createBy().concatenating().streams(multi, multi1).subscribe().with(x -> System.out.print(x + ", "));
    }

}
