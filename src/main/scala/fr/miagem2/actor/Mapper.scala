package fr.miagem2.actor

import akka.actor.Actor
import akka.actor.ActorRef
import scala.collection.mutable.ListBuffer
import fr.miagem2.main.Main.LineMessage

case class Mapper() extends Actor {
  
  var reducers: ListBuffer[ActorRef] = ListBuffer[ActorRef]()

  def receive = {
    case reducers : Array[ActorRef] => 
      this.reducers = reducers.to[ListBuffer];
      println("Reducers size : "+reducers.length)
    case LineMessage(line : String) =>
      println(s"Mapper on reçoit : $line")
  }

}