package no.finn.plan

import unfiltered.filter.Plan
import unfiltered.request.GET
import unfiltered.response.{ResponseString, Ok}

/**
 * User: fijobere
 */
class TestPlan extends Plan{


  def intent = {

    case req@GET(_) => Ok ~> ResponseString("Hello World from Unfiltered!")

  }



}
