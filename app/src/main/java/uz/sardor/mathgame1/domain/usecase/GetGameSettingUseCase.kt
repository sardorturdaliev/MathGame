package uz.sardor.mathgame1.domain.usecase

import uz.sardor.mathgame1.domain.entity.GameSetting
import uz.sardor.mathgame1.domain.entity.Level
import uz.sardor.mathgame1.domain.repository.GameRepository

class GetGameSettingUseCase(private val repository: GameRepository) {

    operator fun invoke(level: Level): GameSetting {
        return repository.getGameSetting(level)
    }

}