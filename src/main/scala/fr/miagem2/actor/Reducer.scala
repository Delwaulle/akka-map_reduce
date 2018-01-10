package fr.miagem2.actor

import akka.actor.Actor
import scala.collection.mutable.HashMap
import fr.miagem2.main.Main.WordMessage
import fr.miagem2.main.Main.CountWordMessage

class Reducer extends Actor {

  val workCount: HashMap[String, Int] = HashMap()
  
  def receive = {
    case WordMessage(word: String) => 
      workCount.put(word, workCount.getOrElse(word, 0)+1)
    case CountWordMessage(word: String) =>
      println(s"Nombre d'occurence de $word : ${workCount.getOrElse(word, 0)}")
  }

}