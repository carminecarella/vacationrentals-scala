package com.vacationrentals.servlet

import org.scalatra.ScalatraServlet
import org.scalatra.swagger.{ApiInfo, NativeSwaggerBase, Swagger}

class ApiDocServlet(val swagger: Swagger) extends ScalatraServlet with NativeSwaggerBase

object ListingSwagger {
  val info = ApiInfo("The Listings API",
  "Docs for the Listings API",
  "",
  "",
  "MIT",
  "http://opensource.org/licenses/MIT")
}

class ListingSwagger extends Swagger(Swagger.SpecVersion, "1.0.0", ListingSwagger.info)
