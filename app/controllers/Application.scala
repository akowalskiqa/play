package controllers

import play.api._
import play.api.mvc._

class Application extends Controller {

  val echoReq = Action {implicit request: Request[AnyContent]=>
    Ok("Got Request ["+request+ "]")
  }

  val index = Action{
    Ok("static echo")
  }

  def secondPage = Action{
    Ok(views.html.secondPage("you have been directed to the second page"))
  }

  def indexWithInput(input:String) = Action{
    Ok(views.html.index(input))
  }
  def doProcessing(input:String) = Action{
    Ok(views.html.secondPage("I see you have added: " + input))
  }

  import play.api.http.HttpEntity
  import akka.util.ByteString
  def customResult = Action {
    Result(
      header = ResponseHeader(200, Map.empty),
      body = HttpEntity.Strict(ByteString("Hello world! Custom Result"), Some("text/plain"))
    )
  }

  val simpleRedirect = Action{
    Redirect("/secondPage", MOVED_PERMANENTLY)
  }

  val testThis = Action {
    Redirect(routes.Application.plannedUpdates())
  }

  def plannedUpdates = Action {
    Ok("Planned technical updates are in progress.")
  }

  val ok =Ok("Hello world!")
  val notFound = NotFound
  val pageNotFound = NotFound(<h1>Page not found</h1>)
  val badRequest = BadRequest("Error happened")
  val oops = InternalServerError("Oops")
  val anyStatus = Status(488)("Strange response type")

  val helper1 = Action {
    anyStatus
  }

  def takeMeToTheCandyShop = TODO


  def startCookie = Action{
    Ok("it works!").withCookies(
      Cookie("theme","blue")
    )
  }

  def discardCookie = Action{implicit request:Request[AnyContent]=>
    Ok("Discarding: " + request.cookies.get("theme").get.value).discardingCookies(
      DiscardingCookie("theme"))
  }
  def changeCookieValue = Action{implicit request:Request[AnyContent]=>
    Ok("Was this theme: " + request.cookies.get("theme").get.value).withCookies(
      Cookie("theme","green")
    )
  }

  def startSession = Action{
    Ok("hello").withSession(
      "connected" -> "emailAddress")

  }
  def addSessionElement = Action{implicit request: Request[AnyContent]=>
    Ok("Hello World!").withSession(
      request.session + ("saidHello" -> "yes"))
  }
  def getSessionElement = Action{implicit request: Request[AnyContent] =>
    Ok("sessionElement: " + request.session.get("saidHello").getOrElse("dunno"))
  }

  def cutSession = Action{implicit request: Request[AnyContent] =>
    Ok("end of session").withNewSession
  }



  def flashRedirect = Action { implicit request =>
    Ok {
      request.flash.get("nameOfFlashVariable").getOrElse("AlternativeToFlashContent!")
    }
  }

  def flash = Action {
    Redirect("/flashRedirect").flashing(
      "nameOfFlashVariable" -> "The item has been created")
  }






}

