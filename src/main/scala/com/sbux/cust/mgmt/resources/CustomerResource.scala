package com.sbux.cust.mgmt.resources

import akka.http.scaladsl.server.{Directives, Route}
import com.sbux.cust.mgmt.entities.{Customer, CustomerUpdate}
import com.sbux.cust.mgmt.routing.MyResource
import com.sbux.cust.mgmt.services.CustomerService
import com.sbux.common.security.CdxAuthenticate.authenticateWithRoles

trait CustomerResource extends MyResource with Directives {

  val customerService: CustomerService

  def customerRoutes: Route = pathPrefix("customers") {
      pathEnd {
        authenticateWithRoles(Seq("user","admin")) { user =>
          post {
            entity(as[Customer]) { customer =>
              completeWithLocationHeader(
                resourceId = customerService.createCustomer(customer),
                ifDefinedStatus = 201, ifEmptyStatus = 409)
            }
          }
        }
      } ~
        path(Segment) { id =>
          get {
            authenticateWithRoles(Seq("consumer", "user","admin")) { user =>
              complete(customerService.getCustomer(id))
            }
          } ~
            put {
              authenticateWithRoles(Seq("user","admin")) { user =>
                entity(as[CustomerUpdate]) { update =>
                  complete(customerService.updateCustomer(id, update))
                  }
              }
            } ~
            delete {
              authenticateWithRoles(Seq("admin")) { user =>
              complete(customerService.deleteCustomer(id))
              }
            }
        }
    }
}

