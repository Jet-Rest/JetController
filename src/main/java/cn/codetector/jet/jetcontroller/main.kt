package cn.codetector.jet.jetcontroller

import cn.codetector.jet.jetcontroller.servicemanager.communication.ServiceCommunicationManager

/**
 * Created by Codetector on 2017/3/7.
 * Project Classroom
 */
fun main(args: Array<String>) {
    val server = ServiceCommunicationManager().startServer()
    server.nettyServer.channel.closeFuture().sync()
}