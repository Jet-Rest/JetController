package cn.codetector.jet.jetcontroller.servicemanager.communication.encoder

import cn.codetector.jet.jetcontroller.servicemanager.communication.packet.JsonPacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import io.netty.handler.codec.MessageToMessageEncoder

/**
 * Created by Codetector on 2017/3/12.
 * Project Jet
 */
class JsonPacketEnc : MessageToMessageEncoder<JsonPacket>() {
    override fun encode(ctx: ChannelHandlerContext, msg: JsonPacket, out: MutableList<Any>) {
        out.add(msg.jsonObject.encode())
    }
}