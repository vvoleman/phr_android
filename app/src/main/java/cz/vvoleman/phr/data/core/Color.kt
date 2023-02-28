package cz.vvoleman.phr.data.core

import cz.vvoleman.phr.R

enum class Color(val color: Int) {
    ORANGE(R.color.category_orange),
    RED(R.color.category_red),
    GREEN(R.color.category_green),
    BLUE(R.color.category_blue),
    PURPLE(R.color.category_purple),
    YELLOW(R.color.category_yellow),
    PINK(R.color.category_pink),
    BROWN(R.color.category_brown),
    GRAY(R.color.category_gray),
    ;

    companion object {
        fun fromString(string: String): Color {
            return enumValueOf(string)
        }
    }
}
