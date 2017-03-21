package cn.codetector.jet.jetcontroller

import cn.codetector.jet.jetcontroller.api.WebService
import cn.codetector.jet.jetcontroller.service.ServiceManager
import io.vertx.core.Vertx

/**
 * Created by Codetector on 2017/3/7.
 * Project Classroom
 */
fun main(args: Array<String>) {
    System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.SLF4JLogDelegateFactory")
    val vertx = Vertx.vertx()
    val servicemgr = ServiceManager(vertx)
    servicemgr.initialize()
    val webService = WebService(vertx)
    webService.initAPI()
    webService.registerServiceAPIRoutes(servicemgr)

    webService.startServer()

}