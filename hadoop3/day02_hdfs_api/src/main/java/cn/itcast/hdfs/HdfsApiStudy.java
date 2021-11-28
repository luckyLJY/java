package cn.itcast.hdfs;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

/**
 * @author ljy
 * @date 2021-10-6 10:46
 * @description 获取文件系统配置
 */
public class HdfsApiStudy {
    //小文件合并


    @Test
    public void mergeFile() throws  Exception{
        //获取分布式文件系统
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://192.168.75.100:8020"), new Configuration(),"hadoop");
        FSDataOutputStream outputStream = fileSystem.create(new Path("/bigfile.xml"));
        //获取本地文件系统
        LocalFileSystem local = FileSystem.getLocal(new Configuration());
        //通过本地文件系统获取文件列表，为一个集合
        FileStatus[] fileStatuses = local.listStatus(new Path("file:///F:\\传智播客大数据离线阶段课程资料\\3、大数据离线第三天\\上传小文件合并"));
        for (FileStatus fileStatus : fileStatuses) {
            FSDataInputStream inputStream = local.open(fileStatus.getPath());
            IOUtils.copy(inputStream,outputStream);
            IOUtils.closeQuietly(inputStream);
        }
        IOUtils.closeQuietly(outputStream);
        local.close();
        fileSystem.close();
    }

    //实现文件的上传
    @Test
    public void uploadFileTest() throws Exception{
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://192.168.75.100:8020"), new Configuration());
        fileSystem.copyFromLocalFile(new Path("f://a.txt"),new Path("/hello.txt"));
        fileSystem.close();

    }
    /**
     * @deprecated 文件下载方式二
     * 1.获取FileSystem对象
     * 2.copyToLocalFile(srcPath,dstPaht)下载
     *
     * 3.关闭FileSystem.close
     */
    @Test
    public void downLoadFileTest2() throws Exception{
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://192.168.75.100:8020"), new Configuration());
        fileSystem.copyToLocalFile(new Path("/test/a.txt"),new Path("f://a.txt"));
        fileSystem.close();

    }
    /**
     * @deprecated 文件的下载 方式一
     * 1.获取FileSystem对象
     * 2.获取hdfs输入流open(Path)
     * 3.获取本地的输出流new FileOutputStream(File(path))
     * 4.实现文件的复制IOUtils.copy(INputStream,OutputStream)
     * 5.关闭流IOUtils.closeQuietly(stream)
     * 6.关闭FileSystem.close
     */
    @Test
    public void downLoadFileTest() throws Exception{
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://192.168.75.100:8020"), new Configuration());
        FSDataInputStream inputStream = fileSystem.open(new Path("/test/a.txt"));
        FileOutputStream fileOutputStream = new FileOutputStream(new File("f://a.txt"));
        IOUtils.copy(inputStream,fileOutputStream);
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(fileOutputStream);
        fileSystem.close();

    }

    /**
     * @deprecated 创建文件夹
     * 1.获取FileSystem.get
     * 2.创建文件夹mkdirs(Path)路径为“/app/test/hello”
     */
    @Test
    public void mkdir() throws  Exception{
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://192.168.75.100:8020"),new Configuration());
        //fileSystem.mkdirs(new Path("/app/test/hello"));

        //创建文件
        fileSystem.create(new Path("/testA.txt"));
    }

    /**
     * @deprecated 获取目录下所有的文件信息
     * 1.获取filesystem.get
     * 2.获取指定目录下的所有文件filesystem.listFiles；返回迭代器
     * 3、遍历迭代器
     *      迭代器.hasNext()是否有下一个元素
     *      .next获取下一个文件
     *          获取文件的存储路径.getPath  文件名 .getPaht().getName()
     *          获取文件的block信息fileStatus.getBolockLocations()
     *              打印每个文件的bolck数 .getLength
     *              每一个block副本的存储位置。遍历返回的数组 blockLocation.getHosts = hosts
     */
    @Test
    public void getListFiles() throws  Exception{
        //获取fileSystem
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://192.168.75.100:8020"),new Configuration());
        //获取指定目录下文件的信息
        RemoteIterator<LocatedFileStatus> it = fileSystem.listFiles(new Path("/"), true);
        //遍历迭代器
        while (it.hasNext()){
            //获取每一个文件的信息
            LocatedFileStatus fileStatus = it.next();
            //获取文件的存储路径
            System.out.println(fileStatus.getPath()+"--"+fileStatus.getPath().getName());
            //获取文件的block信息
            BlockLocation[] blockLocations = fileStatus.getBlockLocations();
            //打印每个block数
            System.out.println(blockLocations.length);
            for (BlockLocation blockLocation :blockLocations){
                String [] hosts = blockLocation.getHosts();
                for (String host:hosts){
                    System.out.println(host+"--");
                }
            }

        }
    }

    /**
     * 第四种获取文件系统的方法
     * 使用FileSystem.newInstance,参数为uri,configuration
     */
    @Test
    public void getFileSystem4() throws Exception {
        FileSystem fileSystem = FileSystem.newInstance(new URI("hdfs://192.168.75.100:8020"),new Configuration());
        System.out.println(fileSystem.toString());
    }

    /**
     * 第三种获取文件系统的方法
     * 1.获取Configuration对象
     * 2.设置Confingration对象，指定操作文件系统
     * 3.获取指定文件系统：FileSystem.newInstance，获取主节点的所有元数据对象
     */
    @Test
    public void getFileSystem3() throws IOException {
        //获取Configuration对象
        Configuration configuration = new Configuration();
        //设置Confingration对象，指定操作文件系统
        configuration.set("fs.defaultFS","hdfs://192.168.75.100:8020");
        //获取指定文件系统：FileSystem，获取主节点的所有元数据对象
        FileSystem fileSystem = FileSystem.newInstance(configuration);
        System.out.println(fileSystem);
    }

    /**
     * 第二种获取文件系统的方法
     * 使用FileSystem.get,参数为uri,configuration
     */
    @Test
    public void getFileSystem2() throws Exception {
       FileSystem fileSystem = FileSystem.get(new URI("hdfs://192.168.75.100:8020"),new Configuration());
        System.out.println(fileSystem.toString());
    }
    /**
     * 第一种获取文件系统的方法
     * 1.获取Configuration对象
     * 2.设置Confingration对象，指定操作文件系统
     * 3.获取指定文件系统：FileSystem.get，获取主节点的所有元数据对象
     */
    @Test
    public void getFileSystem1() throws IOException {
        //获取Configuration对象
        Configuration configuration = new Configuration();
        //设置Confingration对象，指定操作文件系统
        configuration.set("fs.defaultFS","hdfs://192.168.75.100:8020");
        //获取指定文件系统：FileSystem，获取主节点的所有元数据对象
        FileSystem fileSystem = FileSystem.get(configuration);
        System.out.println(fileSystem);
    }
}
