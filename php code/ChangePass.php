<?php 
include("config.php");

$oldpass = strip_tags(trim($_POST["oldpassword"]));
$newpass = strip_tags(trim($_POST["newpassword"]));
$conformpass = strip_tags(trim($_POST["conformpassword"]));
$email = strip_tags(trim($_POST["email"]));

$md5oldpass = md5 ($oldpass);

$sql = "SELECT * FROM user WHERE Password = '$md5oldpass' AND Email = '$email' ";
$query = mysqli_query($conn,$sql);

if($newpass == $conformpass){

    if(!mysqli_num_rows($query) > 0){
        echo "Please conform the old password";

    }else{
        $md5newpassword=md5($newpass);
        $update= "UPDATE user SET Password = '$md5newpassword' WHERE Email = '$email'";
        $res=mysqli_query($conn,$update);
        if($res){
            echo "Password changed Suecessfully";

        }else{
            echo"Something went wrong Please try again";
        }
    }
}else{
    echo"Please Match the new Passwords";
}
?> 
