package cn.codetector.jet.jetcontroller.service

/**
 * Created by Codetector on 2017/3/21.
 * Project Jet
 */
class MicroService(val id: String, val name: String, var status: ServiceStatus) {

    constructor(id: String, name: String, status: Int): this(id, name, ServiceStatus.valueOf(status))
    constructor(id: String, name: String): this(id, name, ServiceStatus.NORMAL)

    private val availiableNode: List<String> = arrayListOf()

    fun numOfAvailNode(): Int = availiableNode.size


    enum class ServiceStatus(val status: Int) {
        NORMAL(0),
        DISABLED(-1);
        companion object {
            fun valueOf(status: Int): ServiceStatus{
                ServiceStatus.values().forEach {
                    if (it.status == status) {
                        return it
                    }
                }
                return NORMAL
            }
        }
    }
}