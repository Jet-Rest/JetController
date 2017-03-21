package cn.codetector.jet.jetcontroller.service

import cn.codetector.jet.jetcontroller.util.DatabaseConfiguration
import cn.codetector.jet.jetcontroller.util.DatabaseConfiguration.db_prefix
import cn.codetector.vertx.jdbc.coroutine.getConnection
import cn.codetector.vertx.jdbc.coroutine.query
import cn.codetector.vertx.jdbc.coroutine.update
import cn.codetector.vertx.jdbc.coroutine.updateWithParams
import io.vertx.core.Vertx
import io.vertx.core.json.JsonArray
import io.vertx.ext.jdbc.JDBCClient
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import org.slf4j.LoggerFactory
import java.util.*

/**
 * Created by Codetector on 2017/3/16.
 * Project Jet
 */
class ServiceManager(private val vertX: Vertx) {
    val dbClient = JDBCClient.createShared(vertX, DatabaseConfiguration.getVertXJDBCConfigObject())

    companion object {
        private val logger = LoggerFactory.getLogger(ServiceManager::class.java)
    }

    // SQL Queries
    private val serviceTableCreation = "CREATE TABLE IF NOT EXISTS `${db_prefix}_services` ( `serviceId` VARCHAR(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'Unique id of the service' , `serviceName` VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'Human readable name of the service', `serviceStatus` INT NOT NULL DEFAULT '0' COMMENT 'Current status of the service (Normal(0) or Disabled(-1))' , PRIMARY KEY (`serviceId`), UNIQUE (`serviceName`)) ENGINE = InnoDB;"

    private val services: MutableMap<String, MicroService> = TreeMap()

    fun initialize() {
        launch(CommonPool) {
            dbClient.getConnection().use { conn ->
                conn.update(serviceTableCreation)
            }
            dbClient.getConnection().use { conn ->
                conn.query("SELECT * FROM `${db_prefix}_services`").rows.forEach { row ->
                    services.put(row.getString("serviceId"), MicroService(row.getString("serviceId"), row.getString("serviceName"), row.getInteger("serviceStatus")))
                }
            }
        }
    }

    suspend fun createService(id: String, name: String){
        dbClient.getConnection().use { connection ->
            connection.updateWithParams("INSERT INTO `${db_prefix}_services` (`serviceId`,`serviceName`) VALUES (?, ?)",
                    JsonArray().add(id).add(name))
            services.put(id, MicroService(id, name))
        }
    }

    fun listServices(): Collection<MicroService>{
        return services.values.toList()
    }

    suspend fun disableService(serviceId: String) {
        val service = services.get(serviceId)
        if (service != null) {
            service.status = MicroService.ServiceStatus.DISABLED
            saveServiceState(service)
        } else {
            throw IllegalArgumentException("Service Not Found")
        }
    }

    suspend fun enableService(serviceId: String) {
        val service = services.get(serviceId)
        if (service != null) {
            service.status = MicroService.ServiceStatus.NORMAL
            saveServiceState(service)
        } else {
            throw IllegalArgumentException("Service Not Found")
        }
    }

    suspend fun saveServiceState(service: MicroService) {
        dbClient.getConnection().use { connection ->
            connection.updateWithParams("INSERT INTO `${db_prefix}_services` (`serviceId`,`serviceName`, `serviceStatus`) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE `serviceStatus` = VALUES(`serviceStatus`)",
                    JsonArray().add(service.id).add(service.name).add(service.status.status))
            return
        }
    }
}