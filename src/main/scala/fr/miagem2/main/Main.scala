package fr.miagem2.main

import akka.actor.ActorSystem
import akka.actor.ActorRef
import akka.actor.Props
import fr.miagem2.actor.Master
import fr.miagem2.actor.Mapper
import fr.miagem2.actor.Reducer
import fr.miagem2.actor.Mapper
import fr.miagem2.actor.Reducer
import scala.io.Source

//#main-class
object Main extends App {

  val MAPPER_NUMBER = 4
  val REDUCER_NUMBER = 4

  // create messages type
  case class DocumentMessage(document: Iterator[String])
  case class CountWordMessage(word: String)
  case class LineMessage(line: String)
  case class WordMessage(word: String)

  // create ActorSystem
  val system: ActorSystem = ActorSystem("actorSystem")
  println("Creating a master actor...")
  val master: ActorRef = system.actorOf(Props[Master], "masterActor")
  println("Master actor created.")
  val mappers = new Array[ActorRef](MAPPER_NUMBER)
  val reducers = new Array[ActorRef](REDUCER_NUMBER)

  // create reducers
  println(s"Creating $REDUCER_NUMBER reducer actor(s)...")
  var i = 0;
  while (i < REDUCER_NUMBER) {
    reducers(i) = system.actorOf(Props[Reducer], "reducerActor" + i)
    i += 1
  }
  println("Reducer actor created.")

  // create mappers
  println(s"Creating $MAPPER_NUMBER mapper actor(s)...")
  i = 0;
  while (i < MAPPER_NUMBER) {
    mappers(i) = system.actorOf(Props[Mapper], "mapperActor" + i)
    master ! mappers(i)
    mappers(i) ! reducers
    i += 1
  }
  println("Mapper actor created.")

  // get a filepath from argument
  var filename = "ressources/test.txt"
  if (args.length != 0) {
    filename = args(0)
  }

  // Send file to the master for analyse
  master ! DocumentMessage(Source.fromFile(filename).getLines())

  // get the word to count from argument
  var word = "Loic"
  if (args.length > 1) {
    word = args(1)
  }

  // Send the word to master to count
  master ! CountWordMessage(word)

}