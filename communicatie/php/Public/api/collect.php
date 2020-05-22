<?php

require_once '../../Private/database.php';


error_reporting(E_ALL);
ini_set('display_errors', 1);

$con = db_connect();
$randoms = $_GET['reknr'];
$collect = $_GET['amount'];
$query = mysqli_query($con, "UPDATE REKENINGEN SET SALDO = SALDO - '" . $collect . "' WHERE REKENINGNUMMER= '" . $randoms . "'");



header('Content-Type: application/json');
echo json_encode([
    'success' => true

]);
