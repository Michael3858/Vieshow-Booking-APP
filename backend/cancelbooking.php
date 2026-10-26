<?php
$result = file_get_contents('php://input');
$object = json_decode($result);
$Ticket_ID = $object->{'Ticket_ID'};
$User_Email = $object->{'User_Email'};
if(isset($Ticket_ID) || isset($User_Email)){
 require __DIR__ . '/conn.php';

 connect();

 $result = mysqli_query($con, "UPDATE booking_record SET State = '已取消' WHERE Ticket_ID = '$Ticket_ID' and User_email = '$User_Email';");
}

?>