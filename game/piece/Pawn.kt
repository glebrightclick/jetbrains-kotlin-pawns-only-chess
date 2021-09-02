package chess.game.piece

import chess.color.Color
import chess.game.BoardCell
import chess.game.Game
import kotlin.math.abs

class Pawn(override var color: Color) : Piece(color) {
    override fun getPossibleMoves(cellFrom: BoardCell, game: Game): MutableList<BoardCell> {
        val possibleMoves: MutableList<BoardCell> = mutableListOf()
        val colorDirectionStep = color.direction() ?: 0
        val nextVerticalY = cellFrom.y + colorDirectionStep
        val throughOneVerticalY = cellFrom.y + 2 * colorDirectionStep

        // pawn can move forward to the next cell
        val simpleMoveCell = BoardCell(cellFrom.x, nextVerticalY)
        if (nextVerticalY in game.board.heightRange() && !game.pieces().containsKey(simpleMoveCell)) {
            possibleMoves.add(simpleMoveCell)
        }

        // if pawn didn't move, we let it make a move forward to two cells
        val extendedMoveCell = BoardCell(cellFrom.x, throughOneVerticalY)
        if (moves.isEmpty()
            && throughOneVerticalY in game.board.heightRange()
            && !game.pieces().containsKey(simpleMoveCell)
            && !game.pieces().containsKey(extendedMoveCell)
        ) {
            possibleMoves.add(extendedMoveCell)
        }

        // captures
        if (nextVerticalY in game.board.heightRange()) {
            val leftCaptureCell = BoardCell(cellFrom.x - 1, nextVerticalY)
            if (cellFrom.x - 1 in game.board.widthRange()
                && getCapturedPieceCell(leftCaptureCell, game) !== null) {
                possibleMoves.add(leftCaptureCell)
            }

            val rightCaptureCell = BoardCell(cellFrom.x + 1, nextVerticalY)
            if (cellFrom.x + 1 in game.board.widthRange()
                && getCapturedPieceCell(rightCaptureCell, game) !== null) {
                possibleMoves.add(rightCaptureCell)
            }
        }

        return possibleMoves
    }

    override fun getCapturedPieceCell(cell: BoardCell, game: Game): BoardCell? {
        // normal capture situation
        val commonCapture = super.getCapturedPieceCell(cell, game)
        if (commonCapture !== null) {
            return commonCapture
        }

        // en passant situation
        val lastGameMove = if (game.moves().size > 0) game.moves().last() else return null
        val lastGameMovePiece = game.pieces()[lastGameMove.to]

        return if (lastGameMovePiece is Pawn
            && lastGameMovePiece.color !== color
            && lastGameMovePiece.moves().size == 1
            && cell.x == lastGameMove.to.x
            && abs(lastGameMove.from.y - lastGameMove.to.y) == 2
            && cell.y == (lastGameMove.from.y + lastGameMove.to.y) / 2) lastGameMove.to else null
    }
}