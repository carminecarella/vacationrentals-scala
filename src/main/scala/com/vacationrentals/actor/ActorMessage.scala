package com.vacationrentals.actor

import com.vacationrentals.dto.ListingDto

case class ActorMessage()

case class ActorMessageFindById(id: String)

case class ActorMessageAdd(listingDto: ListingDto)

case class ActorMessageModify(id: String, listingDto: ListingDto)

case class ActorMessageDelete(id: String)