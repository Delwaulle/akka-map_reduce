package fr.miagem2.actor

import akka.actor.Actor
import akka.actor.ActorRef
import scala.collection.mutable.ListBuffer
import fr.miagem2.main.Main.LineMessage
import fr.miagem2.main.Main.WordMessage
import fr.miagem2.main.Main.CountWordMessage
import akka.actor.ActorLogging

case class Mapper() extends Actor with ActorLogging {

  var reducers: ListBuffer[ActorRef] = ListBuffer[ActorRef]()

  def receive = {
    case reducers: Array[ActorRef] =>
      this.reducers = reducers.to[ListBuffer];
    case LineMessage(line: String) =>
      log.debug(s"Mapper received line <$line> to Map")
      var splitted = line.split(" ")
      for (word <- splitted) {
        var reducer = Math.abs(hash(word) % reducers.size)
        reducers(reducer) ! WordMessage(word)
      }
    case CountWordMessage(word: String) =>
      log.debug(s"Mapper received <$word> to count")
      var reducer = Math.abs(hash(word) % reducers.size)
      reducers(reducer) ! CountWordMessage(word)
    case _ =>
      log.error("Mapper received unknow message")
  }

  def hash(s: String): Int = {
    var h = 0
    for (i <- 0 until s.length()) {
      h = 31 * h + s.charAt(i);
    }
    return h
  }

}