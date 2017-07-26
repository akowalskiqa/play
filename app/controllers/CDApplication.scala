package controllers

import javax.inject.Inject

import models.CD
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._
import models.Item

import scala.concurrent.Future

class CDApplication @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def listCDs = Action { implicit request =>
    Ok(views.html.listCDs(CD.cds, CD.createCDForm))
  }

  def createCD = Action { implicit request =>

    val formValidationResult = CD.createCDForm.bindFromRequest
    formValidationResult.fold({ formWithErrors =>
      BadRequest(views.html.listCDs(CD.cds, formWithErrors))
    }, { cd =>
      CD.cds.append(cd)
      Redirect(routes.CDApplication.listCDs)
    })
  }

  def listItems = Action { implicit request =>
    Ok(views.html.listItems(Item.items, Item.createItemForm))
  }

  def showTODeleteItem = Action { implicit request =>
    Ok(views.html.deleteItem(Item.items, Item.itemTToDelete))
  }

  def listItemsToUpdate = Action { implicit request =>
    Ok(views.html.updateItem(Item.items, Item.createItemForm))
  }


  def newItem = Action { implicit request =>
    val formValidationResult = Item.createItemForm.bindFromRequest
    formValidationResult.fold({ formWithErrors =>
      BadRequest(views.html.listItems(Item.items, formWithErrors))
    }, { item =>
      val newItem = Item(item.name, item.description, item.maker, item.price, item.amount, (item.price * item.amount) * 5, item.seller,item.imageURL)
      Item.items.append(newItem)
      Redirect(routes.CDApplication.listItems)
    })
  }

  def itemUpdate = Action { implicit request =>
    val formValidationResult = Item.createItemForm.bindFromRequest
    formValidationResult.fold({ formWithErrors =>
      BadRequest(views.html.listItems(Item.items, formWithErrors))
    }, { item =>
      println(item.name)
      val indexOfItem = Item.items.indexWhere(e => e.name.equalsIgnoreCase(item.name))
      Item.items(indexOfItem).description = item.description
      Item.items(indexOfItem).maker = item.maker
      Item.items(indexOfItem).price = item.price
      Item.items(indexOfItem).amount = item.amount
      Item.items(indexOfItem).discountForCertainAmount = item.discountForCertainAmount
      Item.items(indexOfItem).seller = item.seller
      Redirect(routes.CDApplication.listItems)
    })
  }

  def deleteItem = Action { implicit request =>
    val formValidationResult = Item.itemTToDelete.bindFromRequest
    formValidationResult.fold({ formWithErrors =>
      BadRequest(views.html.deleteItem(Item.items, formWithErrors))
    }, { item =>
      Item.items.remove(Item.items.indexWhere(e => e.name.equalsIgnoreCase(item.name)) + 1)
      Redirect(routes.CDApplication.showTODeleteItem)
    })
  }
}