<?php
$result = file_get_contents('php://input');
$object = json_decode($result);
$Ticket_ID = $object->{'Ticket_ID'};
$Type_ID = $object->{'Type_ID'};
$Num = $object->{'Num'};

if(isset($Ticket_ID) || isset($Type_ID) || isset($Num)){
	require __DIR__ . '/conn.php';

	connect();

	$result = mysqli_query($con, "INSERT INTO booking_ticket_type(Ticket_ID, Type_ID, Num) VALUES('$Ticket_ID', '$Type_ID', '$Num')");
}

?>