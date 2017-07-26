package models

import play.api.data._
import play.api.data.Forms._

import scala.collection.mutable.ArrayBuffer

case class CD(name: String, price: Int, author: String, playDuration: Int)

object CD {

  val createCDForm = Form(
    mapping(
      "name" -> nonEmptyText,
      "price" -> number(min = 0, max = 100),
      "author" -> nonEmptyText,
      "playDuration" -> number(min = 0, max = 180)
    )(CD.apply)(CD.unapply)
  )

  val cds = ArrayBuffer(
    CD("CD 1", 34, "steven", 50),
    CD("CD 2", 99, "alice", 180),
    CD("CD 3", 14, "johnson", 37)
  )

}