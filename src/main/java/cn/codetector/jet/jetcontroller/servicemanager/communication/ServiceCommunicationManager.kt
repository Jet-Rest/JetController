package cn.codetector.jet.jetcontroller.servicemanager.communication

import cn.codetector.jet.jetconfiguration.ConfigurationManager
import cn.codetector.jet.jetcontroller.util.LogAndDropHandler
import cn.codetector.jet.jetcontroller.util.NettyServer
import io.netty.handler.codec.LengthFieldBasedFrameDecoder

/**
 * Created by Codetector on 2017/3/8.
 * Project Jet
 */
class ServiceCommunicationManager {
    private val config = ConfigurationManager.defaultManager.getJsonConfiguration("serviceCommunication")
    // Configuration fields
    private val targetPort = config.getInteger("serviceCommPort", 19578)
    private val maxLength = config.getInteger("maxPacketLength", 1024000)

    var nettyServer = NettyServer(targetPort, { ch ->
        ch.pipeline().addFirst(LengthFieldBasedFrameDecoder(maxLength, 0, 4, 0, 4))
    })

    fun startServer(): ServiceCommunicationManager {
        nettyServer.startServer()
        return this
    }

}