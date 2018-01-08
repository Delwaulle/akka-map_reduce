package fr.miagem2.actor

import akka.actor.Actor

class Mapper extends Actor {

  def receive = {
    case _ => println("Mapper")
  }

}