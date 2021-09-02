package chess.game

class Board {
    private val width = 8
    private val height = 8

    private fun widthFirstElement() : Char = 97.toChar()
    private fun widthLastElement() : Char = (97 + width).toChar()
    fun widthRange() : CharRange = widthFirstElement() until widthLastElement()

    private fun heightFirstElement() : Int = 1
    private fun heightLastElement() : Int = height
    fun heightRange() : IntRange = heightFirstElement()..heightLastElement()

    fun getCorrectMoveRegex() : String = "[${widthFirstElement()}-${widthLastElement()}][${heightFirstElement()}-${heightLastElement()}]"
}