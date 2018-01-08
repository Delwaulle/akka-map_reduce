package fr.miagem2.actor

import akka.actor.Actor

class Reducer extends Actor {

  def receive = {
    case _ => println("Reducer")
  }

}