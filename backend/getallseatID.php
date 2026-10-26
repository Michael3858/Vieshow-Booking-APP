<?php
require __DIR__ . '/conn.php';

connect();

$datas = array();
$mysql_qry = "SELECT * FROM seat UNION SELECT * FROM seat_2 UNION SELECT * FROM seat_3 UNION SELECT * FROM seat_4 UNION SELECT * FROM seat_5 UNION SELECT * FROM seat_6 UNION SELECT * FROM seat_7 UNION SELECT * FROM seat_8 ORDER BY Seat_ID;";
$result = mysqli_query($con, $mysql_qry);
while ($row = mysqli_fetch_assoc($result)) {
       $datas[] = $row;
}	

print(json_encode($datas,JSON_UNESCAPED_UNICODE));
close();

?>