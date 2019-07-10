<?php

    //variables para la conexión
    $server = "85.10.205.173:3306";
    $user = "tfg_domhogar";
    $pass = "tfg_domhogar";
    $dataBase = "tfg_domhogar";

    //variables del formulario - texfields
    $name = $_POST['name'];
    $email = $_POST['email'];
    $phone = $_POST['phone'];
    $decoWifi = $_POST['decoWifi'];
    $wifiSignal = $_POST['wifiSignal'];
    $smartPlugs = $_POST['smartPlugs'];
    $smartBulbs = $_POST['smartBulbs'];
    $wifiCams = $_POST['wifiCams'];

    //crear la conexión 
    $conn = new mysqli ($server, $user, $pass, $dataBase);
    //comprobar la conexión
    if ($conn->connect_error){
        die ("Connection failed: " . $conn->connect_error);
    }

    //montar la sentencia SQL
    $sql = "INSERT INTO CRM (nombre, email, telefono, DecoWifi_pack, Wifi_Signal_Expansion, Smart_Plugs_Pack, Smart_Bulbs_Pack, Wifi_Surveillance_Cameras)
            VALUES ('$name','$email','$phone','$decoWifi','$wifiSignal','$smartPlugs','$smartBulbs','$wifiCams')";       
    
    //ejecutar la sentencia SQL                                
    if ($conn->query($sql) === TRUE) {
        echo 'Great! We will contact you as soon as possible' ;
    } else {
        echo "Error: " . $sql . "<br>" . $conn->error;
    }
    //cerrar la conexión
    $conn->close();
?>