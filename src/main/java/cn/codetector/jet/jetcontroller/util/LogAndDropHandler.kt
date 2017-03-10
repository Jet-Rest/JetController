package cn.codetector.jet.jetcontroller.util

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandler
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.util.ReferenceCountUtil
import org.slf4j.LoggerFactory

/**
 * Created by Codetector on 2017/3/10.
 * Project Jet
 */
class LogAndDropHandler : ChannelInboundHandlerAdapter() {
    private val logger = LoggerFactory.getLogger(LogAndDropHandler::class.java)

    init {
        logger.info("Log and drop initialized")
    }

    override fun channelActive(ctx: ChannelHandlerContext?) {
        val time = ctx!!.alloc().buffer(4) // (2)
        time.writeInt((System.currentTimeMillis() / 1000L + 2208988800L).toInt())
        ctx.writeAndFlush(time) // (3)
    }

    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        if (msg is ByteBuf) {
            val sb = StringBuilder()
            while (msg.isReadable) {
                sb.append(msg.readByte().toChar())
            }
            logger.info("Bytebuf => $sb")
            if (sb.toString().equals("bye\n", true)) {
                ctx!!.disconnect()
                logger.info("Disconnecting")
            }
        }
    }
}