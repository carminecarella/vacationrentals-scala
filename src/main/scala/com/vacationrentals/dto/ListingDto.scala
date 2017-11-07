package com.vacationrentals.dto

case class ListingDto(contact: ContactDto, address: AddressDto, location: LocationDto)

case class ContactDto(phone: String, formattedPhone: String)

case class AddressDto(address: String, postalCode: String, countryCode: String, city: String, state: String, country: String)

case class LocationDto(lat: Double, lng: Double)

