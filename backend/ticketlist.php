<?php
require __DIR__ . '/conn.php';

connect();

$ID = $_POST["ID"];
$datas = array();
$mysql_qry = "SELECT Type_Name, Type_Price FROM ticket_type WHERE Type_Classify = '臨櫃票種';";
$result = mysqli_query($con, $mysql_qry);
while ($row = mysqli_fetch_assoc($result)) {
       $datas[] = $row;
}	

print(json_encode($datas,JSON_UNESCAPED_UNICODE));
close();

?>