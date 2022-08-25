#!/bin/bash
#需要关闭和启动的java包
app=ruoyi-auth
#项目的上级目录
path=/data/apps/java
#java包存放目录
javaPath=$path/$app
#控制台日志存放目录
consolePath=$javaPath/logs
#启动时间
startTime=$(date +%Y%m%d)
#jar指定环境
active=prod
echo this is app : $app
#判断日志目录是否存在，不存在创建
if [ ! -f $javaPath/$app.jar ];
then
    echo this is $javaPath/$app.jar not found
    exit 1
fi
#判断日志目录是否存在，不存在创建
if [ ! -d $consolePath ];
then
    mkdir -p $consolePath;
fi

#若项目已启动，杀死旧进程
api_pid=`ps -ef | grep "$javaPath/$app.jar" | grep -v grep | awk '{print $2}'`
echo $api_pid

if [ "$api_pid" != "" ]; then
        echo kill api
        kill -9 $api_pid

        echo sleep 3s
        sleep 1
        echo sleep 2s
        sleep 1
        echo sleep 1s
        sleep 1
fi

echo start jar
#后台进程形式启动项目
nohup java -jar -Duser.timezone=GMT+8 -Dspring.profiles.active=$active $javaPath/$app.jar >> $consolePath/console-"$startTime".log 2>&1 &