<?php
$result = file_get_contents('php://input');
$object = json_decode($result);
$Ticket_ID = $object->{'Ticket_ID'};
$Meal_ID = $object->{'Meal_ID'};
$Num = $object->{'Num'};

if(isset($Ticket_ID) || isset($Meal_ID) || isset($Num)){
	require __DIR__ . '/conn.php';

	connect();

	$result = mysqli_query($con, "INSERT INTO booking_meals(Ticket_ID, Meal_ID, Num) VALUES('$Ticket_ID', '$Meal_ID', '$Num')");
}

?>