<?php
require __DIR__ . '/conn.php';

connect();

$ID = $_POST["ID"];
$datas = array();
$mysql_qry = "select * from movie where Movie_ID = '$ID';";
$result = mysqli_query($con, $mysql_qry);
while ($row = mysqli_fetch_assoc($result)) {
       $datas[] = $row;
}	

print(json_encode($datas,JSON_UNESCAPED_UNICODE));
close();

?>