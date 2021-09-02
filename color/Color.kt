package chess.color

enum class Color(private val output: String, private val direction: Byte) {
    WHITE("White", 1),
    BLACK("Black", -1);

    fun outputString() = output
    fun direction() = direction
}