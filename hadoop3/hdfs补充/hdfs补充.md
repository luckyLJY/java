# 一、shell脚本

--shell的操作命令
		hdfs dfs -ls / 查看根路径下面的文件或者文件夹
		hdfs dfs -mkdir -p /xx/xxx	在hdfs上面递归的创建文件夹
		hdfs dfs -moveFromLocal sourceDir(本地磁盘的文件或者文件夹的路径) destDir（hdfs的路径）
		hdfs dfs -mv hdfsourceDir hdfsDestDir 移动hdfs上的文件位置
		hdfs dfs -put localDir hdfsDir	将本地文件系统的文件或者文件夹放到hdfs上面去
		hdfs dfs -appendToFile	a.txt b.txt /hello.txt 将多个小文件合并为一个大文件
		hdfs dfs -cat hdfsFile	查看hdfs的文件内容
		hdfs dfs -cp hdfsSourceDir hdfsDestDir	拷贝文件或者文件夹
		hdfs dfs -rm [-r] File[Dir]	（递归）删除文件或者文件夹
		

		hdfs的权限管理两个命令
		hdfs dfs -chmod -R 777 /xxx 更改文件（目录）访问权限
		hdfs dfs -chown	-R hadoop:hadoop /xxx 更改文件（目录）组访问权限

# 二、windows下hadoop3.1环境搭建运行环境

第一步：将apache-hadoop-3.1.1.1文件夹拷贝到一个没有中文没有空格的路径下面

第二补：在windows下面配置hadoop的环境变量：HADOOP_HOME

第三步：把bin目录下的hadoop.dll文件放到系统盘里面去，c:\\windows\System32

第四步：关闭windows重启

