<?php require_once('../../private/initialize.php'); ?>

$klantid = getfromjava();
<?php $page_title = 'Get'; ?>
<?php include(SHARED_PATH . '/staff_header.php') ?>
<h1> Kom jonko hoekie dan </h1>
<?php
  $sql = "SELECT $KLANTID, VOORNAAM, ACHTERNAAM FROM klantgegevens";
  $result = $connection->query($sql);

  if ($result->num_rows > 0) {
      // output data of each row
      while($row = $result->fetch_assoc()) {
          echo "KlantID: " . $row["KLANTID"]. " - Naam: " . $row["VOORNAAM"]. " " . $row["ACHTERNAAM"]. "<br>";
      }
  } else {
      echo "0 resultaten";
  }
    ?>
    <div id="content">
    </div>
    Ai ja toch

<?php include(SHARED_PATH . '/staff_footer.php') ?>
