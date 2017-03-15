package cn.codetector.jet.jetcontroller.servicemanager.communication.handler

import cn.codetector.jet.std.network.packet.JsonPacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import io.netty.handler.codec.MessageToMessageDecoder

/**
 * Created by Codetector on 2017/3/11.
 * Project Jet
 */
class PacketTypeClassifier : MessageToMessageDecoder<JsonPacket>(){
    override fun decode(ctx: ChannelHandlerContext?, msg: JsonPacket?, out: MutableList<Any>?) {
        println(msg)
    }

}