package chess.game

import chess.color.Color
import chess.game.piece.Piece

data class Player(val name : String, val color: Color, val pieces: MutableMap<BoardCell, Piece>) {
    init {
        if (pieces.isNotEmpty()) {
            for (index in pieces.keys) {
                if (pieces[index]?.color !== color) {
                    throw Exception("Wrong color of input piece ${pieces[index]}")
                }
            }
        }
    }
}