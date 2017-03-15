package cn.codetector.jet.jetcontroller.servicemanager.communication

import cn.codetector.jet.jetconfiguration.ConfigurationManager
import cn.codetector.jet.jetcontroller.servicemanager.communication.handler.PacketTypeClassifier
import cn.codetector.jet.jetcontroller.util.NettyServer
import cn.codetector.jet.jetcontroller.util.formatSizeLength
import cn.codetector.jet.std.network.decoder.ByteToRawpacketDecoder
import cn.codetector.jet.std.network.decoder.PacketLengthBasedDecoder
import cn.codetector.jet.std.network.decoder.RawPacketClassifier
import cn.codetector.jet.std.network.encoder.JsonPacketEncoder
import cn.codetector.jet.std.network.encoder.PuretextPacketEncoder
import cn.codetector.jet.std.network.encoder.RawPacketEncoder
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
        ch.pipeline()
                .addLast("Packet Length Header Stripper", PacketLengthBasedDecoder(maxLength))
                .addLast("RawPacket Decoder", ByteToRawpacketDecoder())
                .addLast("Raw Packet Classifier", RawPacketClassifier())
                // Encoders
                .addLast("Raw Packet Encoder", RawPacketEncoder())
                .addLast("PureText Encoder", PuretextPacketEncoder())
                .addLast("Json -> text Packet", JsonPacketEncoder())
                // Handlers
                .addLast(PacketTypeClassifier())
    })

    fun startServer(): ServiceCommunicationManager {
        nettyServer.startServer()
        logger.info("Listening for Service on Port ${targetPort}")
        logger.info("Service Network max frame length ${maxLength.toLong().formatSizeLength()}")
        return this
    }

}