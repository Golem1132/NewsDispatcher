package com.example.newsdispatcher.utils

import com.example.newsdispatcher.R

sealed class NewsCategories(val id: String, val title: Int) {
//    data object All : NewsCategories(null, R.string.category_all)
    data object Business : NewsCategories("business", R.string.category_business)
    data object Entertainment : NewsCategories("entertainment", R.string.category_entertainment)
    data object General : NewsCategories("general", R.string.category_general)
    data object Health : NewsCategories("health", R.string.category_health)
    data object Science : NewsCategories("science", R.string.category_science)
    data object Sports : NewsCategories("sports", R.string.category_sports)
    data object Technology : NewsCategories("technology", R.string.category_technology)

    companion object {
        fun getAll(): List<NewsCategories> =
            listOf(
                General, Business, Entertainment, Health, Science, Sports, Technology
            )
    }
}