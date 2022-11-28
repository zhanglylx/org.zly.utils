#!/bin/bash
echo 开始执行jar启动脚本 "$(date "+%Y-%m-%d %H:%M:%S")"
#需要关闭和启动的java包  -j 指定jar包名称，不指定默认为-p下的第一个jar包   -p指定路径 不指定默认为当前路径 -v 参数
app=''
#项目的上级目录
path=`pwd`
#jar启动参数   -Duser.timezone=GMT+8 -Dspring.profiles.active=prod
startupParameters=' -Duser.timezone=GMT+8 '
while getopts ":j:p:v:" opt
 do
  case $opt in
      j)
      app="$OPTARG";;
      p)
      path="$OPTARG";;
      v)
        tem="$OPTARG";
        startupParameters=$startupParameters$tem
        if [[ $tem =~ .*-Duser\.timezone=.+ ]];then
          startupParameters=$tem
        fi;;
      *)
      echo "$OPTARG 无效参数"
      exit 1;;
  esac
done
if [[ ! $path =~ /$ ]]; then path=$path'/' ;fi
if [ "$app" = "" ]; then
     echo app is null,The current directory is searched by default
     app=`find $path -maxdepth 1  -name "*.jar" | head -n 1`
     if [ "$app" = "" ]; then
       echo 没有找到当前目录下的jar: $path
       exit 1
     fi
     #去除前缀目录
     app=${app:${#path}}
fi
if [[ ! $app =~ '.jar'$ ]]; then app=$app'.jar' ;fi

#java包
java=$path$app
echo 'jar包:' $java
if [ ! -f $java ];
then
    echo this is $java not found
    exit 1
fi
#启动时间
startTime=$(date +%Y%m%d)
#控制台日志存放目录
consolePath=$path'logs/'
#判断日志目录是否存在，不存在创建
if [ ! -d $consolePath ];
then
    echo Creating a Log Directory:"$consolePath"
    mkdir -p $consolePath;
fi
consoleLog="$consolePath"console-"$startTime".log
echo '日志路径:' "$consoleLog"
#若项目已启动，杀死旧进程
api_pid=`ps -ef | grep "$java" | grep -v grep | awk '{print $2}'`
if [ "$api_pid" != "" ]; then
        echo The current JAR pid $api_pid
        echo kill $api_pid
        kill -9 $api_pid
        echo sleep 3s
        sleep 1
        echo sleep 2s
        sleep 1
        echo sleep 1s
        sleep 1
else
        echo The current JAR is not running
fi

echo starting "【nohup java -jar  $startupParameters $java >> $consoleLog 2>&1 &】"
#后台进程形式启动项目
如果jenkins执行，要防止后台启动的程序执行完被关闭掉
JENKINS_NODE_COOKIE=dontKIllMe
nohup java -jar  $startupParameters $java >> $consoleLog 2>&1 &
echo 脚本执行结束 "$(date "+%Y-%m-%d %H:%M:%S")"
echo ""