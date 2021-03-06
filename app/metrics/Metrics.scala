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

package metrics

import com.codahale.metrics.Timer
import com.codahale.metrics.Timer.Context
import com.kenshoo.play.metrics.MetricsRegistry
import metrics.MetricsEnum.MetricsEnum

trait Metrics {

  def startTimer(api: MetricsEnum): Timer.Context

  def incrementSuccessCounter(api: MetricsEnum): Unit

  def incrementFailedCounter(api: MetricsEnum): Unit

}

object Metrics extends Metrics {

  val timers = Map(
    MetricsEnum.GG_ADMIN_ADD_KNOWN_FACTS -> MetricsRegistry.defaultRegistry.timer("gga-add-known-facts-agent-response-timer"),
    MetricsEnum.ETMP_REGISTER_BUSINESS_PARTNER -> MetricsRegistry.defaultRegistry.timer("etmp-create-business-partner-response-timer")

  )

  val successCounters = Map(
    MetricsEnum.GG_ADMIN_ADD_KNOWN_FACTS -> MetricsRegistry.defaultRegistry.counter("gga-add-known-facts-agent-success-counter"),
    MetricsEnum.ETMP_REGISTER_BUSINESS_PARTNER -> MetricsRegistry.defaultRegistry.counter("etmp-create-business-partner-success-counter")

  )

  val failedCounters = Map(
    MetricsEnum.GG_ADMIN_ADD_KNOWN_FACTS -> MetricsRegistry.defaultRegistry.counter("gga-add-known-facts-agent-failed-counter"),
    MetricsEnum.ETMP_REGISTER_BUSINESS_PARTNER -> MetricsRegistry.defaultRegistry.counter("etmp-create-business-partner-failed-counter")
  )

  override def startTimer(api: MetricsEnum): Context = timers(api).time()

  override def incrementSuccessCounter(api: MetricsEnum): Unit = successCounters(api).inc()

  override def incrementFailedCounter(api: MetricsEnum): Unit = failedCounters(api).inc()

}
