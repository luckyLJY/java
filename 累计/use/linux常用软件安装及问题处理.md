### centos6.9默认mysql密码修改

1. 修改密码

   ```shell
   mysql_secure_installation
   ```

2. 重启mysql

   ```shell
    /etc/init.d/mysqld restart
   ```

3. 开启windows的远程连接权限

   ```sql
   GRANT ALL PRIVILEGES ON. TO 'root'@'%' IDENTIFIED BY 'yourpassword'
   WITH GRANT OPTION;
   FLUSH PRIVILEGES;
   ```

   

### mysql使用

1. 创建数据库、用户、密码、权限、使用库、导入数据

   ```shell
   CREATE DATABASE azkaban;
   CREATE USER 'azkaban'@'%' IDENTIFIED BY 'azkaban';
   GRANT all privileges ON azkaban.* to 'azkaban'@'%' identified by 'azkaban' WITH GRANT OPTION;
   flush privileges;
   use azkaban;
   source /export/software/create-all-sql-0.1.0-SNAPSHOT.sql;
   ```

   
