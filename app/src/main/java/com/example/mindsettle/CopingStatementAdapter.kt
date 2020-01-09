package com.example.mindsettle

import android.view.View
import com.github.islamkhsh.CardSliderAdapter
import kotlinx.android.synthetic.main.item_card_content.view.*

class CopingStatementAdapter(items : ArrayList<CopingStatement>) : CardSliderAdapter<CopingStatement>(items) {
    override fun bindView(position: Int, itemContentView: View, item: CopingStatement?) {

        item?.run {
            itemContentView.statement.text = statement
        }
    }

    override fun getItemContentLayout(position: Int) = R.layout.item_card_content
}