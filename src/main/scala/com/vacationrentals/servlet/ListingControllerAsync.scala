package com.vacationrentals.servlet

import akka.actor.{ActorRef, ActorSystem}
import akka.pattern.ask
import akka.util.Timeout
import com.vacationrentals.actor._
import com.vacationrentals.dto.ListingDto
import com.vacationrentals.service.ListingService
import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json.JacksonJsonSupport

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

class ListingControllerAsync(system: ActorSystem, listingActor: ActorRef, listingService: ListingService) extends ScalatraServlet
  with FutureSupport with JacksonJsonSupport {

  implicit val timeout = new Timeout(2 seconds)
  protected implicit def executor: ExecutionContext = system.dispatcher
  protected implicit val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  get("/") {
    listingActor ? new ActorMessage()
  }

  get("/:id") {
    val id = params("id")
    listingActor ? new ActorMessageFindById(id)
  }

  post("/add") {
    val jsonString = request.body
    val jValue = parse(jsonString)
    val listingDto = jValue.extract[ListingDto]

    listingActor ? new ActorMessageAdd(listingDto)
  }

  put("/:id") {
    val id = params("id")
    val jsonString = request.body
    val jValue = parse(jsonString)
    val listingDto = jValue.extract[ListingDto]

    listingActor ? new ActorMessageModify(id, listingDto)
  }

  delete("/:id") {
    val id = params("id")
    listingActor ? new ActorMessageDelete(id)
  }

}
