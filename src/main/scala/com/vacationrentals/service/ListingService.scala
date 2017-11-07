package com.vacationrentals.service

import com.vacationrentals.dao.ListingDao
import com.vacationrentals.dto.ListingDto
import com.vacationrentals.model.{Listing, ListingError}

import scala.language.postfixOps

class ListingService(listingDao: ListingDao) {

  def searchAllListing() : List[Listing] = {
    listingDao.findAll()
  }

  def searchListingById(id: String): Either[Listing, ListingError]  = {

    val result = listingDao.findById(id)

    result match {
      case Some(result) => Left(result)
      case None => Right(new ListingError("Listing not found"))
    }

  }

  def addListing(listingDto: ListingDto): Either[Listing, ListingError] = {
    val result = listingDao.create(listingDto)

    result match {
      case Some(result) => Left(result)
      case None => Right(new ListingError("Listing not created"))
    }

  }

  def modifyListing(id: String, listingDto: ListingDto): Either[Listing, ListingError] = {
    val result = listingDao.modify(id, listingDto)

    result match {
      case Some(result) => Left(result)
      case None => Right(new ListingError("Listing not modified"))
    }
  }

  def deleteListing(id: String): Either[Listing, ListingError] = {
    val result = listingDao.delete(id)

    result match {
      case Some(result) => Left(result)
      case None => Right(new ListingError("Listing not found to delete"))
    }
  }

}
