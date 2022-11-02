package example


import dk.brics.automaton.{Automaton, RegExp, RunAutomaton}
import example.DomainExtractorOld.protocolAndWwwRegex

import scala.util.matching.Regex

object DomainExtractor {
  private val HOST_END_SYMBOLS = Array(':', '?', '/')
  private val protocolAndWwwAutomaton = new RunAutomaton(new RegExp("""([a-zA-Z]+[a-zA-Z0-9]*://)?(www[0-9]*\.)?""").toAutomaton(), true)
  private val hostValidationAutomaton: RunAutomaton = new RunAutomaton(new RegExp(
    """(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\-]*[a-zA-Z0-9])\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\-]*[A-Za-z0-9])"""
  ).toAutomaton(), true)



  def tryExtractHost(url: String, allowFirstLevelDomains: Boolean = false): Option[String] = {
    val hostStart = {
      val matcher = protocolAndWwwAutomaton.newMatcher(url)
      if (matcher.find() && matcher.start() == 0) {
          matcher.end()
      } else 0
    }
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
    } else if (hostValidationAutomaton.run(host)) {
      Some(host)
    } else {
      None
    }
  }

}
