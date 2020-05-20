<?php
   if(!isset($page_title)) {
      $page_title = 'Standard title';
   }
?>

<!doctype html>

<html lang="en">
    <head>
     <title>Staff Menu<?php echo $page_title; ?> </title>
     <meta charset="utf-8">
     <link rel="Stylesheet" media="all" href="<?php echo url_for('/stylesheets/staff.css'); ?>" />
    </head>

    <body>
     <header>
        <h1>De Get Bank staff area</h1>
     </header>

    <navigation>
     <ul>
      <li><a href="<?php echo url_for('/staff/index.php'); ?>">Go to Main Menu</a></li>
     </ul>
    </navigation>
