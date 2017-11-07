package com.vacationrentals.dao

import com.vacationrentals.dto.ListingDto

trait Dao [T] {

  def findAll(): List[T]

  def findById(id: String): Option[T]

  def create(entity: ListingDto): Option[T]

  def modify(id: String, listingDto: ListingDto): Option[T]

  def delete(id: String): Option[T]

}
