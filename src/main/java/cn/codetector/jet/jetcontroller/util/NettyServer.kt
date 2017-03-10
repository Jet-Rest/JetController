package cn.codetector.jet.jetcontroller.util

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel

/**
 * Created by Codetector on 2017/3/10.
 * Project Jet
 */
class NettyServer(val bindPort: Int, val channelPipe: (SocketChannel) -> Unit) {

    lateinit var channel: Channel
        private set
    lateinit var bossGroup: NioEventLoopGroup
    lateinit var workerGroup: NioEventLoopGroup
    fun startServer() {
        bossGroup = NioEventLoopGroup()
        workerGroup = NioEventLoopGroup()

        val b = ServerBootstrap()
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel::class.java)
                .childHandler(object : ChannelInitializer<SocketChannel>() {
                    override fun initChannel(channel: SocketChannel?) {
                        channelPipe.invoke(channel!!)
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)

        val channelFuture = b.bind(bindPort).sync()
        channel = channelFuture.channel()
    }

    fun isServerRunning(): Boolean {
        try {
            return channel.isActive
        } catch (unInit: UninitializedPropertyAccessException) {
            return false
        }
    }

    fun stopServer() {
        if (isServerRunning()) {
            channel.close().sync()
            val workerGroupFuture = workerGroup.shutdownGracefully()
            bossGroup.shutdownGracefully().sync()
            workerGroupFuture.sync()
        }
    }
}