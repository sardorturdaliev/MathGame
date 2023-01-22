package uz.sardor.mathgame1.data

import android.media.Ringtone
import uz.sardor.mathgame1.domain.entity.GameSetting
import uz.sardor.mathgame1.domain.entity.Level
import uz.sardor.mathgame1.domain.entity.Questions
import uz.sardor.mathgame1.domain.repository.GameRepository
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.random.Random

object GameRepositoryImp : GameRepository {
    private val MIN_SUM_VALUE = 2
    private val MIN_ANSWER_VALUE = 1


    override fun genereteQuestions(maxValue: Int, countOfOptions: Int): Questions {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxValue) // 1 .. 10 -> 7
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)//  -> 4
        val options = HashSet<Int>()
        val rightAnswer = sum - visibleNumber// 3 , 10 , 4 , 9,5 ,2
        options.add(rightAnswer)// 3
        val from = max(rightAnswer - countOfOptions, MIN_ANSWER_VALUE) // 1
        val to = min(maxValue, rightAnswer + countOfOptions)//10
        while (options.size < countOfOptions) {// 1 .. 6
            options.add(Random.nextInt(from, to))
        }
        return Questions(sum, visibleNumber, options.toList())
    }

    override fun getGameSetting(level: Level): GameSetting {
        return when (level) {
            Level.TEST -> {
                GameSetting(10, 3, 50, 8)
            }
            Level.EASY -> {
                GameSetting(
                    50, 10, 70, 60
                )
            }
            Level.NORMAL -> {
                GameSetting(
                    20, 20, 80, 40
                )
            }
            Level.HARD -> {
                GameSetting(
                    30, 30, 90, 40
                )
            }
        }

    }
}