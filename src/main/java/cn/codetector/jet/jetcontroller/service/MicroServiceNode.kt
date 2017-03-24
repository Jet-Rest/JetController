package cn.codetector.jet.jetcontroller.service

import io.vertx.ext.web.client.WebClient
import kotlin.coroutines.experimental.suspendCoroutine

/**
 * Created by codetector on 2017/3/24.
 */
class MicroServiceNode(val parentService: MicroService, var connection: MicroServiceConnection) {
    var lastVerification: Long = System.currentTimeMillis()
    var currentState: MicroServiceNodeStatus = MicroServiceNodeStatus.Normal

    enum class MicroServiceNodeStatus {
        Normal,
        SemiNormal,
        Error
    }

    suspend fun updateState(webClient: WebClient) = suspendCoroutine<Void> { cons ->
        webClient.get("${connection.httpUrl}/status").send { action ->
            if (action.succeeded()){

            } else {
                cons.resumeWithException(action.cause())
            }
        }
    }
}