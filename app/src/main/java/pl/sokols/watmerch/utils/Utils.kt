package pl.sokols.watmerch.utils

import pl.sokols.watmerch.data.model.Product
import kotlin.random.Random

class Utils {

    companion object {
        const val MERCH_ITEM_KEY: String = "merch_item_key"

        fun exampleArray(): Array<Product> =
            arrayOf(
                Product(0, "Alfa", getRandom(0, 10, 1000)),
                Product(1, "Bravo", getRandom(1, 10, 1000)),
                Product(2, "Charlie", getRandom(2, 10, 1000)),
                Product(3, "Delta", getRandom(3, 10, 1000)),
                Product(4, "Echo", getRandom(4, 10, 1000)),
                Product(5, "Foxtrot", getRandom(5, 10, 1000)),
                Product(6, "Golf", getRandom(6, 10, 1000)),
                Product(7, "Hotel", getRandom(7, 10, 1000)),
                Product(8, "India", getRandom(8, 10, 1000)),
                Product(9, "Juliett", getRandom(9, 10, 1000)),
            )

        private fun getRandom(seed: Int, from: Int, to: Int) = Random(seed).nextInt(from, to)
    }

}