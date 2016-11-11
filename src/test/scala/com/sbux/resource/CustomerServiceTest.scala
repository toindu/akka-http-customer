package com.sbux.resource

import javax.xml.ws.Response

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpEntity, HttpMethods, HttpRequest, MediaTypes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.stream.ActorMaterializer
import akka.util.{ByteString, Timeout}
import com.sbux.cust.mgmt.RestInterface
import com.sbux.cust.mgmt.resources.CustomerResource
import com.typesafe.config.ConfigFactory
import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.Await


/**
  * Created by inbanerj on 11/10/16.
  */
class CustomerServiceTest extends WordSpec with Matchers with ScalatestRouteTest with RestInterface {
  val config = ConfigFactory.load()
  val host = config.getString("http.host")
  val port = config.getInt("http.port")

//  implicit val system = ActorSystem("quiz-management-service")
//  implicit val materializer = ActorMaterializer()


  implicit val executionContext = system.dispatcher
  //implicit val timeout = Timeout(10)

  "Customers API" should {
    val jsonRequest = ByteString(
      s"""
         |{
         |    "id": "sbc123",
         |    "name" : "Indrajit Banerjee",
         |    "mobile":123456789
         |}
        """.stripMargin)

    val newJsonRequest = ByteString(
      s"""
         |{
         |    "id": "sbc123",
         |    "name" : "Indra Ban",
         |    "mobile":987654321
         |}
        """.stripMargin)

    "Posting to /customers should add the customer" in {
     val postRequest = HttpRequest(
        HttpMethods.POST,
        uri = "/customers",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))

      postRequest ~> routes ~> check {
        println("POST - Response:isSuccess" + status.isSuccess() + "; status.intValue():"  + status.intValue())
        status.intValue() shouldEqual 201
      }
    }

    "get /customers/{id} should retrieve customer" in {
      val getRequest = HttpRequest(
        HttpMethods.GET,
        uri = "/customers/sbc123")

      getRequest ~> routes ~> check {
        println("GET - Response:isSuccess" + status.isSuccess() + "; status.intValue():"  + status.intValue())
        status.intValue() shouldEqual 200
//        response.status match {
//          case OK if (response.entity.contentType == ContentTypes.`application/json`) =>
//            Future.successful(Right(read[Response](Await.result(response.entity.toStrict(3000.millis).map{ entity =>
//              val data = Charset.forName("UTF-8").decode(entity.data.asByteBuffer)
//              data.toString
//            }, Duration.Inf))))
//            println("Response object:" + response.entity.asInstanceOf[String])
//          case BadRequest => Future.successful(Left(s"$latlang: incorrect Latitude and Longitude format"))
//          case _ => Unmarshal(response.entity).to[String].flatMap { entity =>
//            val error = s"Google GeoCoding request failed with status code ${response.status} and entity $entity"
//            Future.failed(new IOException(error))
//          }
//        }
      }
    }

    "Update /customers/{id} should retrieve customer" in {

      val getRequest = HttpRequest(
        HttpMethods.PUT,
        uri = "/customers/sbc123",
        entity = HttpEntity(MediaTypes.`application/json`, newJsonRequest))

      getRequest ~> routes ~> check {
        println("UPDATE - Response:isSuccess" + status.isSuccess() + "; status.intValue():"  + status.intValue())
        status.intValue() shouldEqual 200
      }
    }

    "get after update should have updated value url /customers/{id}" in {
      val getRequest = HttpRequest(
        HttpMethods.GET,
        uri = "/customers/sbc123")

      getRequest ~> routes ~> check {
        println("GET - Response:isSuccess" + status.isSuccess() + "; status.intValue():"  + status.intValue())
        status.intValue() shouldEqual 200
      }
    }

    "Delete /customers/{id} should retrieve customer" in {

      val getRequest = HttpRequest(
        HttpMethods.DELETE,
        uri = "/customers/sbc123")

      getRequest ~> routes ~> check {
        println("DELETE - Response:isSuccess" + status.isSuccess() + "; status.intValue():"  + status.intValue())
        status.intValue() shouldEqual 204
      }
    }

    "get after delete should return 404 /customers/{id}" in {
      val getRequest = HttpRequest(
        HttpMethods.GET,
        uri = "/customers/sbc123")

      getRequest ~> routes ~> check {
        println("GET - Response:isSuccess" + status.isSuccess() + "; status.intValue():"  + status.intValue())
        status.intValue() shouldEqual 404
      }
    }
  }
}
