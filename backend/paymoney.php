<?php
$result = file_get_contents('php://input');
$object = json_decode($result);
$acc = $object->{'acc'};
$money = $object->{'money'};

if(isset($acc) || isset($money)){
	require __DIR__ . '/conn.php';

	connect();

	$result = mysqli_query($con, "UPDATE user SET User_Money = '$money' WHERE User_Email = '$acc'");
}

?>