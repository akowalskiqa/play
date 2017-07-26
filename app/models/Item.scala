package models

import play.api.data._
import play.api.data.Forms._
import scala.collection.mutable.ArrayBuffer

case class Item(var name: String, var description: String, var maker: String, var price: Int, var amount: Int, var discountForCertainAmount: Int, var seller: String, var imageURL: String)

case class ItemTToDelete(name: String)

object Item {
  val createItemForm = Form(
    mapping(
      "name" -> nonEmptyText(minLength = 0, maxLength = 30),
      "description" -> nonEmptyText,
      "maker" -> nonEmptyText(minLength = 0, maxLength = 30),
      "price" -> number(min = 0),
      "amount" -> number(min = 0),
      "discountForCertainAmount" -> number(min = 0),
      "seller" -> nonEmptyText,
      "imageURL" -> nonEmptyText
    )(Item.apply)(Item.unapply)
  )

  val itemTToDelete = Form(
    mapping("name" -> nonEmptyText)(ItemTToDelete.apply)(ItemTToDelete.unapply)
  )

  val items = ArrayBuffer(
    Item("primarykey1", "aa", "steven", 50, 2, 0, "ccc", "http://www.qygjxz.com/data/out/193/3856596-random-image.png"),
    Item("primarykey2", "bb", "alice", 180, 4, 0, "ccc", "http://globalgamejam.org/sites/default/files/styles/game_sidebar__normal/public/game/featured_image/promo_5.png?itok=9dymM8JD")
  )

}