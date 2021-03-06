/*
 * Copyright 2016 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers

import connectors.{EtmpConnector, GovernmentGatewayAdminConnector}
import play.api.Logger
import play.api.mvc.Action
import uk.gov.hmrc.play.microservice.controller.BaseController

import scala.concurrent.ExecutionContext.Implicits.global

trait BusinessRegistrationController extends BaseController {

  val desConnector: EtmpConnector

  def register(utr: String) = Action.async {
    implicit request =>
      val json = request.body.asJson.get
      desConnector.register(json).map {
        registerData =>
          Logger.info(
            s"""[BusinessRegistrationController] [register] [registerData.status]
                |= ${registerData.status} && [registerData.body] = ${registerData.body}""".stripMargin)
          registerData.status match {
            case OK => Ok(registerData.body)
            case NOT_FOUND => NotFound(registerData.body)
            case BAD_REQUEST => BadRequest(registerData.body)
            case SERVICE_UNAVAILABLE => ServiceUnavailable(registerData.body)
            case INTERNAL_SERVER_ERROR | _ => InternalServerError(registerData.body)
          }
      }
  }
}

object BusinessRegistrationController extends BusinessRegistrationController {
  val desConnector: EtmpConnector = EtmpConnector
  val ggAdminConnector: GovernmentGatewayAdminConnector = GovernmentGatewayAdminConnector
}

object SaBusinessRegistrationController extends BusinessRegistrationController {
  val desConnector: EtmpConnector = EtmpConnector
  val ggAdminConnector: GovernmentGatewayAdminConnector = GovernmentGatewayAdminConnector
}

object AgentBusinessRegistrationController extends BusinessRegistrationController {
  val desConnector: EtmpConnector = EtmpConnector
  val ggAdminConnector: GovernmentGatewayAdminConnector = GovernmentGatewayAdminConnector
}
