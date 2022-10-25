<?php

include("config.php");


$sql="SELECT * FROM user";

$result=mysqli_query($conn,$sql);

$users=array();


while ($row = mysqli_fetch_array($result)){
   
    $ds['username'] = $row['Username'];
    $ds['email'] = $row ['Email'];
    $ds['fullname'] = $row ['FullName'];
    $ds['phone'] = $row ['PhoneNumber'];
    $ds['banned'] = $row ['Banned'];
    array_push($users,$ds);

}

echo json_encode($users);

mysqli_close($conn);

?>
