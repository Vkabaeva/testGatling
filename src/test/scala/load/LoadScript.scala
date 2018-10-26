package load

import io.gatling.core.ConfigKeys._
import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class LoadScript extends Simulation {

  //конфигурация
  //ssv - формат файла, circular - меод обхода значений в файле (по кругу)
  val users = ssv("C:\\testUsers.csv").circular

  //конфиг http с настройками для всех запросов
  val httpConf = http
    .baseURL("https://reqres.in/")
    .acceptHeader("*/*")
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3")
    .userAgentHeader("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0")
    .check(status is 200)

  //сценарий
  val basicLoad = scenario("BASIC_LOAD").feed(users).during(20 minutes) {
    exec(BasicLoad.start)
  }
  setUp(basicLoad.inject(rampUsers(1000) over(20 minutes))
    .protocols(httpConf))
    .maxDuration(21 minutes)

  //профиль нагрузки
  object BasicLoad {
    val start =
      exec(
        http("HTTP request create user")
          .post("/api/users")
          .formParam("name", "${Username}")
          .formParam("job", "${Company}")
      )
      .exec(
        http("HTTP request users")
          .get("/api/users")
      )
  }


}
