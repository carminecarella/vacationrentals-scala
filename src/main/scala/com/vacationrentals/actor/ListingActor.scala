package com.vacationrentals.actor

import akka.actor.Actor
import com.vacationrentals.dto.ListingDto
import com.vacationrentals.model.Listing
import com.vacationrentals.service.ListingService

class ListingActor(listingService: ListingService) extends Actor {

  def receive = {
    case message: ActorMessage => sender ! getListings()
    case messageFindById: ActorMessageFindById => sender ! getListingById(messageFindById.id)
    case messageAdd: ActorMessageAdd => sender ! addListing(messageAdd.listingDto)
    case messageModify: ActorMessageModify => sender ! modifyListing(messageModify.id, messageModify.listingDto)
    case messageDelete: ActorMessageDelete => sender ! deleteListing(messageDelete.id)
  }

  def getListings() = {
    listingService.searchAllListing()
  }

  def getListingById(id: String): Listing = {
    val result = listingService.searchListingById(id)
    result.left.get
  }

  def addListing(listingDto: ListingDto): Listing = {
    val result = listingService.addListing(listingDto)
    result.left.get
  }

  def modifyListing(id: String, listingDto: ListingDto): Listing = {
    val result = listingService.modifyListing(id, listingDto)
    result.left.get
  }

  def deleteListing(id: String): Listing = {
    val result = listingService.deleteListing(id)
    result.left.get
  }

}
