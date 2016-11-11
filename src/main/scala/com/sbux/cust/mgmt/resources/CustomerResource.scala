package com.sbux.cust.mgmt.resources

import akka.http.scaladsl.server.Route
import com.sbux.cust.mgmt.entities.{Customer, CustomerUpdate}
import com.sbux.cust.mgmt.routing.MyResource
import com.sbux.cust.mgmt.services.CustomerService

trait CustomerResource extends MyResource {

  val customerService: CustomerService

  def customerRoutes: Route = pathPrefix("customers") {
    pathEnd {
      post {
        entity(as[Customer]) { customer =>
          completeWithLocationHeader(
            resourceId = customerService.createCustomer(customer),
            ifDefinedStatus = 201, ifEmptyStatus = 409)
          }
        }
    } ~
    path(Segment) { id =>
      get {
        complete(customerService.getCustomer(id))
      } ~
      put {
        entity(as[CustomerUpdate]) { update =>
          complete(customerService.updateCustomer(id, update))
        }
      } ~
      delete {
        complete(customerService.deleteCustomer(id))
      }
    }

  }
}
