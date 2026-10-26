<?php
require __DIR__ . '/conn.php';

connect();

$datas = array();
$mysql_qry = "select Theater_ID, Theater_Name from theater;";
$result = mysqli_query($con, $mysql_qry);
while ($row = mysqli_fetch_assoc($result)) {
       $datas[] = $row;
}	

print(json_encode($datas,JSON_UNESCAPED_UNICODE));
close();

?>