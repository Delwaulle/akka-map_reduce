package fr.miagem2.actor

import akka.actor.Actor

class Mapper extends Actor {
  
  var reducers: Array[Reducer] = Array[Reducer]()

  def receive = {
    case _ => println("Mapper")
  }

}