<?php

require_once '../../Private/database.php';


error_reporting(E_ALL);
ini_set('display_errors', 1);

$con = db_connect();
$randoms = $_GET['reknr'];

$query = mysqli_query($con, "UPDATE REKENINGEN SET INLOGPOGINGEN = INLOGPOGINGEN + 1 WHERE REKENINGNUMMER= '" . $randoms . "'");



header('Content-Type: application/json');
echo json_encode([
    'success' => true

]);
