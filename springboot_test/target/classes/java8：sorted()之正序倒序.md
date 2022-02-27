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