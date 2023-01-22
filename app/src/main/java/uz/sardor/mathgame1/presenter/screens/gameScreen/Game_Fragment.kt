package uz.sardor.mathgame1.presenter.screens.gameScreen

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import uz.sardor.mathgame1.R
import uz.sardor.mathgame1.databinding.FragmentGameBinding
import uz.sardor.mathgame1.domain.entity.Level


class Game_Fragment : Fragment() {
    private val binding by lazy { FragmentGameBinding.inflate(layoutInflater) }
    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.option1)
            add(binding.option2)
            add(binding.option3)
            add(binding.option4)
            add(binding.option5)
            add(binding.option6)
        }
    }

    private val viewModel by viewModels<GameViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerViewModel()
        viewModel.startGame(Level.EASY)
        setClickListenerToOptions()

    }

    //functions

    private fun setClickListenerToOptions() {
        for (tvoption in tvOptions) {
            tvoption.setOnClickListener {
                viewModel.chooseAnswer(tvoption.text.toString().toInt())
            }
        }
    }

    private fun observerViewModel() {
        viewModel.question.observe(viewLifecycleOwner) {
            binding.tvSum.text = it.sum.toString()
            binding.visible.text = it.visibleNumber.toString()

            for (i in 0 until tvOptions.size) {
                tvOptions[i].text = it.options[i].toString()
            }
        }

        viewModel.persentOfRightAnswer.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it)
        }

        viewModel.enoughCountOfRightAnswer.observe(viewLifecycleOwner) {

        }

        viewModel.enoughPersentOfRightAnswer.observe(viewLifecycleOwner) {
            val color = getColorByState(it)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }

        viewModel.formatTime.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }


    }


    private fun getColorByState(goodState: Boolean): Int {
        val colorResId = if (goodState) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(), colorResId)
    }
}