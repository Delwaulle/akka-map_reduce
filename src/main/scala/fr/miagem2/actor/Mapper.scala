package fr.miagem2.actor

import akka.actor.Actor
import akka.actor.ActorRef
import scala.collection.mutable.ListBuffer
import fr.miagem2.main.Main.LineMessage
import fr.miagem2.main.Main.WordMessage
import fr.miagem2.main.Main.CountWordMessage

case class Mapper() extends Actor {
  
  var reducers: ListBuffer[ActorRef] = ListBuffer[ActorRef]()

  def receive = {
    case reducers : Array[ActorRef] => 
      this.reducers = reducers.to[ListBuffer];
      println("Reducers size : "+reducers.length)
    case LineMessage(line : String) =>
      var splitted = line.split(" ")
      for (word <- splitted) {
       var reducer = Math.abs(hash(word)%reducers.size)
       println(s"On envoie $word sur reducer $reducer")
       reducers(reducer) ! WordMessage(word)
      }
    case CountWordMessage(word: String) =>
      var reducer = Math.abs(hash(word)%reducers.size)
      reducers(reducer) ! CountWordMessage(word)
  }
  
  def hash(s : String) : Int = {
	    var h = 0
	    for (i <- 0 until s.length()) {
	        h = 31 * h + s.charAt(i);
	    }
	    return h
	}

}