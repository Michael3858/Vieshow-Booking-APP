<?php
require __DIR__ . '/conn.php';

connect();

$acc = $_POST["acc"];
$datas = array();
$mysql_qry = "SELECT User_Birthday, User_Name from user where User_Email = '$acc';";
$result = mysqli_query($con, $mysql_qry);
while ($row = mysqli_fetch_assoc($result)) {
       $datas[] = $row;
}	

print(json_encode($datas,JSON_UNESCAPED_UNICODE));
close();

?>