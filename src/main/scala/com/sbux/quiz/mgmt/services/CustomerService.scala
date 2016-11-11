package com.sbux.quiz.mgmt.services

import com.sbux.quiz.mgmt.entities.{Customer, CustomerUpdate}
import com.sbux.quiz.mgmt.resources.CassandraResource

import scala.concurrent.{ExecutionContext, Future}

class CustomerService(implicit val executionContext: ExecutionContext) {

  var customers = Vector.empty[Customer]

  def createCustomer(customer: Customer): Future[Option[String]] = Future {
    customers.find(_.id == customer.id) match {
      case Some(q) => None // Conflict! id is already taken
      case None =>
        println("About to invoke DB")
        //CassandraResource.execute(customer)
        println("After DB invoke")
        customers = customers :+ customer
        Some(customer.id)
    }
  }

  def getCustomer(id: String): Future[Option[Customer]] = Future {
    customers.find(_.id == id)
  }

  def updateCustomer(id: String, update: CustomerUpdate): Future[Option[Customer]] = {

    def updateEntity(customer: Customer): Customer = {
      val title = update.name.getOrElse(customer.name)
      val text = update.mobile.getOrElse(customer.mobile)
      Customer(id, title, text)
    }

    getCustomer(id).flatMap { maybeQuestion =>
      maybeQuestion match {
        case None => Future { None } // No question found, nothing to update
        case Some(customer) =>
          val updatedCustomer = updateEntity(customer)
          deleteCustomer(id).flatMap { _ =>
             createCustomer(updatedCustomer).map(_ => Some(updatedCustomer))
          }
      }
    }
  }

  def deleteCustomer(id: String): Future[Unit] = Future {
    customers = customers.filterNot(_.id == id)
  }


}

