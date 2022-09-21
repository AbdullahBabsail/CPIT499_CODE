<?php 

include("config.php");


$UserName = strip_tags(trim($_POST["Username"]));
$Fullname = strip_tags(trim($_POST["Fullname"]));
$Email = strip_tags(trim($_POST["Email"]));
$Phone = strip_tags(trim($_POST["Phonenum"]));
$oldEmail = strip_tags(trim($_POST["OldEmail"]));
$oldUserName = strip_tags(trim($_POST["oldUsername"]));

$check_stamt="SELECT * FROM user WHERE Email = '$Email' OR Username = '$UserName'";
$check_1 = mysqli_query($conn,$check_stamt);
$row= mysqli_fetch_array($check_1);
if(mysqli_num_rows($check_1) > 0 &&  $row['Email'] != $oldEmail && $row['Username'] != $oldUserName){
    
     echo "Existing User Data";
}else{

$sql = "SELECT * FROM user WHERE Email = '$oldEmail'";
$check = mysqli_query($conn,$sql);
if(mysqli_num_rows($check)>0){
    $update_sql = "UPDATE user SET Email = '$Email' , Username = '$UserName' , FullName = '$Fullname' , PhoneNumber = '$Phone' WHERE Email = '$oldEmail'" ;

    if(mysqli_query($conn,$update_sql)){
        echo"Data Updated Successfully ";

    }else{
        echo "Something Went Wrong Try Again";
    }

}else{
    echo "UnAthorized User";
   }
   //echo "Something Went Wrong Try Again Please";

}
?> 