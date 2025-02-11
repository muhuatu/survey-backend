package com.yuina.survey;

import com.yuina.survey.service.ifs.QuizService2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SpringBootTest
class SurveyApplicationTests {

    @Test
    void contextLoads() {
    }

    // JAVA 8 新特性
    @Test
    public void lambdaListTest() {
        List<Integer> list = new ArrayList<>(); // new後面不用寫資料型態
        list.add(1);
        list.add(2);
        list.add(3);

        // foreach
        for (Integer item : list) {
            System.out.println(item);
        }

        // lambda foreach (等同於上面)
        list.forEach((item) -> {
            System.out.println(item);
        });

        // 小括號中的變數只有一個時，小括號可省略
        list.forEach(item -> {
            System.out.println(item);
        });

        // 大括號中的實作行數只有一行時，大括號可以省略
        list.forEach(item ->
                System.out.println(item)
        );
    }

    @Test
    public void lambdaMapTest() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "A");
        map.put(2, "B");
        map.put(3, "C");

        // entrySet
        for (Map.Entry<Integer, String> item : map.entrySet()) {
            System.out.println("key : " + item.getKey());
            System.out.println("value : " + item.getValue());
        }

        System.out.println("=========================");

        // keySet 只收集 key
        for (Integer item : map.keySet()) {
            System.out.println("key : " + item);
            System.out.println("value : " + map.get(item));
        }

        System.out.println("=========================");

        // k, v 就是 key, value
        map.forEach((k, v) -> {
            System.out.println("key : " + k);
            System.out.println("value : " + v);
        });

    }

    @Test
    public void ifTest() {

        // 原本的寫法：重新定義 QuizService 中的方法
        QuizService2 ifs = new QuizService2() {
            @Override
            public void test() {
                System.out.println("=========================");
            }
        };

        // lambda 表達式：定義所有的方法 (介面中只有定義一個方法時使用)
        // 因為介面中的 test 方法沒有參數，所以小括號中不需要有參數
        QuizService2 ifss = () -> {
            System.out.println("=========================");
        };
    }

    @Test
    public void streamTest() {
        List<String> names = new ArrayList<>();
        Collections.addAll(names, "張三豐", "張無忌", "周芷若", "趙敏", "張強");

        List<String> list2 = names.stream()
                .filter(s -> s.startsWith("張"))
                .filter(age -> age.length() == 3)
                .collect(Collectors.toList());
        System.out.println(list2); // [張三豐, 張無忌]

    }

    @Test
    public void functionTest(){
        // Function<T(參數資料型態), R(回傳資料型態)> = Function<String, Integer>
        // String(T): 重新定義 apply 方法中的參數資料型態
        // Integer(R): 重新定義 apply 方法執行結果的回傳值資料型態

        Function<String, Integer> fun = new Function<>() {

            @Override
            public Integer apply(String str) {
                if(str.equalsIgnoreCase("Single")){
                    return 1;
                }
                if(str.equalsIgnoreCase("Multi")){
                    return 2;
                }
                if(str.equalsIgnoreCase("Text")){
                    return 3;
                }
                return null;
            }
        };

        Integer result = fun.apply("Single");
        Integer result1 = fun.apply("Text");
        Integer result2 = fun.apply("A");
        System.out.println(result); // 1
        System.out.println(result1); // 3
        System.out.println(result2); // null
    }

    @Test
    public void predicateTest(){
        Predicate<Integer> prd = new Predicate<>() {
            @Override
            public boolean test(Integer t) {
                return t % 2 == 0;
            }
        };
        boolean result = prd.test(100);
        System.out.println(result); // true
    }



}
