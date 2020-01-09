package com.example.mindsettle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.RadioButton
import android.widget.RadioGroup
import com.github.islamkhsh.CardSliderIndicator
import com.github.islamkhsh.CardSliderViewPager
import kotlinx.android.synthetic.main.activity_coping_statement.*

class CopingStatementActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coping_statement)
        val statements = arrayListOf<CopingStatement>()
        for (i in resources.getStringArray(R.array.generalWorryStatement).indices) {
            statements.add(
                CopingStatement(
                    resources.getStringArray(R.array.generalWorryStatement)[i]
                )
            )
        }
        viewPager.adapter = CopingStatementAdapter(statements)

        viewPager.smallScaleFactor = 0.9f
        viewPager.smallAlphaFactor = 0.5f
        viewPager.autoSlideTime = CardSliderViewPager.STOP_AUTO_SLIDING
        indicator.indicatorsToShow = CardSliderIndicator.UNLIMITED_INDICATORS


        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener{ group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            when(radio){
                radioButtonGeneralWorry ->{
                    statements.clear()
                    for (i in resources.getStringArray(R.array.generalWorryStatement).indices) {
                        statements.add(
                            CopingStatement(
                                resources.getStringArray(R.array.generalWorryStatement)[i]
                            )
                        )
                    }
                    viewPager.adapter = CopingStatementAdapter(statements)
                }
                radioButtonSocialAnxiety ->{
                    statements.clear()

                    for (i in resources.getStringArray(R.array.socialAnxietyStatement).indices) {
                        statements.add(
                            CopingStatement(
                                resources.getStringArray(R.array.socialAnxietyStatement)[i]
                            )
                        )
                    }
                    viewPager.adapter = CopingStatementAdapter(statements)
                }
                radioButtonPanic ->{
                    statements.clear()

                    for (i in resources.getStringArray(R.array.panicStatement).indices) {
                        statements.add(
                            CopingStatement(
                                resources.getStringArray(R.array.panicStatement)[i]
                            )
                        )
                    }
                    viewPager.adapter = CopingStatementAdapter(statements)

                }
                radioButtonPhobia ->{
                    statements.clear()


                    for (i in resources.getStringArray(R.array.phobiaStatement).indices) {
                        statements.add(
                            CopingStatement(
                                resources.getStringArray(R.array.phobiaStatement)[i]
                            )
                        )
                    }
                    viewPager.adapter = CopingStatementAdapter(statements)

                }
            }

        })

    }
}
