package com.sbux.common.security

import akka.http.scaladsl.server.{Directive1, Directives}

/**
  * Created by inbanerj on 12/3/16.
  */
object CdxAuthenticate extends Directives {

  val userRepo = LoginToken

  def authenticateWithRoles(roles: Seq[String]): Directive1[User] = {
    optionalHeaderValueByName("authentication") flatMap {
      case _ =>
        //val accessToken = authHeader.split(' ').last
        onSuccess(userRepo.authenticateUser("admin","admin")).flatMap {
          case Some(user) =>
            if (roles.contains(user.role)) {
              provide(user)
            } else {
              complete((403, "You are not authorized with the privileges to do this action"))
            }
          case _ => complete((401, "Wrong Authorization header"))
        }
//      case _ => complete((401, "Wrong Authorization header"))
    }
  }
}