#!/bin/bash
echo start backup $(date "+%Y-%m-%d %H:%M:%S")
#存在文件的目录
backup_dir=/data/apps/mysql_backup/qc/$(date "+%Y%m")
#数据库用户名
username=USERNAME
#数据库密码
password=PASSWORD
#端口
mysqlprot=3306
#ip
ipaddress=192.0.0.0
#拉取的实例
database=cxx
#文件的名称
filename="$database"_$(date "+%Y-%m-%d_%H:%M:%S")
echo this is filename $filename
echo this is Storage directory $backup_dir
#判断目录是否存在，不存在创建
if [ ! -d $backup_dir ];
then
    mkdir -p $backup_dir;
fi
#开始拉取数据库  将USERNAME  PASSWORD
mysqldump -u$username -P$mysqlprot -p$password -h $ipaddress --databases $database | gzip  > $backup_dir/$filename.sql.gz
#删除过期文件
delete_dir=/data/apps/mysql_backup/qc/$(date "+%Y%m" -d '2 month ago')
echo delete dir $delete_dir
if [  -d $delete_dir ];
then
    rm -rf $delete_dir;
    echo delete succeed
else
    echo delete Directory does not exist
fi
echo stop backup $(date "+%Y-%m-%d %H:%M:%S")
#使用linux crontab -e 定时任务   注意，加了锁，需要先创建一个锁文件
#30 22 * * * flock -xn /data/apps/mysql_backup/qc/.lock  -c "/data/apps/mysql_backup/qc/caqbackup.sh > /data/apps/mysql_backup/qc/runlog.log 2>&1 &"
