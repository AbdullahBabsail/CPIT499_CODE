<?php 

include("config.php");

$Username = strip_tags(trim($_POST["Username"]));

$sql= "UPDATE user SET Banned = 'T' WHERE Username = '$Username'";

$query = mysqli_query($conn,$sql);
if($query){
    echo "User Banned Suecessfully";

}else{
    echo"Something went wrong Please try again";
}


?> 
