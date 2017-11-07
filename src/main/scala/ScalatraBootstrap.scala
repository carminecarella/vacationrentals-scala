import org.scalatra.LifeCycle
import javax.servlet.ServletContext

import akka.actor.{ActorSystem, Props}
import com.vacationrentals.actor.ListingActor
import com.vacationrentals.dao.ListingDao
import com.vacationrentals.service.ListingService
import com.vacationrentals.servlet.{ApiDocServlet, ListingControllerAsync, ListingServlet, ListingSwagger}

class ScalatraBootstrap extends LifeCycle {

  override def init(context: ServletContext) {

    val swagger = new ListingSwagger
    lazy val listingDao = new ListingDao()
    lazy val listingService = new ListingService(listingDao)

    val system = ActorSystem()
    val listingActor = system.actorOf(Props(new ListingActor(listingService)))

    context mount (new ListingServlet(listingService, swagger), "/listings")
    context.mount(new ApiDocServlet(swagger), "/apidoc")

    context.mount(new ListingControllerAsync(system, listingActor, listingService), "/async/listings")
  }
}