package fr.miagem2.actor

import akka.actor.Actor
import akka.actor.ActorRef
import akka.Main
import akka.actor.RepointableActorRef
import scala.collection.mutable.ListBuffer
import java.io.File

class Master extends Actor {
  
  var mappers: ListBuffer[ActorRef] = ListBuffer[ActorRef]()

  def receive = {
    case act : ActorRef => 
      mappers += act
      println("Mapper size : "+mappers.size)  
    case file : File => 
      println("File")
  }
 
}