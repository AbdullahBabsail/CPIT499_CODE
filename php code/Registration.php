
<?php

include("config.php");


$UserName = strip_tags(trim($_POST["UserName"]));
$Password = strip_tags(trim($_POST["Password"]));
$Email = strip_tags(trim($_POST["Email"]));


if ($UserName<>"" && $Password<>"" && $Email<>""){

	$sql="SELECT * FROM user WHERE Email = '$Email' OR Username ='$UserName'";
	$check =mysqli_query($conn,$sql);
	if(mysqli_num_rows($check)){
		echo"Existing User";
	}else{
	$newPass = md5($Password);
  $sql_query = "INSERT INTO user (Username, Password, Email)VALUES('$UserName','$newPass','$Email')";
	$dbResult = $conn->query($sql_query);
	if ($dbResult === TRUE) { 
		echo "Registration is Done";
		} else {
		 echo"Sorry Something Went Wrong";
		}
	}


}


?>
