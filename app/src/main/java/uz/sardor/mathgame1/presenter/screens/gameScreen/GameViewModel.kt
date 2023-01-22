package uz.sardor.mathgame1.presenter.screens.gameScreen

import android.app.Application
import android.os.CountDownTimer
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uz.sardor.mathgame1.R
import uz.sardor.mathgame1.data.GameRepositoryImp
import uz.sardor.mathgame1.domain.entity.GameResult
import uz.sardor.mathgame1.domain.entity.GameSetting
import uz.sardor.mathgame1.domain.entity.Level
import uz.sardor.mathgame1.domain.entity.Questions
import uz.sardor.mathgame1.domain.usecase.GenerateQuestionUseCase
import uz.sardor.mathgame1.domain.usecase.GetGameSettingUseCase

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application
    private lateinit var level: Level
    private lateinit var gameSetting: GameSetting
    private val _minPersent = MutableLiveData<Int>()
    val minPersent: LiveData<Int> get() = _minPersent
    private val _question = MutableLiveData<Questions>()
    val question: LiveData<Questions> get() = _question
    private var timer: CountDownTimer? = null

    private var counterOfRightAnswer = 0
    private var countOfQuestions = 0

    private val _persentOfRightAnswer = MutableLiveData<Int>()
    val persentOfRightAnswer: LiveData<Int> get() = _persentOfRightAnswer

    private val _formatTime = MutableLiveData<String>()
    val formatTime: LiveData<String> get() = _formatTime

    private val _progressAnswer = MutableLiveData<String>()
    val progressAnswer: LiveData<String> get() = _progressAnswer

    private val _enoughCountOfRightAnswer = MutableLiveData<Boolean>()
    val enoughCountOfRightAnswer: LiveData<Boolean> get() = _enoughCountOfRightAnswer

    private val _enoughPersentOfRightAnswer = MutableLiveData<Boolean>()
    val enoughPersentOfRightAnswer: LiveData<Boolean> get() = _enoughPersentOfRightAnswer

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult> get() = _gameResult

    //useCase
    private val repository = GameRepositoryImp
    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingUseCase = GetGameSettingUseCase(repository)


    fun startGame(level: Level) {
        getGameSetting(level)
        startTimer()
        generateQuestion()
        updateProgress()
    }


    fun chooseAnswer(number:Int){
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer) {
            counterOfRightAnswer++
        }
        countOfQuestions++
    }

    private fun getGameSetting(level: Level) {
        this.level = level
        this.gameSetting = getGameSettingUseCase(level)
        _minPersent.value = gameSetting.minPersentOfRightAnswer

    }
    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSetting.maxSumValue)
    }
    private fun updateProgress() {
        val persent = calculatePercentRightAnswer()
        _persentOfRightAnswer.value = persent
        _progressAnswer.value = String.format(
            context.resources.getString(R.string.progres_answer),
            counterOfRightAnswer,
            gameSetting.mincountRightAnswer
        )
        _enoughCountOfRightAnswer.value = counterOfRightAnswer >= gameSetting.mincountRightAnswer
        _enoughPersentOfRightAnswer.value = persent >= gameSetting.minPersentOfRightAnswer
    }
    private fun calculatePercentRightAnswer(): Int {
        if (countOfQuestions == 0) {
            return 0
        }
        return ((counterOfRightAnswer / countOfQuestions.toDouble()) * 100).toInt()
    }
    private fun startTimer() {
        timer = object :
            CountDownTimer(gameSetting.gameTimeInSecond * MILLIS_IN_SECOND, MILLIS_IN_SECOND) {
            override fun onTick(millis: Long) {
                _formatTime.value = formatTimer(millis)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }
    private fun finishGame() {
        _gameResult.value = GameResult(
            enoughCountOfRightAnswer.value == true && enoughCountOfRightAnswer.value == true,
            counterOfRightAnswer,
            countOfQuestions,
            gameSetting
        )
        Toast.makeText(context,"Game is Over",Toast.LENGTH_SHORT).show()
    }
    private fun formatTimer(millis: Long): String {
        val second = millis / MILLIS_IN_SECOND //   8000 / 1000 = 8
        val minute = second / SECOND_IN_MINUTE //  8 / 60  0.13
        val leftSecond = second - (minute * SECOND_IN_MINUTE) // 8 - (0.13 * 60)
        return String.format("%2d:%2d", minute, leftSecond)
    }
    companion object {
        private const val MILLIS_IN_SECOND = 1000L
        private const val SECOND_IN_MINUTE = 60
    }
    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}