<?php

require('db_credentials.php');

function db_connect()
{
    $connection = mysqli_connect(DB_SERVER, DB_USER, DB_PASS, DB_NAME);
    if ($connection->connect_error) {
        die("Connection failed: " . $connection->connect_error);
    }
    //echo "Connected successfully";
    return $connection;
}

function db_disconnect($connection)
{
    mysqli_close($connection);
}


?>
