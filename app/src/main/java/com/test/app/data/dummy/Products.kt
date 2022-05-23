package com.test.app.data.dummy

import com.test.app.data.models.Product

object Products {
    private val images =
        arrayListOf(
            "ic_temp_breads",
            "ic_temp_drinks",
            "ic_temp_ice_cream",
            "ic_temp_pastas",
            "ic_temp_pizza_round",
            "ic_temp_salad",
            "ic_temp_steak",
        )

    private val names1 =
        arrayListOf(
            "bread",
            "drink",
            "ice cream",
            "pasta",
            "pizza round",
            "salad",
            "steak",
        )

    private val prefix =
        arrayListOf(
            "Time for ",
            "Special of ",
            "The super ",
            "Cheap ",
            "On discount ",
            "Small ",
            "Big ",
        )

    fun getRandomList(): ArrayList<Product> {
        val array = ArrayList<Product>()

        for (i in 0..15) {
            val rProduct = (0..6).random()
            val rPrefix = (0..6).random()

            array.add(Product("${prefix[rPrefix]}${names1[rProduct]}", images[rProduct]))
        }

        return array
    }
}