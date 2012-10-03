package no.finn

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.WebAppContext
import org.eclipse.jetty.util.resource.ResourceCollection
import org.eclipse.jetty.server.handler.ContextHandlerCollection
import scala.Array
import java.io.File

/**
 * User: fijobere
 */
object TestLauncher extends App{

  val server = new Server(8080)

  val web = new WebAppContext()
  web.setContextPath("/")
  web.setBaseResource(new ResourceCollection(dirs(
    "src/main/webapp",
    "src/main/resources"
  )))

  val contexts = new ContextHandlerCollection()
  contexts.setHandlers(Array(web))

  server.setHandler(contexts)

  server.start()
  server.join()

  def dirs(ds: String*): Array[String] = ds.map(new File(_)).filter(_.exists()).map(_.getPath).toArray



}
