package uz.sardor.mathgame1.domain.entity

data class Questions(
    val sum: Int,
    val visibleNumber: Int,
    val options: List<Int>
) {
    val rightAnswer: Int get() = sum - visibleNumber
}
