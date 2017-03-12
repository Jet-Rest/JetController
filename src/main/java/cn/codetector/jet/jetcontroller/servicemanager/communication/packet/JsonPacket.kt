package cn.codetector.jet.jetcontroller.servicemanager.communication.packet

import cn.codetector.jet.jetsimplejson.JSONObject

/**
 * Created by Codetector on 2017/3/12.
 * Project Jet
 */
class JsonPacket{
    val jsonObject: JSONObject
    constructor() {
        jsonObject = JSONObject()
    }
    constructor(string: String) {
        jsonObject = JSONObject(string)
    }
    constructor(pureTextPacket: PureTextPacket) : this(pureTextPacket.stringContent)
}