<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hp
 */
interface IDao {

    //put your code here
    function create($o);

    function delete($o);

    function update($o);

    function findAll();

    function findById($id);
}
