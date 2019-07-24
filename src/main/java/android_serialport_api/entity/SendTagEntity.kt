package android_serialport_api.entity

/**
 * Create by Panda on 2019-06-24
 */
class SendTagEntity : BaseEntity() {

    var heartbeat: Byte = 0x00
    var rfaddr = arrayListOf<Byte>()
    var ch = arrayListOf<Byte>()
    var rate = arrayListOf<Byte>()
    var enable: Byte = 0x00

    override fun write(): ArrayList<Byte> {
        payload.add(0x55)
        payload.add(heartbeat)
        payload.addAll(rfaddr)
        payload.addAll(ch)
        payload.addAll(rate)
        payload.add(enable)
        return super.write()
    }
}