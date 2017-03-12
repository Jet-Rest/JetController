package cn.codetector.jet.jetcontroller.servicemanager.communication.handler

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import io.netty.handler.codec.MessageToMessageDecoder

/**
 * Created by Codetector on 2017/3/11.
 * Project Jet
 */
class PacketTypeClassifier : ByteToMessageDecoder(){
    override fun decode(ctx: ChannelHandlerContext?, `in`: ByteBuf?, out: MutableList<Any>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}