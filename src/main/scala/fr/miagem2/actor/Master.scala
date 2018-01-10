package fr.miagem2.actor

import akka.actor.Actor
import akka.actor.ActorRef
import akka.Main
import akka.actor.RepointableActorRef
import scala.collection.mutable.ListBuffer
import java.io.File
import fr.miagem2.main.Main.DocumentMessage
import fr.miagem2.main.Main.LineMessage

class Master extends Actor {
  
  var mappers: ListBuffer[ActorRef] = ListBuffer[ActorRef]()

  def receive = {
    case act : ActorRef => 
      mappers += act
      println("Mapper size : "+mappers.size)  
    case DocumentMessage(document: Iterator[String]) => 
      var i = 0;
      for (line <- document) {
        println(s"Master : on envoie : $line")
        mappers(i%mappers.size) ! LineMessage(line)
      }
  }
 
}