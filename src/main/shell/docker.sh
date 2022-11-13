#!/bin/bash
horbar_addr=$1
horbar_repo=$2
project=$3
version=$4
container_port=$5
host_port=$6

imageName=$horbar_addr/$horbar_repo/$project:$version

echo $imageName

if [ "$horbar_addr" == "" ] ; then
        echo ERROR:horbar_addr is empty
        exit 1
fi
if [ "$horbar_repo" == "" ] ; then
        echo ERROR:horbar_repo is empty
        exit 1
fi
if [ "$project" == "" ] ; then
        echo ERROR:project is empty
        exit 1
fi
if [ "$version" == "" ] ; then
        echo ERROR:version is empty
        exit 1
fi

if [ "$container_port" == "" ] ; then
        echo ERROR:container_port is empty
        exit 1
fi
if [ "$host_port" == "" ] ; then
        echo ERROR:host_port is empty
        exit 1
fi

containerId=`docker ps -a | grep "$project"  | awk '{print $1 }'`
echo $containerId
#停止容器和删除容器
if [ "$containerId" != "" ] ; then
        docker stop  $containerId
        docker rm  $containerId
fi
#删除镜像
tag=`docker images | grep "$project" | awk '{print $2}'`
echo $tag
if [[ "$tag" =~ "$version"  ]] ; then
        docker rmi -f $imageName
fi

#拉取
docker login -u admin -p 12345 $horbar_addr
docker pull $imageName
docker run -d -p $container_port:$host_port --name $project $imageName
echo docker run --restart=always -d -p $container_port:$host_port --name $project $imageName
containerId=`docker ps  | grep "$project"  | awk '{print $1 }'`
if [ "$containerId" == "" ] ; then
        echo RUN FAIL
        exit 1
fi
echo "SUCCESS"
