<?php
$result = file_get_contents('php://input');
$object = json_decode($result);
$User_Money = $object->{'User_Money'};
$User_Email = $object->{'User_Email'};

if(isset($User_Money) || isset($User_Email)){
	require __DIR__ . '/conn.php';

	connect();

	$result = mysqli_query($con, "UPDATE user SET User_Money = '$User_Money' where User_Email = '$User_Email'");
}

?>