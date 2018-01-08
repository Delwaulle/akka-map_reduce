package fr.miagem2.actor

import akka.actor.Actor
import akka.actor.ActorRef
import akka.Main

class Master extends Actor {
  
  var mappers: Array[ActorRef] = Array[ActorRef]()

  def receive = {
    case _ => {
      println("Master")
    }
  }
 
}