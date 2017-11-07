package com.vacationrentals.servlet

import com.vacationrentals.dto.ListingDto
import com.vacationrentals.model.{Listing, ListingError}
import com.vacationrentals.service.ListingService
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json.JacksonJsonSupport
import org.scalatra.swagger.{Swagger, SwaggerSupport}
import org.scalatra.{Created, NoContent, NotFound, ScalatraServlet}

class ListingServlet(listingService: ListingService, val swagger: Swagger) extends ScalatraServlet with JacksonJsonSupport with SwaggerSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats
  protected val applicationDescription: String = "Listing endpoint"

  before() {
    contentType = formats("json")
  }

  val findAllListings =
    (apiOperation[Listing]("findAllProperties")
      summary "Find all vacation rental properties")

  get("/", operation(findAllListings)) {
    listingService.searchAllListing
  }

  val findListingById =
    (apiOperation[Listing]("findListingById")
      summary "Find a listing by id"
      parameters (
      pathParam[String]("id").description("id of the listing")
    ))

  get("/:id", operation(findListingById)) {
    val id = params("id")
    val result = listingService.searchListingById(id)

    result match {
      case Left(listing: Listing) => listing
      case Right(error: ListingError) => NotFound(error.message)
    }
  }

  val addListing =
    (apiOperation[Listing]("addListing")
      summary "Modify a listing by id"
      parameters (
      pathParam[ListingDto]("listing").description("listing to be added")
    ))

  post("/add", operation(addListing)) {
    val jsonString = request.body
    val jValue = parse(jsonString)
    val listingDto = jValue.extract[ListingDto]

    val result = listingService.addListing(listingDto)

    result match {
      case Left(listing: Listing) => {
        val location = s"/listings/${listing.id}"
        Created(listing, headers = Map("Location" -> location))
      }
      case Right(error: ListingError) => NoContent(reason = error.message)
    }
  }

  val modifyListing =
    (apiOperation[Listing]("modifyListing")
      summary "Modify a listing by id"
      parameters (
      pathParam[String]("id").description("id of the listing")
    ))

  put("/:id", operation(modifyListing)) {
    val id = params("id")
    val jsonString = request.body
    val jValue = parse(jsonString)
    val listingDto = jValue.extract[ListingDto]

    val result = listingService.modifyListing(id, listingDto)

    result match {
      case Left(listing: Listing) => NoContent()
      case Right(error: ListingError) => NotFound(error.message)
    }
  }

  val deleteListing =
    (apiOperation[Listing]("deleteListing")
      summary "Delete a listing by id"
      parameters (
      pathParam[String]("id").description("id of the listing")
    ))

  delete("/:id", operation(deleteListing)) {
    val id = params("id")
    val result = listingService.deleteListing(id)

    result match {
      case Left(listing: Listing) => NoContent()
      case Right(error: ListingError) => NotFound(error.message)
    }
  }

}
