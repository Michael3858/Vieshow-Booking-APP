<?php
$result = file_get_contents('php://input');
$object = json_decode($result);
$id = $object->{'id'};
$account = $object->{'account'};
$password = $object->{'password'};
$userid = $object->{'userid'};
$name = $object->{'name'};
$date = $object->{'date'};
$phonenum = $object->{'phonenum'};
$money = $object->{'money'};

if(isset($id) || isset($account) || isset($password) || isset($userid) || isset($name) || isset($date) || isset($phonenum) || isset($money)){
	require __DIR__ . '/conn.php';

	connect();

	$result = mysqli_query($con, "INSERT INTO user(User_IC, User_Email, Password, User_ID, User_Name, User_Birthday, User_PhoneNum, User_Money) VALUES('$id', '$account', '$password', '$userid', '$name', '$date', '$phonenum', '$money')");
}

?>