<?php

    include "user.php";

    $id = 1;

    $dsn = "mysql:host=localhost;dbname=usertest";
    $user = "testUser";
    $passwd = "qwerty";
    
    $pdo = new PDO($dsn, $user, $passwd);
    
    $stm = $pdo->prepare("SELECT * FROM user WHERE id = ?");
    $stm->bindValue(1, $id);
    $stm->execute();
    
    $row = $stm->fetch(PDO::FETCH_ASSOC);

    $localUser = new user($row['id'], $row['nume'], $row['prenume'], $row['photo']);
    
    
?>