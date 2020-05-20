<?php

require_once '../../Private/database.php';



error_reporting(E_ALL);
ini_set('display_errors', 1);

$con = db_connect();

$query = mysqli_query($con, "SELECT * FROM KLANTGEGEVENS");
header('Content-Type: application/json');
echo json_encode([
    'success' => true,
    'klantgegevens' => mysqli_fetch_all($query, MYSQLI_ASSOC)
]);
