package example

import org.openjdk.jmh.annotations.{Benchmark, Scope, State}

object DEBenchmark {
  @State(Scope.Group)
  val urls = Seq(
    "http://www.example.com/some_path?parameter=10",
    "https://www.example.com/some_path?parameter=10",
    "http://www1.example.com/some_path?paratemer=10",
    "http://www1.example.com/some_path?paratemer=10&url=http://www.something.com?b",
    "http://www1.example.com/some_path/?paratemer=10&url=http://www.something.com?b",
    "http://www1.example-example.com/some_path?paratemer=10",
    "http://www1.example-example.example-example.com.ua/some_path",
    "http://www2.example.com./some_path?paratemer=10",
    "http://www.example.com./some_path?q=bad query parameters",
    "https://example.com",
    "https://www1.example.com:5432?",
    "www.example.com",
    "www2.example.com",
    "www.example.com:8080",
    "example1234567890.com",
    "example.com.ua:8081",
    "example.com.ua?boost=1",
    "ftp://example.com.ua?boost=1",
    "EXAMPLE.com.ua",
    "192.168.0.1",
    "http://www.libertyland.tv/sport/football/philipp-coutinho-new-liverpool-transfer",
    "https://flipboard.com/@TheDailyBeast"
  )

}
class DEBenchmark {

  import DEBenchmark._

  @Benchmark
  def runOldExtractor() = {
    urls.foreach(DomainExtractorOld.tryExtractHost(_, false))
  }

  @Benchmark
  def runNewExtractor() = {
    urls.foreach(DomainExtractor.tryExtractHost(_, false))
  }

}
