<?php 

include("config.php");

$Username = strip_tags(trim($_POST["Username"]));

$sql= "UPDATE user SET Banned = 'F' WHERE Username = '$Username'";

$query = mysqli_query($conn,$sql);
if($query){
    echo "User UnBanned Suecessfully";

}else{
    echo"Something went wrong Please try again";
}


?> 
