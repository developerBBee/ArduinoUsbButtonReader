package jp.developerbbee.arduino.data

enum class InputKey(private val key: String) {
    NONE(""),
    COPY_KEY("CopyTactSwitchOn"),
    ;

    companion object {
        fun from(key: String): InputKey {
            return entries.firstOrNull { it.key == key } ?: NONE
        }
    }
}