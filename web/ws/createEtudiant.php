<?php

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Scripting/EmptyPHP.php to edit this template
 */
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    include_once("../racine.php");
    include_once RACINE . '/service/EtudiantService.php';
    create();
}

function create() {
    extract($_POST);
    $es = new EtudiantService();
//insertion d’un étudiant
    $es->create(new Etudiant(1, $nom, $prenom, $ville, $sexe));
//chargement de la liste des étudiants sous format json
    header('Content-type: application/json');
    echo json_encode($es->findAllApi());
}
