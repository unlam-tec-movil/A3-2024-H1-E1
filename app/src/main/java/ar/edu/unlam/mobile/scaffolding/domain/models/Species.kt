package ar.edu.unlam.mobile.scaffolding.domain.models

enum class Species {
    LORO,
    GATO,
    PERRO,
    CONEJO,
    UNKNOWN,
    ;

    fun getEmoji(): String {
        return when (this) {
            LORO -> "🦜"
            GATO -> "🐱"
            PERRO -> "🐶"
            CONEJO -> "🐰"
            UNKNOWN -> "❓"
        }
    }

    companion object {
        fun fromString(value: String): Species {
            return try {
                valueOf(value.uppercase())
            } catch (e: IllegalArgumentException) {
                UNKNOWN
            }
        }
    }
}
