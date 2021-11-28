package com.itguigu;/**
 * Project: Project0408
 * Package: com.itguigu
 * Version: 1.0
 * Created by ljy on 2021-11-13 15:53
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.checkerframework.checker.units.qual.C;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName HbaseDemo
 * @Author: ljy on 2021-11-13 15:53
 * @Version: 1.0
 * @Description: Hbase操作
 */
public class HbaseDemo {

    private Connection con;
    private Admin admin;
    /**
     * 读取配置并获得admin对象
     * @throws Exception
     */
    @Before
    public void init()throws   Exception{
        //1.获取hbase连接
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum","192.168.75.100:2181,192.168.75.120:2181,192.168.75.130:2181");
        con = ConnectionFactory.createConnection(conf);
        //2.创建Admin
       admin = con.getAdmin();
    }

    /**
     * 创建命名空间
     */
    @Test
    public void createNameSpace() throws IOException {

        //3.创建命名空间
        NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create("bigdata").build();
        admin.createNamespace(namespaceDescriptor);

    }

    /**
     * 删除命名空间
     * @throws Exception
     */
    @Test
    public void dropNameSpace() throws Exception{
        //1、获取命名空间所有表
        NamespaceDescriptor[] namespaceDescriptors = admin.listNamespaceDescriptors();
        List<TableDescriptor> tableDescriptors = admin.listTableDescriptorsByNamespace("bigdata".getBytes());
        for (TableDescriptor tableDescriptor: tableDescriptors){
            //1.1禁用表
            admin.disableTable(tableDescriptor.getTableName());
            //1.2删除表
            admin.deleteTable(tableDescriptor.getTableName());
        }

        //2.删除命名空间
        admin.deleteNamespace("bigdata");
    }

    /**
     * 显示所有的命名空间
     * @throws Exception
     */
    @Test
    public void listNamespace()throws Exception{
        NamespaceDescriptor[] namespaceDescriptors = admin.listNamespaceDescriptors();
        for (NamespaceDescriptor namespaceDescriptor: namespaceDescriptors){
            System.out.println(namespaceDescriptor.getName());
        }
    }

    ///////////////////////////////////////////////////命名空间操作END////////////////////////////////////////////////


    /**
     * 创建表
     */
    @Test
    public void createTable() throws Exception{

        //创建列族描述
        ColumnFamilyDescriptor base_info = ColumnFamilyDescriptorBuilder.newBuilder("base_info".getBytes()).build();
        ColumnFamilyDescriptor extra_info = ColumnFamilyDescriptorBuilder.newBuilder("extra_info".getBytes()).build();

        //创建表描述
        TableDescriptor tableDescriptor = TableDescriptorBuilder.newBuilder(TableName.valueOf("bigdata:person"))
                .setColumnFamily(base_info)
                .setColumnFamily(extra_info)
                .build();
        admin.createTable(tableDescriptor);
    }

    /**
     * 获取指定命名空间下的所有表
     * @throws Exception
     */
    @Test
    public void listTable()throws Exception{
        List<TableDescriptor> tableDescriptors = admin.listTableDescriptorsByNamespace("bigdata".getBytes());
        for (TableDescriptor tableDescriptor: tableDescriptors){
            System.out.println(new String(tableDescriptor.getTableName().getName()));
        }
    }

    /**
     * 使用Get插入一条数
     * @throws Exception
     */
    @Test
   public void put() throws Exception{
        //1.创建table对象
       Table table = con.getTable(TableName.valueOf("bigdata:person"));
       //2.插入数据
       Put put = new Put("1000".getBytes());
       put.addColumn("base_info".getBytes(),"name".getBytes(),"zhangsan".getBytes());
       put.addColumn("base_info".getBytes(),"name".getBytes(), Bytes.toBytes(20));
       table.put(put);

       //3.关闭table
       table.close();
   }

    /**
     * 批量插入数据
     * @throws Exception
     */
   @Test
   public void putList() throws Exception{
       //1.获取table
       Table table = con.getTable(TableName.valueOf("bigdata:person"));

       //2.构建添加数据
       ArrayList<Put> puts= new ArrayList<Put>();
       Put put=null;
       for(int i=0;i<10;i++){
           put = new Put(("1001"+i).getBytes());
           put.addColumn("base_info".getBytes(),"name".getBytes(),("zhangsan"+i).getBytes());
           put.addColumn("base_info".getBytes(),"age".getBytes(),Bytes.toBytes(20+i));
           put.addColumn("extra_info".getBytes(),"address".getBytes(),("shenzhen"+i).getBytes());
           puts.add(put);
       }
       table.put(puts);
       //3.关闭table
       table.close();
   }

    /**
     * 修改表
     * @throws Exception
     */
   @Test
   public void alterTable() throws Exception{
       ColumnFamilyDescriptor familyDescriptor = ColumnFamilyDescriptorBuilder.newBuilder(("extra_info".getBytes())).setMinVersions(2).setMaxVersions(2).build();
       TableDescriptor tableDescriptor = TableDescriptorBuilder.newBuilder(TableName.valueOf("bigdata:person")).setColumnFamily(familyDescriptor).build();
       admin.modifyTable(tableDescriptor);
   }

    /**
     * 获取所有表
     * @throws Exception
     */
   @Test
   public void listAllTable()throws Exception{
       TableName[] tableNames = admin.listTableNames();
       for (TableName tableName:tableNames){
           System.out.println(new String(tableName.getName()));
       }
   }

    /**
     * 删除表
     * @throws Exception
     */
   @Test
   public void dropTable() throws Exception{
       //关闭表
       admin.disableTable(TableName.valueOf("user"));
       //删除表
       admin.deleteTable(TableName.valueOf("user"));
   }

   ///////////////////////////////////////////////////////表操作END/////////////////////////////////////


    /**
     * 使用Get查询整行数据
     * @throws Exception
     */
    @Test
    public void  get() throws Exception{
       //获取Table对象
        Table table = con.getTable(TableName.valueOf("bigdata:person"));
        //查询使用Get
        Get get = new Get("10011".getBytes());
        Result result = table.get(get);
        //显示数据
        List<Cell> cells = result.listCells();
        for (Cell cell:cells){
            //列族 列限定 值
            String family = new String(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength());
            String qualifier = new String(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
            String rowkey = new String(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());
            if (family.equals("base_info") && qualifier.equals("age")){
                int value = Bytes.toInt(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                System.out.println(family+"--"+qualifier+"--"+value);
            }else {
               String value= new String(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                System.out.println(family+"--"+qualifier+"--"+value);
            }
        }
        //关闭table
        table.close();
    }

    /**
     * 使用Get获取整个列族对象
     * @throws Exception
     */
    @Test
    public void  get1() throws Exception{
        //获取Table对象
        Table table = con.getTable(TableName.valueOf("bigdata:person"));
        //查询使用Get
        Get get = new Get("10011".getBytes());
        get.addFamily("base_info".getBytes());
        get.addColumn("extra_info".getBytes(),"address".getBytes());
        Result result = table.get(get);
        //显示数据
        List<Cell> cells = result.listCells();
        for (Cell cell:cells){
            String family = new String(CellUtil.cloneFamily(cell));
            String qualifier = new String(CellUtil.cloneQualifier(cell));
            String rowkey = new String(CellUtil.cloneRow(cell));
            if (family.equals("base_info") && qualifier.equals("age")){
                int value = Bytes.toInt(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                System.out.println(family+"--"+qualifier+"--"+value);
            }else {
                String value= new String(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                System.out.println(family+"--"+qualifier+"--"+value);
            }
        }

        //关闭table
        table.close();
    }

    /**
     * 使用Get批量查询
     * @throws Exception
     */
    @Test
    public void getBatch() throws Exception{
        //获取Table对象
        Table table = con.getTable(TableName.valueOf("bigdata:person"));
        //查询使用Get
        List<Get> gets = new ArrayList<Get>();
        Get get=null;
        for (int i=3;i<6;i++){
            get = new Get(("1001"+i).getBytes());
            get.addFamily("base_info".getBytes());
            get.addColumn("extra_info".getBytes(),"address".getBytes());
            gets.add(get);
        }

        Result[] results = table.get(gets);
        //显示数据

        for (Result result:results){
            List<Cell> cells = result.listCells();
            for (Cell cell:cells){
                String family = new String(CellUtil.cloneFamily(cell));
                String qualifier = new String(CellUtil.cloneQualifier(cell));
                String rowkey = new String(CellUtil.cloneRow(cell));
                if (family.equals("base_info") && qualifier.equals("age")){
                    int value = Bytes.toInt(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                    System.out.println(family+"--"+qualifier+"--"+value);
                }else {
                    String value= new String(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                    System.out.println(family+"--"+qualifier+"--"+value);
                }
            }
        }

        //关闭table
        table.close();
    }

    /////////////////////////////////////////GetEND//////////////////////////////////////////

    /**
     * 使用scan查询数据
     * @throws Exception
     */
    @Test
    public void scan()throws Exception{
        //获取table
        Table table = con.getTable(TableName.valueOf("bigdata:person"));
        //查询数据
        Scan scan =new Scan();
        //查询某个列族
        scan.addFamily("base_info".getBytes());

        ResultScanner scanner = table.getScanner(scan);
        //查询数据
        Iterator<Result> iterator = scanner.iterator();
        while (iterator.hasNext()){
            Result result = iterator.next();
            List<Cell> cells = result.listCells();
            for (Cell cell:cells){
                String family = new String(CellUtil.cloneFamily(cell));
                String qualifier = new String(CellUtil.cloneQualifier(cell));
                String rowkey = new String(CellUtil.cloneRow(cell));
                if (family.equals("base_info") && qualifier.equals("age")){
                    int value = Bytes.toInt(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                    System.out.println(family+"--"+qualifier+"--"+value);
                }else {
                    String value= new String(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                    System.out.println(family+"--"+qualifier+"--"+value);
                }
            }
        }
        //关闭
        table.close();
    }

    ////////////////////scan//////////////////////////////////


    /**
     * filter使用:根据值查询到一整条数据
     * @throws Exception
     */
    @Test
    public void filter1() throws Exception{
        //获取table
        Table table = con.getTable(TableName.valueOf("bigdata:person"));
        //查询数据
        Scan scan =new Scan();
        //根据值过滤，只显示单个cell
        /*BinaryComparator longComparator = new BinaryComparator(Bytes.toBytes(25));
        ValueFilter valueFilter = new ValueFilter(CompareOperator.EQUAL, longComparator);
        scan.setFilter(valueFilter);*/
        //根据value进行过滤，显示整行数据
        SingleColumnValueFilter valueFilter = new SingleColumnValueFilter("base_info".getBytes(), "age".getBytes(), CompareOperator.EQUAL, Bytes.toBytes(25));
        scan.setFilter(valueFilter);
        ResultScanner scanner = table.getScanner(scan);
        //查询数据
        Iterator<Result> iterator = scanner.iterator();
        while (iterator.hasNext()){
            Result result = iterator.next();
            List<Cell> cells = result.listCells();
            for (Cell cell:cells){
                String family = new String(CellUtil.cloneFamily(cell));
                String qualifier = new String(CellUtil.cloneQualifier(cell));
                String rowkey = new String(CellUtil.cloneRow(cell));
                if (family.equals("base_info") && qualifier.equals("age")){
                    int value = Bytes.toInt(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                    System.out.println(family+"--"+qualifier+"--"+value);
                }else {
                    String value= new String(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                    System.out.println(family+"--"+qualifier+"--"+value);
                }
            }
        }
        //关闭
        table.close();
    }


    /**
     * 模糊查询
     * @throws Exception
     */
    @Test
    public void filterByLike() throws  Exception{
        //获取table
        Table table = con.getTable(TableName.valueOf("bigdata:person"));
        //查询数据
        Scan scan =new Scan();
        //模糊查询
        SubstringComparator comparator = new SubstringComparator("5");
        /*ValueFilter valueFilter = new ValueFilter(CompareOperator.EQUAL,comparator);*/

        SingleColumnValueFilter filter = new SingleColumnValueFilter("base_info".getBytes(), "name".getBytes(), CompareOperator.EQUAL, comparator);
        scan.setFilter(filter);
        ResultScanner scanner = table.getScanner(scan);
        //查询数据
        Iterator<Result> iterator = scanner.iterator();
        while (iterator.hasNext()){
            Result result = iterator.next();
            List<Cell> cells = result.listCells();
            for (Cell cell:cells){
                String family = new String(CellUtil.cloneFamily(cell));
                String qualifier = new String(CellUtil.cloneQualifier(cell));
                String rowkey = new String(CellUtil.cloneRow(cell));
                if (family.equals("base_info") && qualifier.equals("age")){
                    int value = Bytes.toInt(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                    System.out.println(family+"--"+qualifier+"--"+value);
                }else {
                    String value= new String(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                    System.out.println(family+"--"+qualifier+"--"+value);
                }
            }
        }
        //关闭
        table.close();
    }

    /**
     * 多条件查询
     * @throws Exception
     */
    @Test
    public void fileterByMuti()throws Exception{
        //获取table
        Table table = con.getTable(TableName.valueOf("bigdata:person"));
        //查询数据
        Scan scan =new Scan();
        //复合
        SingleColumnValueFilter like = new SingleColumnValueFilter("base_info".getBytes(), "name".getBytes(), CompareOperator.EQUAL, new SubstringComparator("san"));
        SingleColumnValueFilter greateAge = new SingleColumnValueFilter("base_info".getBytes(), "age".getBytes(), CompareOperator.GREATER, Bytes.toBytes(20));
        SingleColumnValueFilter name = new SingleColumnValueFilter("base_info".getBytes(), "name".getBytes(), CompareOperator.EQUAL, Bytes.toBytes("zhangsan4"));

        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        filterList.addFilter(like);

        FilterList nameAndAge= new FilterList(FilterList.Operator.MUST_PASS_ONE);
        nameAndAge.addFilter(greateAge);
        nameAndAge.addFilter(name);

        filterList.addFilter(nameAndAge);

        scan.setFilter(filterList);
        ResultScanner scanner = table.getScanner(scan);
        //查询数据
        Iterator<Result> iterator = scanner.iterator();
        while (iterator.hasNext()){
            Result result = iterator.next();
            List<Cell> cells = result.listCells();
            for (Cell cell:cells){
                String family = new String(CellUtil.cloneFamily(cell));
                String qualifier = new String(CellUtil.cloneQualifier(cell));
                String rowkey = new String(CellUtil.cloneRow(cell));
                if (family.equals("base_info") && qualifier.equals("age")){
                    int value = Bytes.toInt(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                    System.out.println(family+"--"+qualifier+"--"+value);
                }else {
                    String value= new String(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                    System.out.println(family+"--"+qualifier+"--"+value);
                }
            }
        }
        //关闭
        table.close();
    }

    ////////////////////filter///////////////////////

    /**
     * rangeScan根据列查询
     * @throws Exception
     */
    @Test
    public void rangeScan()throws Exception{
        //获取table
        Table table = con.getTable(TableName.valueOf("bigdata:person"));
        //查询数据
        Scan scan =new Scan();
        //根据列查询
        ColumnRangeFilter filter = new ColumnRangeFilter("base_info:name".getBytes(), true, null, false);

        scan.setFilter(filter);
        ResultScanner scanner = table.getScanner(scan);
        //查询数据
        Iterator<Result> iterator = scanner.iterator();
        while (iterator.hasNext()){
            Result result = iterator.next();
            List<Cell> cells = result.listCells();
            for (Cell cell:cells){
                String family = new String(CellUtil.cloneFamily(cell));
                String qualifier = new String(CellUtil.cloneQualifier(cell));
                String rowkey = new String(CellUtil.cloneRow(cell));
                if (family.equals("base_info") && qualifier.equals("age")){
                    int value = Bytes.toInt(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                    System.out.println(family+"--"+qualifier+"--"+value);
                }else {
                    String value= new String(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                    System.out.println(family+"--"+qualifier+"--"+value);
                }
            }
        }
        //关闭
        table.close();
    }

    /**
     * 删除数据
     * @throws Exception
     */
    @Test
    public void delete() throws    Exception{
        //获取table
        Table table = con.getTable(TableName.valueOf("bigdata:person"));
        //删除
        Delete delete = new Delete("10011".getBytes());
        table.delete(delete);
        //关闭table
        table.close();
    }

    /**
     * 批量删除
     * @throws Exception
     */
    @Test
    public void deleteBatch() throws Exception{
        //获取table
        Table table = con.getTable(TableName.valueOf("bigdata:person"));
        List<Delete> deleteList = new ArrayList<Delete>();
        Delete delete=null;
        for(int i=4;i<7;i++){

            delete = new Delete(("1001"+i).getBytes());
            deleteList.add(delete);
        }

        table.delete(deleteList);
        //关闭table
        table.close();
    }
    /**
     * 关闭连接
     * @throws Exception
     */
    @After
    public void close()throws Exception{
        //4.关闭连接
        if(admin != null){
            admin.close();
            if (con!=null){
                con.close();
            }
        }
    }
}
