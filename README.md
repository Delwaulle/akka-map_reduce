# akka-map_reduce
Welcome to Akka Map Reduce in Scala 
made by Clément Guyot, Loic Delwaulle & Tanguy Dépommier.

# How to run the program ? 
Firstly you need to run sbt in the root folder of the project :
```
./sbt
```
The first time, it can take some time to download dependencies. 
When it's done you can launch the program with this command :
```
reStart
```

By default it will read the file test.txt in the ressources folder and search the Word "Loic".
If you want to analyze another file and search another word, you can put them in the arguments of the program.
Example : 
```
reStart ressources/10k.txt Sarkozy
```