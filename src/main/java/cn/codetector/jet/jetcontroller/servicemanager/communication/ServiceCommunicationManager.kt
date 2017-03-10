package cn.codetector.jet.jetcontroller.servicemanager.communication

import cn.codetector.jet.jetconfiguration.ConfigurationManager
import cn.codetector.jet.jetcontroller.util.LogAndDropHandler
import cn.codetector.jet.jetcontroller.util.NettyServer

/**
 * Created by Codetector on 2017/3/8.
 * Project Jet
 */
class ServiceCommunicationManager {
    private val config = ConfigurationManager.defaultManager.getJsonConfiguration("serviceCommunication")
    // Configuration fields
    private val targetPort = config.getInteger("serviceCommPort", 19578)

    var nettyServer  = NettyServer(targetPort, {ch ->
        ch.pipeline().addLast(LogAndDropHandler())
    })

    fun startServer() : ServiceCommunicationManager{
        nettyServer.startServer()
        return this
    }

}