/*
 * Copyright 2019 HM Revenue & Customs
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

package router.services

import config.{AppConfig, FeatureSwitch}
import javax.inject.Inject
import play.api.libs.json.JsValue
import play.api.mvc.Request
import router.connectors.{CharitableGivingConnector, SelfAssessmentConnector}
import router.constants.Versions.{VERSION_1, VERSION_2}
import router.httpParsers.SelfAssessmentHttpParser.SelfAssessmentOutcome
import uk.gov.hmrc.http.HeaderCarrier

import scala.concurrent.Future

class CharitableGivingService @Inject()(val appConfig: AppConfig,
                                        val charitableGivingConnector: CharitableGivingConnector,
                                        val selfAssessmentConnector: SelfAssessmentConnector) extends Service {


  def put(body: JsValue)(implicit hc: HeaderCarrier, req: Request[_]): Future[SelfAssessmentOutcome] = {

    withApiVersion {
      case Some(VERSION_1) => selfAssessmentConnector.put(req.uri, body) 
      case Some(VERSION_2) => {
        if (FeatureSwitch(appConfig.featureSwitch).isCharitableGivingV2Enabled) { 
          charitableGivingConnector.put(s"/$VERSION_2${req.uri}", body) 
        } else { 
          selfAssessmentConnector.put(req.uri, body)(convertHeaderToVersion1, req)
        }
      }
    }
  }

  def get()(implicit hc: HeaderCarrier, req: Request[_]): Future[SelfAssessmentOutcome] = {
    withApiVersion {
      case Some(VERSION_1) => selfAssessmentConnector.get(req.uri)
      case Some(VERSION_2) => {
        if (FeatureSwitch(appConfig.featureSwitch).isCharitableGivingV2Enabled) {
          charitableGivingConnector.get(s"/$VERSION_2${req.uri}")
        } else {
          selfAssessmentConnector.get(req.uri)(convertHeaderToVersion1, req)
        }
      }
    }
  }

}
