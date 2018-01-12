package fr.miagem2.actor

import akka.actor.Actor
import scala.collection.mutable.HashMap
import fr.miagem2.main.Main.WordMessage
import fr.miagem2.main.Main.CountWordMessage
import akka.actor.ActorLogging

class Reducer extends Actor with ActorLogging {

  val workCount: HashMap[String, Int] = HashMap()

  def receive = {
    case WordMessage(word: String) =>
      // removing ponctuation mark and force lower case
      val newWord = word.filterNot(x => x == ',' || x == '.').toLowerCase();

      log.debug(s"Reducer received <$newWord> to reduce")
      workCount.put(newWord, workCount.getOrElse(newWord, 0) + 1)
    case CountWordMessage(word: String) =>
      log.debug(s"Reducer received <$word> to count")
      log.info(s"<$word> appears ${workCount.getOrElse(word.toLowerCase(), 0)} time(s)")
    case _ =>
      log.error("Reducer received unknow message")
  }

}