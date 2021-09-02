package chess.game.piece

import chess.color.Color
import chess.game.BoardCell
import chess.game.Game
import chess.game.Move

abstract class Piece(open var color: Color) {
    protected val moves: MutableList<Move> = mutableListOf()
    fun output() = color.outputString()[0].uppercase()

    abstract fun getPossibleMoves(cellFrom: BoardCell, game: Game) : MutableList<BoardCell>
    fun isValidMove(move: Move, game: Game): Boolean =
        getPossibleMoves(move.from, game).contains(move.to)

    fun addMoveAndReturnCapturedPieceCell(move: Move, game: Game) : BoardCell? =
        if (moves.add(move)) getCapturedPieceCell(move.to, game) else null

    fun moves() = moves

    // for the most pieces, capture is common
    protected open fun getCapturedPieceCell(cell: BoardCell, game: Game) : BoardCell? =
        if (game.pieces().containsKey(cell) && game.pieces()[cell]!!.color !== color) cell else null
}