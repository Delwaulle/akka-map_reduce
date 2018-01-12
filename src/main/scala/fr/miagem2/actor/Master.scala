package fr.miagem2.actor

import akka.actor.Actor
import akka.actor.ActorRef
import akka.Main
import akka.actor.RepointableActorRef
import scala.collection.mutable.ListBuffer
import java.io.File
import fr.miagem2.main.Main.DocumentMessage
import fr.miagem2.main.Main.LineMessage
import fr.miagem2.main.Main.CountWordMessage
import akka.actor.ActorLogging

class Master extends Actor with ActorLogging {

  var mappers: ListBuffer[ActorRef] = ListBuffer[ActorRef]()

  def receive = {
    case act: ActorRef =>
      mappers += act
    case DocumentMessage(document: Iterator[String]) =>
      log.debug("Master received file message")
      var i = 0;
      for (line <- document) {
        mappers(i % mappers.size) ! LineMessage(line)
      }
    case CountWordMessage(word: String) =>
      log.debug(s"Master received <$word> to count")
      mappers(0) ! CountWordMessage(word)
    case _ =>
      log.error("Master received unknow message")
  }

}