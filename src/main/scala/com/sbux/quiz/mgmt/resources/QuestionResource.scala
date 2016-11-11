package com.sbux.quiz.mgmt.resources

import akka.http.scaladsl.server.Route

import com.sbux.quiz.mgmt.entities.{Question, QuestionUpdate}
import com.sbux.quiz.mgmt.routing.MyResource
import com.sbux.quiz.mgmt.services.QuestionService

trait QuestionResource extends MyResource {

  val questionService: QuestionService

  def questionRoutes: Route = pathPrefix("questions") {
    pathEnd {
      post {
        entity(as[Question]) { question =>
          completeWithLocationHeader(
            resourceId = questionService.createQuestion(question),
            ifDefinedStatus = 201, ifEmptyStatus = 409)
          }
        }
    } ~
    path(Segment) { id =>
      get {
        complete(questionService.getQuestion(id))
      } ~
      put {
        entity(as[QuestionUpdate]) { update =>
          complete(questionService.updateQuestion(id, update))
        }
      } ~
      delete {
        complete(questionService.deleteQuestion(id))
      }
    }

  }
}

