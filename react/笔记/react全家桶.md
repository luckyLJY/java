# 一、React简介

- 是一个将数据渲染为HTML视图的开源JavaScript库
- Facebook开发，且开源

## react特点

1. 采用组件化模式、声明式编码，提高开发效率及组件复用率。
2. 在React Native中可以使用React语法进行移动端开发。
3. 使用虚拟DDOM和优秀的Diffing算法，尽量减少与真实DOM的交互。

## 基础知识

- 判断this的指向
- class类
- ES6语法规范
- npm包管理
- 原型、原型链
- 数组常用方法
- 模块化

# 二、React入门

## 官网

http://reactjs.org

http://react.docschina.org

## Hello React案例

```html
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>hello_react</title>
    </head>
    <body>
        <!--准备好一个容器-->
        <div id=text></div>

        <!-- 引入react核心库 -->
        <script type="text/javascript" src="../js/react.development.js"></script>
        <!-- 引入react-dom,用于支持react操作DOM -->
        <script type="text/javascript" src="../js/react-dom.development.js"></script>
        <!-- 引入babel，用于将jsx转为js-->
        <script type="text/javascript" src="../js/babel.min.js"></script>
        <script type="text/babel"> /* 此处一定要写babel*/
            //1. 创建虚拟dom
            const VDOM = <h1>Hello,React</h1>/*此处一定不要写引号，因为不是字符串*/
            //2. 渲染虚拟DOM到页面
            ReactDOM.render(VDOM,document.getElementById('text'))
        </script>
    </body>
</html>
```

## 创建虚拟DOM的两种方式

1. 通过jsx
2. 通过js