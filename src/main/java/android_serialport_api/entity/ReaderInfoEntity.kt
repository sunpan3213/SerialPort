package android_serialport_api.entity

/**
 * Create by Panda on 2019-06-24
 */
class ReaderInfoEntity :BaseEntity(){

    override fun write(): ArrayList<Byte> {
        payload.add(0x50)
        return super.write()
    }
}