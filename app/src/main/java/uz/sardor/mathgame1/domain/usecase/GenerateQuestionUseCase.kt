package uz.sardor.mathgame1.domain.usecase

import uz.sardor.mathgame1.domain.entity.Questions
import uz.sardor.mathgame1.domain.repository.GameRepository

class GenerateQuestionUseCase(private val repository: GameRepository) {

    operator fun invoke(maxSumValue:Int):Questions{
        return  repository.genereteQuestions(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object{
        private const val COUNT_OF_OPTIONS = 6
    }

}