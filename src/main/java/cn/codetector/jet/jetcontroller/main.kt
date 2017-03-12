package cn.codetector.jet.jetcontroller

import cn.codetector.jet.jetcontroller.servicemanager.communication.ServiceCommunicationManager
import cn.codetector.jet.jetcontroller.servicemanager.communication.ServiceCommunicationServer
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.slf4j.LoggerFactory

/**
 * Created by Codetector on 2017/3/7.
 * Project Classroom
 */
fun main(args: Array<String>) {
    val server = ServiceCommunicationManager().startServer()
    server.nettyServer.channel.closeFuture().sync()
}