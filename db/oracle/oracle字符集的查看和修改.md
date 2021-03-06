# oracle字符集的查看和修改
## 一、什么是Oracle字符集

       Oracle字符集是一个字节数据的解释的符号集合,有大小之分,有相互的包容关系。ORACLE 支持国家语言的体系结构允许你使用本地化语言来存储，处理，检索数据。它使数据库工具，错误消息，排序次序，日期，时间，货币，数字，和日历自动适应本地化语言和平台。

 

影响Oracle数据库字符集最重要的参数是NLS_LANG参数。

它的格式如下: NLS_LANG = language_territory.charset

它有三个组成部分(语言、地域和字符集)，每个成分控制了NLS子集的特性。

其中:

Language： 指定服务器消息的语言， 影响提示信息是中文还是英文

Territory： 指定服务器的日期和数字格式，

Charset：  指定字符集。

如:AMERICAN _ AMERICA. ZHS16GBK

从NLS_LANG的组成我们可以看出，真正影响数据库字符集的其实是第三部分。

所以两个数据库之间的字符集只要第三部分一样就可以相互导入导出数据，前面影响的只是提示信息是中文还是英文。

 

## 二．字符集的相关知识：  
2.1 字符集
    实质就是按照一定的字符编码方案，对一组特定的符号，分别赋予不同数值编码的集合。Oracle数据库最早支持的编码方案是US7ASCII。
    Oracle的字符集命名遵循以下命名规则:
    <Language><bit size><encoding>
    即: <语言><比特位数><编码>
    比如: ZHS16GBK表示采用GBK编码格式、16位（两个字节）简体中文字符集
 
2.2 字符编码方案


2.2.1 单字节编码  
    （1）单字节7位字符集，可以定义128个字符，最常用的字符集为US7ASCII  
    （2）单字节8位字符集，可以定义256个字符，适合于欧洲大部分国家
             例如：WE8ISO8859P1(西欧、8位、ISO标准8859P1编码)

 

2.2.2 多字节编码  
    （1）变长多字节编码
    某些字符用一个字节表示，其它字符用两个或多个字符表示，变长多字节编码常用于对亚洲语言的支持，   例如日语、汉语、印地语等
    例如：AL32UTF8（其中AL代表ALL,指适用于所有语言）、zhs16cgb231280  
    （2）定长多字节编码
    每一个字符都使用固定长度字节的编码方案，目前oracle唯一支持的定长多字节编码是AF16UTF16，也是仅用于国家字符集

2.2.3 unicode编码  
    Unicode是一个涵盖了目前全世界使用的所有已知字符的单一编码方案，也就是说Unicode为每一个字符提供唯一的编码。UTF-16是unicode的16位编码方式，是一种定长多字节编码，用2个字节表示一个unicode字符，AF16UTF16是UTF-16编码字符集。  
    UTF-8是unicode的8位编码方式，是一种变长多字节编码，这种编码可以用1、2、3个字节表示一个unicode字符，AL32UTF8，UTF8、UTFE是UTF-8编码字符集
 
2.3 字符集超级  
    当一种字符集（字符集A）的编码数值包含所有另一种字符集（字符集B）的编码数值，并且两种字符集相同编码数值代表相同的字符时，则字符集A是字符集B的超级，或称字符集B是字符集A的子集。  
    Oracle8i和oracle9i官方文档资料中备有子集-超级对照表（subset-superset pairs），例如：WE8ISO8859P1是WE8MSWIN1252的子集。由于US7ASCII是最早的Oracle数据库编码格式，因此有许多字符集是US7ASCII的超集，例如WE8ISO8859P1、ZHS16CGB231280、ZHS16GBK都是US7ASCII的超集。  
 
2.4 数据库字符集（oracle服务器端字符集）  
    数据库字符集在创建数据库时指定，在创建后通常不能更改。在创建数据库时，可以指定字符集(CHARACTER SET)和国家字符集(NATIONAL CHARACTER SET)。

 

2.4.1字符集  
    (1)用来存储CHAR, VARCHAR2, CLOB, LONG等类型数据  
    (2)用来标示诸如表名、列名以及PL/SQL变量等  
    (3)用来存储SQL和PL/SQL程序单元等  

 

2.4.2国家字符集：  
    (1)用以存储NCHAR,   NVARCHAR2, NCLOB等类型数据  
    (2)国家字符集实质上是为oracle选择的附加字符集，主要作用是为了增强oracle的字符处理能力，因为NCHAR数据类型可以提供对亚洲使用定长多字节编码的支持，而数据库字符集则不能。国家字符集在oracle9i中进行了重新定义，只能在unicode编码中的AF16UTF16和UTF8中选择，默认值是AF16UTF16  

 

2.4.3查询字符集参数  
    可以查询以下数据字典或视图查看字符集设置情况
    nls_database_parameters、props$、v$nls_parameters
    查询结果中NLS_CHARACTERSET表示字符集，NLS_NCHAR_CHARACTERSET表示国家字符集

 

2.4.4修改数据库字符集  
    按照上文所说，数据库字符集在创建后原则上不能更改。不过有2种方法可行。

 

1. 如果需要修改字符集，通常需要导出数据库数据，重建数据库，再导入数据库数据的方式来转换。

2. 通过ALTER DATABASE CHARACTER SET语句修改字符集，但创建数据库后修改字符集是有限制的，只有新的字符集是当前字符集的超集时才能修改数据库字符集，例如UTF8是US7ASCII的超集，修改数据库字符集可使用ALTER DATABASE CHARACTER SET UTF8。
 
2.5 客户端字符集（NLS_LANG参数）


2.5.1客户端字符集含义  
    客户端字符集定义了客户端字符数据的编码方式，任何发自或发往客户端的字符数据均使用客户端定义的字符集编码,客户端可以看作是能与数据库直接连接的各种应用，例如sqlplus,exp/imp等。客户端字符集是通过设置NLS_LANG参数来设定的。

 

2.5.2 NLS_LANG参数格式  
    NLS_LANG=<language>_<territory>.<client character set>
    Language: 显示oracle消息,校验，日期命名
    Territory：指定默认日期、数字、货币等格式
    Client character set：指定客户端将使用的字符集
    例如：NLS_LANG=AMERICAN_AMERICA.US7ASCII
    AMERICAN是语言，AMERICA是地区，US7ASCII是客户端字符集

 

2.5.3客户端字符集设置方法  
     1)UNIX环境  
         $NLS_LANG=“simplified chinese”_china.zhs16gbk
         $export NLS_LANG
         编辑oracle用户的profile文件  
    2)Windows环境  
         编辑注册表
         Regedit.exe ---》 HKEY_LOCAL_MACHINE ---》SOFTWARE ---》 ORACLE-HOME

 

2.5.4 NLS参数查询  
    Oracle提供若干NLS参数定制数据库和用户机以适应本地格式，例如有NLS_LANGUAGE,NLS_DATE_FORMAT,NLS_CALENDER等，可以通过查询以下数据字典或v$视图查看。  
NLS_DATABASE_PARAMETERS:显示数据库当前NLS参数取值，包括数据库字符集取值
NLS_SESSION_PARAMETERS：  显示由NLS_LANG 设置的参数，或经过alter session 改变后的参数值（不包括由NLS_LANG 设置的客户端字符集）
NLS_INSTANCE_PARAMETE： 显示由参数文件init<SID>.ora 定义的参数

V$NLS_PARAMETERS：显示数据库当前NLS参数取值

 

2.5.5修改NLS参数  
    使用下列方法可以修改NLS参数  
    （1）修改实例启动时使用的初始化参数文件  
    （2）修改环境变量NLS_LANG  
    （3）使用ALTER SESSION语句，在oracle会话中修改  
    （4）使用某些SQL函数
    NLS作用优先级别：Sql function > alter session > 环境变量或注册表 > 参数文件 > 数据库默认参数

 

三．EXP/IMP 与 字符集

3.1 EXP/IMP  
    Export 和 Import 是一对读写Oracle数据的工具。Export 将 Oracle 数据库中的数据输出到操作系统文件中, Import 把这些文件中的数据读到Oracle 数据库中，由于使用exp/imp进行数据迁移时，数据从源数据库到目标数据库的过程中有四个环节涉及到字符集，如果这四个环节的字符集不一致，将会发生字符集转换。  
EXP  
     ____________ _________________ _____________  
     |imp导入文件|<-|环境变量NLS_LANG|<-|数据库字符集|  
      ------------   -----------------   -------------

IMP  
     ____________ _________________ _____________  
     |imp导入文件|->|环境变量NLS_LANG|->|数据库字符集|  
      ------------   -----------------   -------------

 

 

四个字符集是  
   （1）源数据库字符集  
   （2）Export过程中用户会话字符集（通过NLS_LANG设定）  
   （3）Import过程中用户会话字符集（通过NLS_LANG设定）  
   （4）目标数据库字符集
 
3.2导出的转换过程  
    在Export过程中，如果源数据库字符集与Export用户会话字符集不一致，会发生字符集转换，并在导出文件的头部几个字节中存储Export用户会话字符集的ID号。在这个转换过程中可能发生数据的丢失。


例:如果源数据库使用ZHS16GBK，而Export用户会话字符集使用US7ASCII，由于ZHS16GBK是16位字符集,而US7ASCII是7位字符集，这个转换过程中，中文字符在US7ASCII中不能够找到对等的字符，所以所有中文字符都会丢失而变成“?? ”形式，这样转换后生成的Dmp文件已经发生了数据丢失。
因此如果想正确导出源数据库数据，则Export过程中用户会话字符集应等于源数据库字符集或是源数据库字符集的超集
 
3.3导入的转换过程  
    （1）确定导出数据库字符集环境
             通过读取导出文件头，可以获得导出文件的字符集设置  
    （2）确定导入session的字符集，即导入Session使用的NLS_LANG环境变量  
    （3）IMP读取导出文件
             读取导出文件字符集ID，和导入进程的NLS_LANG进行比较  
    （4）如果导出文件字符集和导入Session字符集相同，那么在这一步骤内就不需要转换，             如果不同，就需要把数据转换为导入Session使用的字符集。可以看出，导入数据到数据库过程中发生两次字符集转换


    第一次:导入文件字符集与导入Session使用的字符集之间的转换，如果这个转换过程不能正确完成，Import向目标数据库的导入过程也就不能完成。
    第二次:导入Session字符集与数据库字符集之间的转换。

 

四. 查看数据库字符集

涉及三方面的字符集，

1. oracel server端的字符集;

2. oracle client端的字符集;

3. dmp文件的字符集。

 

在做数据导入的时候，需要这三个字符集都一致才能正确导入。

 

4.1 查询oracle server端的字符集

有很多种方法可以查出oracle server端的字符集，比较直观的查询方法是以下这种:

SQL> select userenv('language') from dual;

USERENV('LANGUAGE')

----------------------------------------------------

SIMPLIFIED CHINESE_CHINA.ZHS16GBK

 

SQL>select userenv(‘language’) from dual;

AMERICAN _ AMERICA. ZHS16GBK

 

4.2 如何查询dmp文件的字符集

用oracle的exp工具导出的dmp文件也包含了字符集信息，dmp文件的第2和第3个字节记录了dmp文件的字符集。如果dmp文件不大，比如只有几M或几十M，可以用UltraEdit打开(16进制方式)，看第2第3个字节的内容，如0354，然后用以下SQL查出它对应的字符集:

SQL> select nls_charset_name(to_number('0354','xxxx')) from dual;

ZHS16GBK

 

如果dmp文件很大，比如有2G以上(这也是最常见的情况)，用文本编辑器打开很慢或者完全打不开，可以用以下命令(在unix主机上):

cat exp.dmp |od -x|head -1|awk '{print $2 $3}'|cut -c 3-6

然后用上述SQL也可以得到它对应的字符集。

 

4.3 查询oracle client端的字符集

在windows平台下，就是注册表里面相应OracleHome的NLS_LANG。还可以在dos窗口里面自己设置，

比如: set nls_lang=AMERICAN_AMERICA.ZHS16GBK

这样就只影响这个窗口里面的环境变量。

 

在unix平台下，就是环境变量NLS_LANG。

$echo $NLS_LANG

AMERICAN_AMERICA.ZHS16GBK

 

如果检查的结果发现server端与client端字符集不一致，请统一修改为同server端相同的字符集。

 

补充：

(1).数据库服务器字符集

select * from nls_database_parameters

来源于props$，是表示数据库的字符集。

 

(2).客户端字符集环境

select * from nls_instance_parameters

其来源于v$parameter，表示客户端的字符集的设置，可能是参数文件，环境变量或者是注册表

 

(3).会话字符集环境

select * from nls_session_parameters

来源于v$nls_parameters，表示会话自己的设置，可能是会话的环境变量或者是alter session完成，如果会话没有特殊的设置，将与nls_instance_parameters一致。

 

(4).客户端的字符集要求与服务器一致，才能正确显示数据库的非Ascii字符。

如果多个设置存在的时候，NLS作用优先级别：Sql function > alter session > 环境变量或注册表 > 参数文件 > 数据库默认参数

 

字符集要求一致，但是语言设置却可以不同，语言设置建议用英文。如字符集是zhs16gbk，则nls_lang可以是American_America.zhs16gbk。

 

 

五． 修改oracle的字符集

按照上文所说，数据库字符集在创建后原则上不能更改。因此，在设计和安装之初考虑使用哪一种字符集十分重要。对数据库server而言，错误的修改字符集将会导致很多不可测的后果，可能会严重影响数据库的正常运行，所以在修改之前一定要确认两种字符集是否存在子集和超集的关系。一般来说，除非万不得已，我们不建议修改oracle数据库server端的字符集。特别说明，我们最常用的两种字符集ZHS16GBK和ZHS16CGB231280之间不存在子集和超集关系，因此理论上讲这两种字符集之间的相互转换不受支持。

 

不过修改字符集有2种方法可行。

1. 通常需要导出数据库数据，重建数据库，再导入数据库数据的方式来转换。

2. 通过ALTER DATABASE CHARACTER SET语句修改字符集，但创建数据库后修改字符集是有限制的，只有新的字符集是当前字符集的超集时才能修改数据库字符集，例如UTF8是US7ASCII的超集，修改数据库字符集可使用ALTER DATABASE CHARACTER SET UTF8。
 

 

5.1 修改server端字符集(不建议使用)

 

1. 关闭数据库 

    SQL>SHUTDOWN IMMEDIATE   
 
2. 启动到Mount

    SQL>STARTUP MOUNT;
    SQL>ALTER SYSTEM ENABLE RESTRICTED SESSION;
    SQL>ALTER SYSTEM SET JOB_QUEUE_PROCESSES=0;
    SQL>ALTER SYSTEM SET AQ_TM_PROCESSES=0;
    SQL>ALTER DATABASE OPEN;
 


--这里可以从父集到子集

SQL>ALTER DATABASE CHARACTER SET ZHS16GBK;

SQL>ALTER DATABASE NATIONAL CHARACTER SET ZHS16GBK;


--如果是从子集到父集，需要使用INTERNAL_USE 参数，跳过超子集检测

SQL>ALTER DATABASE CHARACTER SET INTERNAL_USE AL32UTF8;

SQL>ALTER DATABASE NATIONAL CHARACTER SET INTERNAL_USE AL32UTF8;

 

 

SQL>SHUTDOWN IMMEDIATE;

 

SQL>STARTUP

注意：如果没有大对象，在使用过程中进行语言转换没有什么影响，（切记设定的字符集必须是ORACLE支持，不然不能start） 按上面的做法就可以。

 

若出现‘ORA-12717: Cannot ALTER DATABASE NATIONAL CHARACTER SET when NCLOB data exists’ 这样的提示信息，

要解决这个问题有两种方法

1. 利用INTERNAL_USE 关键字修改区域设置,

2. 利用re-create,但是re-create有点复杂,所以请用internal_use

 

SQL>SHUTDOWN IMMEDIATE;

SQL>STARTUP MOUNT EXCLUSIVE;

SQL>ALTER SYSTEM ENABLE RESTRICTED SESSION;

SQL>ALTER SYSTEM SET JOB_QUEUE_PROCESSES=0;

SQL>ALTER SYSTEM SET AQ_TM_PROCESSES=0;

SQL>ALTER DATABASE OPEN;

SQL>ALTER DATABASE NATIONAL CHARACTER SET INTERNAL_USE UTF8;

SQL>SHUTDOWN immediate;

SQL>startup;

如果按上面的做法做,National charset的区域设置就没有问题

 

5.2 修改dmp文件字符集

上文说过，dmp文件的第2第3字节记录了字符集信息，因此直接修改dmp文件的第2第3字节的内容就可以‘骗’过oracle的检查。这样做理论上也仅是从子集到超集可以修改，但很多情况下在没有子集和超集关系的情况下也可以修改，我们常用的一些字符集，如US7ASCII，WE8ISO8859P1，ZHS16CGB231280，ZHS16GBK基本都可以改。因为改的只是dmp文件，所以影响不大。

 

具体的修改方法比较多，最简单的就是直接用UltraEdit修改dmp文件的第2和第3个字节。

比如想将dmp文件的字符集改为ZHS16GBK，可以用以下SQL查出该种字符集对应的16进制代码: SQL> select to_char(nls_charset_id('ZHS16GBK'), 'xxxx') from dual;

0354

然后将dmp文件的2、3字节修改为0354即可。

如果dmp文件很大，用ue无法打开，就需要用程序的方法了。

 

5.3客户端字符集设置方法
     1)UNIX环境
         $NLS_LANG=“simplified chinese”_china.zhs16gbk
         $export NLS_LANG
         编辑oracle用户的profile文件
    2)Windows环境
         编辑注册表
         Regedit.exe ---》 HKEY_LOCAL_MACHINE ---》SOFTWARE ---》 ORACLE-HOME

 或者在窗口设置：

        set nls_lang=AMERICAN_AMERICA.ZHS16GBK
