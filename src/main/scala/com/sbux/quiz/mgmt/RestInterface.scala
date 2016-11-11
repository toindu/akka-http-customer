package com.sbux.quiz.mgmt

import akka.http.scaladsl.server.Route
import com.sbux.quiz.mgmt.resources.CustomerResource
import com.sbux.quiz.mgmt.services.CustomerService

import scala.concurrent.ExecutionContext

trait RestInterface extends Resources {

  implicit def executionContext: ExecutionContext

  lazy val customerService = new CustomerService

  val routes: Route = customerRoutes

}

trait Resources extends CustomerResource

