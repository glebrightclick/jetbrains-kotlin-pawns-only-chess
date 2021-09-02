package chess

import chess.game.Game
import chess.game.piece.Piece

class Printer {
    fun print(game: Game) {
        val chessBoard = game.board
        var outputMap: MutableMap<Int, MutableMap<Char, Piece?>> = mutableMapOf()

        for (rowNumber in chessBoard.heightRange().reversed()) {
            for (column in chessBoard.widthRange()) {
                if (!outputMap.containsKey(rowNumber)) {
                    outputMap[rowNumber] = mutableMapOf()
                }

                outputMap[rowNumber]?.set(column, null)
            }
        }

        val pieces = game.pieces()
        for (index in pieces.keys) {
            outputMap[index.y]?.set(index.x, pieces[index])
        }

        printDelimiter(chessBoard.widthRange())

        for (column in outputMap.keys) {
            print("$column ")

            for (row in outputMap[column]?.keys!!) {
                print("| ${outputMap[column]?.get(row)?.output() ?: ' '} ")
            }
            print("|\n")

            printDelimiter(chessBoard.widthRange())
        }

        print("  ")
        for (row in chessBoard.widthRange()) {
            print("  $row ")
        }
        print("\n")
    }

    private fun printDelimiter(range: CharRange) {
        print("  +")
        for (column in range) print("---+")
        print("\n")
    }
}