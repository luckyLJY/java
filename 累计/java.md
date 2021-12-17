# 基础

## 数组转列表

```java
List<String> stooges = Arrays.asList("Larry", "Moe", "Curly");
```

## 随机数生成

```java
1.获取上述范围内的随机数：
  double d = Math.random();
2.获取一个1~100之间的随机数（int型）
  int num = (int)(Math.random()*100+1);
3.获取一个任意范围（n~m）之间的随机整数（int型）
  int num = (int)(Math.random()*(m-n+1)+n);
4.一次性生成24个整数
  String random = RandomStringUtils.random(24, false, true);
  System.out.println(random);
```

## sorted使用

```java
下面代码以自然序排序一个list
`list.stream().sorted()`

自然序逆序元素，使用Comparator 提供的reverseOrder() 方法
`list.stream().sorted(Comparator.reverseOrder())`

使用Comparator 来排序一个list
`list.stream().sorted(Comparator.comparing(Student::getAge))`

把上面的元素逆序
`list.stream().sorted(Comparator.comparing(Student::getAge).reversed())`


当然还可以不用借助steam方式直接排序：
`list.sort(Comparator.comparing(Integer::intValue));
list.sort(Comparator.comparing(Integer::intValue).reversed());
list.sort(Comparator.comparing(Student::getAge));
list.sort(Comparator.comparing(Student::getAge).reversed());`
```

# web

## token和session的区别

```
session的弊端
    1.服务器资源占用大：存储在服务器的内存中，随着用户量的增加，服务器压力增大；
    2.安全低：基于cookie进行用户识别；
    3.推展性不强：session的数据保存在单节点上，用户再次登陆可能无法获取session;
    
token:通过服务器计算token进行用户验证
```

## get和post请求

```
get请求：
    1.参数位置在URL中；
    2.能够被浏览器缓存;(2048B)
    3.参数长度有限；
    4.参数安全新低；
    5.支持浏览器访问
post请求：
    1.参数在请求体中；
    2.不能被浏览器缓存;
    3.参数长度不受限;
    4.参数安全性较好;
    5.不支持浏览器访问;
```
