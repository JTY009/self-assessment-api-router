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

package config

import router.enums.SourceType.SourceType
import play.api.Configuration

case class FeatureSwitch(value: Option[Configuration]) {
  val DEFAULT_VALUE = true

  def isEnabled(sourceType: SourceType): Boolean = value match {
    case Some(config) => FeatureConfig(config).isSourceEnabled(sourceType.toString)
    case None => DEFAULT_VALUE
  }

  def isEnabled(sourceType: SourceType, summary: Option[String]): Boolean = value match {
    case Some(config) =>
      summary match {
        case None | Some("") => FeatureConfig(config).isSourceEnabled(sourceType.toString)
        case Some(_summary) => FeatureConfig(config).isSummaryEnabled(sourceType.toString, _summary)
      }
    case None => DEFAULT_VALUE
  }

  def isWhiteListingEnabled: Boolean = {
    value match {
      case Some(config) => config.getBoolean("white-list.enabled").getOrElse(false)
      case None => false
    }
  }

  def isAgentSimulationFilterEnabled: Boolean = value match {
    case Some(config) => config.getBoolean("test-scenario-simulation.enabled").getOrElse(false)
    case None => false
  }

  def whiteListedApplicationIds: Seq[String] = {
    value match {
      case Some(config) =>
        config
          .getStringSeq("white-list.applicationIds")
          .getOrElse(throw new RuntimeException(s"feature-switch.white-list.applicationIds is not configured"))
      case None => Seq()
    }
  }

  def isRelease2Enabled: Boolean = value match {
    case Some(config) => config.getBoolean("release-2.enabled").getOrElse(false)
    case None => false
  }

  def isCharitableGivingV2Enabled: Boolean = value match {
    case Some(config) => config.getBoolean("charitable-giving-version-2.enabled").getOrElse(false)
    case None => false
  }


}

sealed case class FeatureConfig(config: Configuration) {

  def isSummaryEnabled(source: String, summary: String): Boolean = {
    val summaryEnabled = config.getBoolean(s"$source.$summary.enabled") match {
      case Some(flag) => flag
      case None => true
    }
    isSourceEnabled(source) && summaryEnabled
  }

  def isSourceEnabled(source: String): Boolean = {
    config.getBoolean(s"$source.enabled").getOrElse(true)
  }
}
