<?php
$result = file_get_contents('php://input');
$object = json_decode($result);
$account = $object->{'account'};
$name = $object->{'name'};
$gender = $object->{'gender'};
$date = $object->{'date'};
$phoneNum = $object->{'phoneNum'};

if(isset($account) || isset($name) || isset($gender) || isset($date) || isset($phoneNum)){
	require __DIR__ . '/conn.php';

	connect();

	$result = mysqli_query($con, "UPDATE user SET User_Name = '$name', User_Gender = '$gender', User_Birthday = '$date', User_PhoneNum = '$phoneNum' WHERE User_Email = '$account'");
}

?>