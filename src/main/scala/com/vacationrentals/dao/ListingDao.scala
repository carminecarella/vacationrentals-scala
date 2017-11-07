package com.vacationrentals.dao

import com.vacationrentals.dto.ListingDto
import com.vacationrentals.model._

import scala.collection.mutable
import scala.util.{Failure, Success, Try}

class ListingDao extends Dao[Listing] {

  var listings = initializeAndPopulateDatabase

  override def findAll(): List[Listing] = {
    listings.values.toList
  }

  override def findById(id: String): Option[Listing] = {
    listings.get(id)
  }

  override def create(listingDto: ListingDto): Option[Listing] = {
    Try(addToDatabase(listingDto)) match {
      case Success(listing) => Some(listing)
      case Failure(ex) => None
    }
  }

  override def modify(id: String, listingDto: ListingDto): Option[Listing] = {
    val listing = convertDtoToModel(id, listingDto)
    listings(id) = listing
    Some(listing)
  }

  override def delete(id: String): Option[Listing] = {
    val listing = listings.get(id)
    listing match {
      case Some(listing) => {
        listings -= id
        Some(listing)
      }
      case None => None
    }
  }

  private def initializeAndPopulateDatabase(): mutable.Map[String, Listing] = {
    var map = mutable.Map[String, Listing]()

    val id: String = "5e22a83a-6f4f-11e6-8b77-86f30ca893d3"
    val contact = new Contact("15126841100", "+1 512-684-1100")
    val address = new Address("1011 W 5th St", "1011", "US", "Austin", "TX", "United States")
    val location = new Location(40.4255485534668, -3.7075681686401367)
    val listing = new Listing(id, contact, address, location)

    map. += (id -> listing)

  }

  private def addToDatabase(listingDto: ListingDto): Listing = {
    val id = java.util.UUID.randomUUID.toString
    val listing = convertDtoToModel(id, listingDto)
    listings += (id -> listing)
    listing
  }

}

object convertDtoToModel {
  def apply(id: String, listingDto: ListingDto): Listing = {
    val contact = new Contact(listingDto.contact.phone, listingDto.contact.formattedPhone)
    val address = new Address(listingDto.address.address,
                              listingDto.address.postalCode,
                              listingDto.address.countryCode,
                              listingDto.address.city,
                              listingDto.address.state,
                              listingDto.address.country)
    val location = new Location(listingDto.location.lat, listingDto.location.lng)
    new Listing(id, contact, address, location)
  }
}
