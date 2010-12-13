TuxDroid Java API
=================

Quick sample
------------

This sample is a light game where you have to guess the result of a simple multiplication between two numbers from 1 to 10.

### Dependencies
* Downlad JNA library
* Copy libtuxdriver.so to /usr/lib/libtuxdriver.so (for example...)

### Compile
* Go to your repo path (in my case, jna.jar is in there)
* javac -cp .:jna.jar com/tuxisalive/direct/TuxDroid.java MultiplicationGameTuxDroidSample.java

### Run
* Plug TuxDroid's fish 
* java -cp .:jna.jar MultiplicationGameTuxDroidSample

### Play !
* solve the problem proposed on console
* press on left wing to increment units
* press on right wing to increment tens
* press on head button to validate your answer
* if it is the good one, start again :D
* press ^D (Control-D) to exit from console ; ^C is not the better choice...

