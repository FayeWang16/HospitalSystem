

 
1、第一步, 启动 MySQL (如果没安装，还要安装一下) :

 brew services start mysql



2、然后检查 MySQL 是否在运行：

brew services list



3、成功启动 MySQL 后，通过以下命令连接到 MySQL ：

mysql -u root -p


最终设置的root密码是123456



4、已经连接到 MySQL后， 查看数据库中的表：

USE outpatient;

SHOW TABLES;



5、可以看到表之后，说明SQL文件成功导入本地。

然后运行代码，点击运行 OutpatientApplication 程序



















