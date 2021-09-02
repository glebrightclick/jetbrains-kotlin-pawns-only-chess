package chess

import chess.color.Color

import chess.game.Game
import chess.game.Board
import chess.game.Player
import chess.game.BoardCell
import chess.game.piece.Pawn

fun main() {
    println("Pawns-Only Chess")

    println("First Player's name:")
    val whitePlayerName = readLine()!!

    println("Second Player's name:")
    val blackPlayerName = readLine()!!

    val printer = Printer()
    val game = Game(
        Board(),
        Player(
            whitePlayerName,
            Color.WHITE,
            mutableMapOf(
                Pair(BoardCell('a', 2), Pawn(Color.WHITE)),
                Pair(BoardCell('b', 2), Pawn(Color.WHITE)),
                Pair(BoardCell('c', 2), Pawn(Color.WHITE)),
                Pair(BoardCell('d', 2), Pawn(Color.WHITE)),
                Pair(BoardCell('e', 2), Pawn(Color.WHITE)),
                Pair(BoardCell('f', 2), Pawn(Color.WHITE)),
                Pair(BoardCell('g', 2), Pawn(Color.WHITE)),
                Pair(BoardCell('h', 2), Pawn(Color.WHITE)),
            )
        ),
        Player(
            blackPlayerName,
            Color.BLACK,
            mutableMapOf(
                Pair(BoardCell('a', 7), Pawn(Color.BLACK)),
                Pair(BoardCell('b', 7), Pawn(Color.BLACK)),
                Pair(BoardCell('c', 7), Pawn(Color.BLACK)),
                Pair(BoardCell('d', 7), Pawn(Color.BLACK)),
                Pair(BoardCell('e', 7), Pawn(Color.BLACK)),
                Pair(BoardCell('f', 7), Pawn(Color.BLACK)),
                Pair(BoardCell('g', 7), Pawn(Color.BLACK)),
                Pair(BoardCell('h', 7), Pawn(Color.BLACK)),
            )
        )
    )

    printer.print(game)

    while (true) {
        println("${game.nextMovePlayer().name}'s turn:")

        when (val move = readLine()!!) {
            "exit" -> {
                println("Bye!")
                break
            }
            else -> {
                if (!move.matches(Regex(game.board.getCorrectMoveRegex() + game.board.getCorrectMoveRegex()))) {
                    println("Invalid Input")
                    continue
                }

                val from = BoardCell(move[0], move[1].code - '0'.code)
                val to = BoardCell(move[2], move[3].code - '0'.code)

                if (!game.makeMove(from, to)) {
                    println(game.getLastMoveError())
                    continue
                }

                printer.print(game)

                if (game.isFinished()) {
                    val winner: Player? = game.winner()

                    println(if (winner !== null) "${winner.color.outputString()} Wins!" else "StaleMate!")
                    println("Bye!")

                    break
                }
            }
        }
    }
}