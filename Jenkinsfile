//所有的脚本命令都放在pipeline中
pipeline{
   //指定任务在哪个集群节点中执行
   agent any

   //声明全局变量，方便后面使用
   environment {
	  key = 'value'
   }
  tools {
     gradle "gradle 6.3"  //设置gradle   通过gradle前缀在sh中引用
      jdk "jdk8"  //设置java
  }
  stages{
    stage('上传文件访问'){
       //jenkins需要安装FileParameter插件  官网用法 https://plugins.jenkins.io/file-parameters/
       //使用Stashed file parameter直接访问文件方式
        withFileParameter('FILE'){
          sh '''
             mv $FILE ./$name
           '''
         }
//          Base64 file parameter访问方式1
      sh 'echo $FILE | base64 -d'
//          Base64 file parameter访问方式2
          withFileParameter('FILE') {
              sh 'cat $FILE'
          }
    }
  }

   stages{
	   stage('拉取git创库代码'){
	      steps{
		    echo '拉取git创库代码  - SUCCESS'
		  }
	   }
	   stage('通过maven构建项目'){
	      steps{
		    echo '通过maven构建项目  - SUCCESS'

		  }
	   }
	   stage('通过node构建项目'){
       	      steps{
       		   	 nodejs('node_14') {
                 // some block
                sh '''npm install --registry=https://registry.npmmirror.com
                 npm install
                  #npm install html-webpack-plugin
                 npm run build'''
               }
       		  }
       }
	   stage('通过SonarQube做代码检测'){
	      steps{
		    echo '通过SonarQube做代码检测  - SUCCESS'

		  }
	   }
	   stage('通过Docker制作自定义镜像'){
	      steps{
		    echo '通过Docker制作自定义镜像  - SUCCESS'

		  }
	   }

	   stage('将自定义镜像推送到Harbor'){
	      steps{
		    echo '将自定义镜像推送到Harbor  - SUCCESS'
	        script {
                if("${JENKINS_ENV}" == 'qa'){
                }
                if("${JENKINS_ENV}" == 'dev'){
                }
	        }
		  }
	   }
	   stage('通过Publish  Over SSH通知目标服务器'){
	      steps{
		    echo '通过Publish  Over SSH通知目标服务器  - SUCCESS'

		  }
	   }

   }



}

//环境变量更新
//在environment已设置的环境变量
//                  withEnv(["ipv4Address=172.16.238.51"]) { // 将"MY_VARIABLE"的值更改为"newValue"
//                          echo "$ipv4Address"      // 这里放入需要运行的命令或脚本
//                  }
//创建新的环境变量
//如果在在environment已设置的环境变量，下边的环境变量更新不生效
//第一种 执行脚本
//                 env.ipv4Address= sh (
//                         script: 'echo 172.16.238.51',
//                         returnStdout: true
//                     ).trim()
//第二种 直接赋值
//env.ipv4Address="172.16.238.51"