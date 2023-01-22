package uz.sardor.mathgame1.domain.entity

data class GameResult(
    val winner: Boolean,
    val countOfRightAnswer: Int,
    val countOfQuestions: Int,
    val gameSetting: GameSetting

)
