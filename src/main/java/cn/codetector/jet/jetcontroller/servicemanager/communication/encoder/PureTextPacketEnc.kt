package cn.codetector.jet.jetcontroller.servicemanager.communication.encoder

import cn.codetector.jet.jetcontroller.servicemanager.communication.packet.PureTextPacket
import cn.codetector.jet.jetcontroller.servicemanager.communication.packet.RawPacket
import com.google.common.io.ByteArrayDataOutput
import com.google.common.io.ByteStreams
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import io.netty.handler.codec.MessageToMessageEncoder

/**
 * Created by Codetector on 2017/3/12.
 * Project Jet
 */
class PureTextPacketEnc: MessageToMessageEncoder<PureTextPacket>() {
    override fun encode(ctx: ChannelHandlerContext?, msg: PureTextPacket, out: MutableList<Any>) {
        val pOut: ByteArrayDataOutput = ByteStreams.newDataOutput()
        pOut.writeChars(msg.stringContent)
        out.add(RawPacket(header = emptyArray(),content = pOut.toByteArray().toTypedArray()))
    }
}