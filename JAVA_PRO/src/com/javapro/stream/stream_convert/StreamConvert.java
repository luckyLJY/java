package com.javapro.stream.stream_convert;/**
 * Project: JAVA_PRO
 * Package: com.javapro.stream.stream_convert
 * Version: 1.0
 * Created by ljy on 2021-12-6 17:40
 */

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName StreamConvert
 * @Author: ljy on 2021-12-6 17:40
 * @Version: 1.0
 * @Description:类型转换
 */
public class StreamConvert {
    public static void main(String[] args) throws Exception {
        //TODO:Stream转List
        //stream2List();
        //TODO:文件转流
        //file2Stream();
        //TODO:用map将整数转换为String
        //useMapInt2String();
        //TODO:Stream转Set
        //stream2Set();
        //TODO；Stream转数组
        //stream2Set();
        //TODO:Stream加入
        joinStream();
    }

    //TODO:Strem转List
    public static void stream2List(){
        List<String> names = Arrays.asList("Chris", "HTML", "XML", "CSS");

        Stream<String> s = names.stream().filter(name -> name.startsWith("C"));

        System.out.println(s.collect(Collectors.toList()));
    }

    /**
     * TODO:文件转流
     */
    public static void file2Stream() throws Exception{
        BufferedReader reader = Files.newBufferedReader(Paths.get("a.bat"), StandardCharsets.UTF_8);
        //BufferedReader's lines methods returns a stream of all lines
        reader.lines().forEach(System.out::println);
    }

    //TODO:用map将整数转换为String
    public static void useMapInt2String(){
        Arrays.asList(1,20,40,3)
                .stream()
                .filter(n->n<30)
                .map(n -> "number: " + String.valueOf(n))
                .forEach(n -> System.out.println(n));
    }

    //TODO:Stream转Set
    public static void stream2Set(){
        Stream<String> stream = Stream.of("a", "c", "b", "11");
        Set<String> set = stream.collect(Collectors.toSet());
        set.forEach(n-> System.out.println(n));
    }

    //TODO:Stream转数组
    public static void stream2Array(){
        Stream<String> stream = Stream.of("a", "c", "b", "11");
        String[] strings = stream.toArray(String[]::new);
        Arrays.asList(strings).forEach(n-> System.out.println(n));
    }

    //TODO:Stream加入
    public static void joinStream(){
        // collect as typed array
        Stream<String> words = Stream.of("All", "men", "are", "created", "equal");
        final String joinedWords = words.collect(Collectors.joining(" "));
        System.out.println(joinedWords);
    }
}
