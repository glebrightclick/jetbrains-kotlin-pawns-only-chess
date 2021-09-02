package chess.game

import chess.game.piece.Piece

class Game(val board : Board, private val first: Player, private val second: Player) {
    private var next : Player = first
    private val moves: MutableList<Move> = mutableListOf()
    private var lastMoveError : String = ""

    fun pieces() : Map<BoardCell, Piece> = first.pieces + second.pieces

    fun nextMovePlayer() = next
    private fun oppositePlayer() = if (next == first) second else first
    fun moves() = moves
    fun getLastMoveError() = lastMoveError

    fun makeMove(from: BoardCell, to: BoardCell) : Boolean {
        if (!(from.x in board.widthRange() && from.y in board.heightRange() && to.x in board.widthRange() && to.y in board.heightRange())) {
            lastMoveError = "Invalid Input"
            return false
        }

        if (!next.pieces.containsKey(from)) {
            lastMoveError = "No ${next.color.outputString().lowercase()} pawn at ${from.x}${from.y}"
            return false
        }

        val piece = next.pieces[from]!!
        val move = Move(from, to)

        if (!piece.isValidMove(move, this)) {
            lastMoveError = "Invalid Input"
            return false
        }

        val capturedPieceCell: BoardCell? = piece.addMoveAndReturnCapturedPieceCell(move, this)

        // recording the move
        moves.add(move)

        next.pieces[to] = piece
        next.pieces.remove(from)
        next = oppositePlayer()

        // if piece was captured
        if (capturedPieceCell !== null) {
            next.pieces.remove(capturedPieceCell)
        }

        return true
    }

    private fun isStalemate() : Boolean = next.pieces.isNotEmpty() &&
        next.pieces.filter { it.value.getPossibleMoves(it.key, this).isNotEmpty() }.isEmpty()

    fun isFinished() : Boolean =
        // no pieces of next move player - opposite player wins
        next.pieces.isEmpty() ||
        // piece of opposite player is on last row - opposite player wins
        moves().last().to.y + oppositePlayer().color.direction() !in board.heightRange() ||
        // no valid moves for current player - stalemate
        isStalemate()

    fun winner() : Player? = when {
        !isFinished() -> throw Exception("Game isn't finished")
        !isStalemate() -> oppositePlayer()
        else -> null
    }
}