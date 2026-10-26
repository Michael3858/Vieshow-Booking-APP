<?php
$result = file_get_contents('php://input');
$object = json_decode($result);
$Ticket_ID = $object->{'Ticket_ID'};
$User_Email = $object->{'User_Email'};
$Screenings_ID = $object->{'Screenings_ID'};
$Booking_Payment = $object->{'Booking_Payment'};
$Booking_Time = $object->{'Booking_Time'};
$Total_Price = $object->{'Total_Price'};
$State = $object->{'State'};

if(isset($Ticket_ID) || isset($User_Email) || isset($Screenings_ID) || isset($Booking_Payment) || isset($Booking_Time) || isset($Total_Price) || isset($State)){
	require __DIR__ . '/conn.php';

	connect();

	$result = mysqli_query($con, "INSERT INTO booking_record(Ticket_ID, User_Email, Screenings_ID, Booking_Payment, Booking_Time, Total_Price, State) VALUES('$Ticket_ID', '$User_Email', '$Screenings_ID', '$Booking_Payment', '$Booking_Time', '$Total_Price', '$State')");
}

?>