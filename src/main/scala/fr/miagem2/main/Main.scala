package fr.miagem2.main

import akka.actor.ActorSystem
import akka.actor.ActorRef
import akka.actor.Props
import fr.miagem2.actor.Master
import fr.miagem2.actor.Mapper
import fr.miagem2.actor.Reducer
import fr.miagem2.actor.Mapper
import fr.miagem2.actor.Reducer

//#main-class
object Main extends App {

  //#printer-messages

  val MAPPER_NUMBER = 4
  val REDUCER_NUMBER = 4

  // create ActorSystem
  val system: ActorSystem = ActorSystem("actorSystem")
  val master: ActorRef = system.actorOf(Props[Master], "masterActor")

  val mappers = new Array[Mapper](MAPPER_NUMBER)
  val reducers = new Array[Reducer](REDUCER_NUMBER)

  var i = 0;
  while (i < REDUCER_NUMBER) {
    reducers(i) = system.actorOf(Props[Reducer], "reducerActor" + i).asInstanceOf[Reducer]
    i += 1
  }

  i = 0;
  while (i < MAPPER_NUMBER) {
    mappers(i) = system.actorOf(Props[Mapper], "mapperActor" + i).asInstanceOf[Mapper]
    mappers(i).reducers = reducers;
    i += 1
  }

  master.asInstanceOf[Master].mappers = mappers;

}