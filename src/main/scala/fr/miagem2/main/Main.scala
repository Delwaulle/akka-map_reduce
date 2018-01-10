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

  // create ActorSystem
  val system: ActorSystem = ActorSystem("actorSystem")
  val master: ActorRef = system.actorOf(Props[Master], "masterActor")

  val mappers = new Array[ActorRef](MAPPER_NUMBER)
  val reducers = new Array[ActorRef](REDUCER_NUMBER)
  
  case class DocumentMessage(document: Iterator[String])
  case class ResultMessage(word: String)
  case class LineMessage(line: String)

  var i = 0;
  while (i < REDUCER_NUMBER) {
    reducers(i) = system.actorOf(Props[Reducer], "reducerActor" + i)
    i += 1
  }

  i = 0;
  while (i < MAPPER_NUMBER) {
    mappers(i) = system.actorOf(Props[Mapper], "mapperActor" + i)
    master ! mappers(i)
    mappers(i) ! reducers
    i += 1
  }
  
  val filename = "test.txt"
  master ! DocumentMessage(Source.fromFile(filename).getLines())

}