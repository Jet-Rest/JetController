package cn.codetector.jet.jetcontroller.servicemanager.communication

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel


/**
 * Created by Codetector on 2017/3/8.
 * Project Jet
 */
class ServiceCommunicationServer(val bindPort: Int) {
    fun startServer() {
        val bossGroup = NioEventLoopGroup()
        val workerGroup = NioEventLoopGroup()

        val b = ServerBootstrap()
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel::class.java)
                .childHandler(object : ChannelInitializer<SocketChannel>() {
                    override fun initChannel(channel: SocketChannel?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })
        val channelFuture = b.bind(bindPort).sync()

    }
}