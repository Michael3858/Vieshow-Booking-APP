<?php
$result = file_get_contents('php://input');
$object = json_decode($result);
$Ticket_ID = $object->{'Ticket_ID'};
$Seat = $object->{'Seat'};

if(isset($Ticket_ID) || isset($Seat)){
	require __DIR__ . '/conn.php';

	connect();

	$result = mysqli_query($con, "INSERT INTO booking_seat(Ticket_ID, Seat) VALUES('$Ticket_ID', '$Seat')");
}

?>