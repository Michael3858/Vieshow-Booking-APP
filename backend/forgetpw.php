<?php
$result = file_get_contents('php://input');
$object = json_decode($result);
$account = $object->{'account'};
$password = $object->{'password'};

if(isset($account) || isset($password)){
	require __DIR__ . '/conn.php';

	connect();

	$result = mysqli_query($con, "UPDATE user SET Password = '$password' WHERE User_Email = '$account'");
}

?>