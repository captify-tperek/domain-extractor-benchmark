package example

import scala.util.matching.Regex

object DomainExtractorOld {
  private val HOST_END_SYMBOLS = Array(':', '?', '/')

  private val protocolAndWwwRegex: Regex = """^((\w+://)?(www\d*\.)?)""".r
  private val hostValidationRegex: Regex =
    """^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\-]*[a-zA-Z0-9])\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\-]*[A-Za-z0-9])$""".r

  def tryExtractHost(url: String, allowFirstLevelDomains: Boolean = false): Option[String] = {
    val hostStart: Int = protocolAndWwwRegex.findFirstIn(url).map(_.length).getOrElse(default = 0)
    val hostEnd: Int = url.indexWhere(s => HOST_END_SYMBOLS.contains(s), hostStart) match {
      case -1 => url.length
      case index => index
    }

    if (hostEnd - hostStart > 1) {
      validatedHost(url.substring(hostStart, hostEnd).trim.stripSuffix("."), allowFirstLevelDomains)
    } else {
      None
    }
  }

  @inline private def validatedHost(host: String, allowFirstLevelDomains: Boolean): Option[String] = {
    if (!allowFirstLevelDomains && !host.contains('.')) {
      None
    } else if (hostValidationRegex.pattern.matcher(host).matches()) {
      Some(host)
    } else {
      None
    }
  }

}
