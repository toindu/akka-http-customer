package com.sbux.common.security


import java.util.UUID.randomUUID
import org.joda.time.DateTime
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.{ExecutionContext, Future}

case class LoginToken(token: String, modified: DateTime, created: DateTime)

case class User(name: String, pass: String, role: String)

object LoginToken {
  implicit val executionContext: ExecutionContext = global


  def authenticateUser(username: String, password: String): Future[Option[User]] = {
    println(s"Attempting to log in user with username: $username")
    username match {

            case "admin" => {
              password match {
                case "admin" =>
                  println(s"User: $username successfully logged in")
                  Future(Some(User(username, password, "admin")))
                case _ =>
                  println(s"User: $username was denied login because of wrong password")
                  Future(None)
              }
            }
              case _ => Future(None)
            }

  }

  def generateLoginToken(username: String, password: String): Future[Option[LoginToken]] =
    authenticateUser(username, password) flatMap {
      case Some(user) => {
        val created = DateTime.now
        val token = LoginToken(randomUUID.toString, created, created)
        Future(Some(token))
        }
      case _ => Future(None)
    }
}