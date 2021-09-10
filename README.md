# SGF file to Json and send it to Katago
Java Program which allows to transform SGF files into Json and send it to KataGo and stores the response in a sgf/txt file.
## Table of contents
* [General info](#general-info)
* [Technologies & Requirements](#technologies)
* [Setup](#setup)

## General info
This project allows to transform Smart Game Format or SGF files into a JsonObject. SGF is used for storing records of board games.
This program focuses, however only on Go Games and NO other boardgames are supported. The resulting JsonObject after the transofmration will be send to a backend KataGoAPi and the response of KataGo will be stored into a seperated file.

## Technologies
This program uses Java and REQUIRES a running backend service KataGo.

## Setup
Import the project into your favorite IDe and change the imput folder and output folder where the respective SGF files are.
In addition you are required to change the URL of the KataGo API so that it matches your Katago backend Service.
