/*
 * Copyright (c) 2016. Codetector (Yaotian Feng)
 */

package cn.codetector.jet.jetcontroller.util

import cn.codetector.jet.jetconfiguration.ConfigurationManager
import io.vertx.core.json.JsonObject

/**
 * Created by Codetector on 2016/7/14.
 * Project Jet
 */
object DatabaseConfiguration {
    private val dbConfig = ConfigurationManager.defaultManager.getJsonConfiguration("mysql.json")

    val driver_class = dbConfig.getString("driver_class", "com.mysql.jdbc.Driver")
    val max_pool_size = dbConfig.getInteger("max_pool_size", 30)
    val initial_pool_size = dbConfig.getInteger("initial_pool_size", 10)
    val db_user = dbConfig.getString("db_user", "jet_controller")
    val db_password = dbConfig.getString("db_password", "")
    val db_url = dbConfig.getString("url", "jdbc:mysql://localhost:3306/")
    val db_name = dbConfig.getString("db_name", "jet_controller")
    val db_ssl = dbConfig.getString("useSSL", "false")
    val db_charset = dbConfig.getString("charSet", "utf-8")
    val db_prefix = dbConfig.getString("db_prefix", "jet_ctrl")
    val db_max_idle_time = dbConfig.getInteger("db_max_idle_time", 30)

    fun getDBConnectionURLWithSettings(): String {
        return "${db_url}${db_name}?useSSL=${db_ssl}&characterEncoding=${db_charset}"
    }

    fun getVertXJDBCConfigObject(): JsonObject {
        val jsonObject = JsonObject()
        jsonObject.put("driver_class", driver_class)
        jsonObject.put("user", db_user)
        jsonObject.put("password", db_password)
        jsonObject.put("max_pool_size", max_pool_size)
        jsonObject.put("initial_pool_size", initial_pool_size)
        jsonObject.put("max_idle_time", db_max_idle_time)
        jsonObject.put("url", getDBConnectionURLWithSettings())
        return jsonObject
    }

}
