<?php
require __DIR__ . '/conn.php';

connect();

$ID = $_POST["ID"];
$datas = array();
$mysql_qry = "SELECT * FROM booking_record, booking_seat WHERE booking_record.Ticket_ID = booking_seat.Ticket_ID AND state != '已取消' AND Screenings_ID = '$ID';";
$result = mysqli_query($con, $mysql_qry);
while ($row = mysqli_fetch_assoc($result)) {
       $datas[] = $row;
}	

print(json_encode($datas,JSON_UNESCAPED_UNICODE));
close();

?>