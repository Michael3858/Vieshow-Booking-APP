<?php
require __DIR__ . '/conn.php';

connect();

$user_acc = $_POST["account"];
$user_pass = $_POST["password"];
$datas = array();
$mysql_qry = "select * from user where User_Email = '$user_acc' and Password = '$user_pass';";
$result = mysqli_query($con, $mysql_qry);
while ($row = mysqli_fetch_assoc($result)) {
       $datas[] = $row;
} 

print(json_encode($datas,JSON_UNESCAPED_UNICODE));
close();

?>