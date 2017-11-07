package com.vacationrentals.service

import com.vacationrentals.dao.ListingDao
import com.vacationrentals.dto.{AddressDto, ContactDto, ListingDto, LocationDto}
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ListingServiceTest extends FeatureSpec with GivenWhenThen with Matchers with BeforeAndAfterEach {

  var service: ListingService = _

  override def beforeEach() {
    val dao = new ListingDao
    service = new ListingService(dao)
  }

  def createDto(): ListingDto = {
    val contact = new ContactDto("111111111", "111111111")
    val address = new AddressDto("Oxford Street", "W1N6G", "UK", "London", "London", "United Kingdom")
    val location = new LocationDto(40.65464565, -5.65656565)
    new ListingDto(contact, address, location)
  }

  feature("Listings Endpoints") {

    scenario("Search All Listings") {

      Given("the service is called")
      val result = service.searchAllListing()

      Then("a list of all listings is returned")
      result.size should be (1)

    }

    scenario("Search Listing by Id") {

      Given("a listing id")
      val id = "5e22a83a-6f4f-11e6-8b77-86f30ca893d3"

      When("the service is called")
      val result = service.searchListingById(id)

      Then("a listing with the id is returned")
      result.isLeft should be (true)
      result.left.get.id should be (id)

    }

    scenario("Search Listing by Id Not Found") {

      Given("a listing id")
      val id = "10"

      When("the service is called")
      val result = service.searchListingById(id)

      Then("a listing with the id is not found")
      result.isRight should be (true)
      result.right.get.message should be ("Listing not found")

    }

    scenario("Add a new listing") {

      Given("a listing to add")
      val newListing = createDto

      When("the service is called")
      val result = service.addListing(newListing)

      Then("the new listing along with the id is returned")
      result.isLeft should be (true)
      result.left.get.id should not be (null)

      And("The total number of listings in the database is increased")
      service.searchAllListing().size should be (2)

    }

    scenario("Modify a listing") {

      Given("a listing id and a listing dto")
      val id = "5e22a83a-6f4f-11e6-8b77-86f30ca893d3"
      val newListing = createDto

      When("the service is called")
      val result = service.modifyListing(id, newListing)

      Then("the listing is modified")
      result.isLeft should be (true)
      result.left.get.id should be (id)
      result.left.get.address.address should be (newListing.address.address)

      And("The total number of listings in the database remains the same")
      service.searchAllListing().size should be (1)

    }

    scenario("Delete a listing") {

      Given("a listing id")
      val id = "5e22a83a-6f4f-11e6-8b77-86f30ca893d3"

      When("the service is called")
      val result = service.deleteListing(id)

      Then("the listing is deleted")
      result.isLeft should be (true)
      result.left.get.id should be (id)

      And("The total number of listings in the database decreases")
      service.searchAllListing().size should be (0)

    }

  }

}
