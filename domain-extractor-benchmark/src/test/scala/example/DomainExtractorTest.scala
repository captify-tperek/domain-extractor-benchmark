package example

import org.scalatest.Inspectors
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DomainExtractorTest extends AnyWordSpec with Matchers with Inspectors {

  "DomainExtractor" should {
    "extract domain" in {
      val urls = Map(
        "http://www.example.com/some_path?parameter=10" -> "example.com",
        "https://www.example.com/some_path?parameter=10" -> "example.com",
        "http://www1.example.com/some_path?paratemer=10" -> "example.com",
        "http://www1.example.com/some_path?paratemer=10&url=http://www.something.com?b" -> "example.com",
        "http://www1.example.com/some_path/?paratemer=10&url=http://www.something.com?b" -> "example.com",
        "http://www1.example-example.com/some_path?paratemer=10" -> "example-example.com",
        "http://www1.example-example.example-example.com.ua/some_path" -> "example-example.example-example.com.ua",
        "http://www2.example.com./some_path?paratemer=10" -> "example.com",
        "http://www.example.com./some_path?q=bad query parameters" -> "example.com",
        "https://example.com" -> "example.com",
        "https://www1.example.com:5432?" -> "example.com",
        "www.example.com" -> "example.com",
        "www2.example.com" -> "example.com",
        "www.example.com:8080" -> "example.com",
        "example1234567890.com" -> "example1234567890.com",
        "example.com.ua:8081" -> "example.com.ua",
        "example.com.ua?boost=1" -> "example.com.ua",
        "ftp://example.com.ua?boost=1" -> "example.com.ua",
        "EXAMPLE.com.ua" -> "EXAMPLE.com.ua",
        "192.168.0.1" -> "192.168.0.1",
        "http://www.libertyland.tv/sport/football/philipp-coutinho-new-liverpool-transfer" -> "libertyland.tv",
        "https://flipboard.com/@TheDailyBeast" -> "flipboard.com"
      )
      forAll(urls) { case (url, host) =>
        DomainExtractor.tryExtractHost(url) shouldBe Some(host)
      }
    }
  }
}
