package com.example.grid.data

import com.example.grid.model.Topic
import com.example.grid.R

object DataSource {
    val topics = listOf<Topic>(
        Topic(R.string.architecture, 58, R.drawable.architecture),
        Topic(R.string.automotive, 30, R.drawable.automotive),
        Topic(R.string.biology, 90, R.drawable.biology),
        Topic(R.string.crafts, 121, R.drawable.crafts),
        Topic(R.string.business, 78, R.drawable.business),
        Topic(R.string.culinary, 118, R.drawable.culinary),
        Topic(R.string.ecology, 28, R.drawable.ecology),
        Topic(R.string.design, 423, R.drawable.design),
        Topic(R.string.fashion, 92, R.drawable.fashion),
        Topic(R.string.film, 165, R.drawable.film),
        Topic(R.string.gaming, 164, R.drawable.gaming),
        Topic(R.string.drawing, 326, R.drawable.drawing),
        Topic(R.string.lifestyle, 305, R.drawable.lifestyle),
        Topic(R.string.music, 212, R.drawable.music),
        Topic(R.string.painting, 172, R.drawable.painting),
        Topic(R.string.photography, 321, R.drawable.photography),
        Topic(R.string.tech, 118, R.drawable.tech)
    )
}