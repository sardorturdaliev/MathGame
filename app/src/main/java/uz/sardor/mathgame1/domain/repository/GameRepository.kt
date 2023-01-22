package uz.sardor.mathgame1.domain.repository

import uz.sardor.mathgame1.domain.entity.GameSetting
import uz.sardor.mathgame1.domain.entity.Level
import uz.sardor.mathgame1.domain.entity.Questions

interface GameRepository {

    fun genereteQuestions(maxValue: Int, countOfOptions: Int): Questions

    fun getGameSetting(level: Level): GameSetting

}