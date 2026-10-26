<?php
$result = file_get_contents('php://input');
$object = json_decode($result);
$Ticket_ID = $object->{'Ticket_ID'};
if(isset($Ticket_ID)){
 require __DIR__ . '/conn.php';

 connect();

 $result = mysqli_query($con, "DELETE FROM booking_seat WHERE Ticket_ID = '$Ticket_ID';");
}

?>