package cn.codetector.jet.jetcontroller.servicemanager.communication

import cn.codetector.jet.jetconfiguration.ConfigurationManager
import cn.codetector.jet.jetcontroller.util.NettyServer
import cn.codetector.jet.jetcontroller.util.formatSizeLength
import io.netty.handler.codec.LengthFieldBasedFrameDecoder
import org.slf4j.LoggerFactory

/**
 * Created by Codetector on 2017/3/8.
 * Project Jet
 */
class ServiceCommunicationManager {
    companion object {
        private val logger = LoggerFactory.getLogger(ServiceCommunicationManager::class.java)
    }
    private val config = ConfigurationManager.defaultManager.getJsonConfiguration("serviceCommunication")
    // Configuration fields
    private val targetPort = config.getInteger("serviceCommPort", 19578)
    private val maxLength = config.getInteger("maxPacketLength", 1049000)

    var nettyServer = NettyServer(targetPort, { ch ->
        logger.debug("Connection established ${ch.remoteAddress().address}")
        ch.pipeline().addLast("Packet Length Header Stripper", LengthFieldBasedFrameDecoder(maxLength, 0, 4, 0, 4))
    })

    fun startServer(): ServiceCommunicationManager {
        nettyServer.startServer()
        logger.info("Listening for Service on Port ${targetPort}")
        logger.info("Service Network max frame length ${maxLength.toLong().formatSizeLength()}")
        return this
    }

}