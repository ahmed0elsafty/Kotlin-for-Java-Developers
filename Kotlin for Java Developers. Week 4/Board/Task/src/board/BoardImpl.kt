package board

import board.Direction.*

fun createSquareBoard(width: Int): SquareBoard = BoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameImpl(width)

class GameImpl<T>(width: Int) : BoardImpl(width), GameBoard<T> {
    val map = mutableMapOf<Cell, T?>()
    val cells = getAllCells()

    init {
        cells.forEach {
            map[it] = null
        }
    }

    override fun get(cell: Cell): T? {
        return map[cell]
    }

    override fun set(cell: Cell, value: T?) {
        map[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return map.filterValues(predicate).keys
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        val filtered = map.filterValues(predicate).keys
        return filtered.firstOrNull()
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        val filtered = map.filterValues(predicate).keys
        return filtered.isNotEmpty()
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        val filtered = map.filterValues(predicate).keys
        return filtered.size == map.size
    }

}

open class BoardImpl(final override val width: Int) : SquareBoard {
    private val allCells = mutableMapOf<Pair<Int, Int>, Cell>()

    init {
        for (i in 1..width) {
            for (j in 1..width) {
                allCells.put(i to j, Cell(i, j))
            }
        }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return allCells[i to j]
    }

    override fun getCell(i: Int, j: Int): Cell {
        require(i in 1..width && j in 1..width)
        return allCells.getValue(i to j)
    }

    override fun getAllCells(): Collection<Cell> {
        return allCells.values
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val isAsc = jRange.first < jRange.last
        val results = allCells.asSequence()
            .filter { entry ->
                entry.key.first == i && entry.key.second in jRange
            }
            .map { it.value }
            .toList()
        return if (isAsc) results.sortedBy { it.j }
        else results.sortedByDescending { it.j }
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val isAsc = iRange.first < iRange.last
        val results = allCells.asSequence()
            .filter { entry ->
                entry.key.second == j && entry.key.first in iRange
            }
            .map { it.value }
            .toList()
        return if (isAsc) results.sortedBy { it.i }
        else results.sortedByDescending { it.i }
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when (direction) {
            UP -> allCells[i - 1 to j]
            DOWN -> allCells[i + 1 to j]
            RIGHT -> allCells[i to j + 1]
            LEFT -> allCells[i to j - 1]
        }
    }
}