package cn.codetector.jet.jetcontroller.api

import cn.codetector.jet.jetcontroller.service.MicroService
import cn.codetector.jet.jetcontroller.service.ServiceManager
import cn.codetector.jet.jetsimplejson.JSONArray
import cn.codetector.jet.jetsimplejson.JSONObject
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch

/**
 * Created by Codetector on 2017/3/21.
 * Project Jet
 */
class WebService(private val vertx: Vertx) {
    val httpServer = vertx.createHttpServer()
    val rootRouter = Router.router(vertx)
    fun initAPI() {
        registerRoutes()
    }

    fun startServer() {
        httpServer.requestHandler(rootRouter::accept).listen(9090)
    }

    private fun registerRoutes() {
        rootRouter.route().handler { ctx ->
            ctx.response().putHeader("Access-Control-Allow-Origin", "*")
            ctx.next()
        }
        rootRouter.route().handler(BodyHandler.create())
    }

    fun registerServiceAPIRoutes(mgr: ServiceManager) {
        val router = Router.router(vertx)
        rootRouter.mountSubRouter("/admin/services", router)

        router.get("/list").handler { ctx ->
            val arr = JSONArray()
            mgr.listServices().forEach {
                arr.add(JSONObject().put("id", it.id).put("name", it.name).put("status", if (it.status == MicroService.ServiceStatus.NORMAL) it.numOfAvailNode() else -1))
            }
            ctx.response().end(JSONObject().put("services", arr).encode())
        }
        router.post("/add").handler { ctx ->
            val serviceId = ctx.request().getFormAttribute("serviceId")
            val serviceName = ctx.request().getFormAttribute("serviceName")
            if (serviceId != null && serviceName != null) {
                launch(CommonPool) {
                    mgr.createService(serviceId, serviceName)
                    println("processed")
                    ctx.response().end()
                }
            } else {
                ctx.fail(400)
            }
        }
        router.post("/enable").handler { ctx ->
            val targetService = ctx.request().getFormAttribute("targetService")
            if (targetService != null) {
                launch(CommonPool) {
                    try {
                        mgr.enableService(targetService)
                        ctx.response().end()
                    } catch (e: Throwable) {
                        ctx.fail(400)
                    }
                }
            } else {
                ctx.fail(400)
            }
        }
        router.post("/disable").handler { ctx ->
            val targetService = ctx.request().getFormAttribute("targetService")
            if (targetService != null) {
                launch(CommonPool) {
                    try {
                        mgr.disableService(targetService)
                        ctx.response().end()
                    } catch (e: Throwable) {
                        ctx.fail(400)
                    }
                }
            } else {
                ctx.fail(400)
            }
        }
    }
}