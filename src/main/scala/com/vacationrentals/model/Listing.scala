package com.vacationrentals.model

case class Listing(id: String, contact: Contact, address: Address, location: Location)

case class Contact(phone: String, formattedPhone: String)

case class Address(address: String, postalCode: String, countryCode: String, city: String, state: String, country: String)

case class Location(lat: Double, lng: Double)