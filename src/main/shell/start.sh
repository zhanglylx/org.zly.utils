app_path="xxxxxxxxxxx"
app=$app_path"/xxxxxxxxxxxxxxxxxxx"
app_log_path=$app_path"/logs"
app_start_code="xxxxxxxxxxxxxxxxxxxxxxxxxx"
api_pid=$(ps -ef | grep "$app" | grep -v grep | awk '{print $2}')
if [ "$api_pid" != "" ]; then
  echo The current $app pid $api_pid
  echo kill $api_pid
  kill -9 $api_pid
  echo sleep 3s
  sleep 1
  echo sleep 2s
  sleep 1
  echo sleep 1s
  sleep 1
else
  echo The current $app  is not running
fi
mkdir -p $app_log_path
nohup $app_start_code >> "$app_log_path/console.out" 2>&1 &
echo "nohup $app_start_code >> "$app_log_path/console.out" 2>&1 &"
echo Start complete
