package com.forgoogleplay.bodyfatcalculatorpro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CalculatorAdapter : RecyclerView.Adapter<CalculatorAdapter.CalculatorViewHolder>() {
    private var stdList: ArrayList<CalculatorModel> = ArrayList()
    private var onClickItem: ((CalculatorModel) -> Unit)? = null
    private var onClickDeleteItem: ((CalculatorModel) -> Unit)? = null

    fun addItems(items: ArrayList<CalculatorModel>){
        this.stdList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (CalculatorModel) -> Unit){
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (CalculatorModel) -> Unit){
        this.onClickDeleteItem = callback
    }

    class CalculatorViewHolder(var view: View) : RecyclerView.ViewHolder(view){
        private var id = view.findViewById<TextView>(R.id.tvId)
        private var gender = view.findViewById<TextView>(R.id.gender)
        private var age = view.findViewById<TextView>(R.id.age)
        private var weight = view.findViewById<TextView>(R.id.weight)
        private var height = view.findViewById<TextView>(R.id.height)
        private var neck = view.findViewById<TextView>(R.id.neck)
        private var waist = view.findViewById<TextView>(R.id.waist)
        private var hip = view.findViewById<TextView>(R.id.hip)
        private var result = view.findViewById<TextView>(R.id.result)
        var btnDelete = view.findViewById<Button>(R.id.btnDelete)

        fun bindView(std:CalculatorModel){
            id.text = std.id.toString()
            gender.text = std.gender
            age.text = std.age.toString()
            weight.text = std.weight.toString()
            height.text = std.height.toString()
            neck.text = std.neck.toString()
            waist.text = std.waist.toString()
            hip.text = std.hip.toString()
            result.text = std.result.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CalculatorViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_std,parent,false)
    )

    override fun onBindViewHolder(holder: CalculatorViewHolder, position: Int) {
        val std = stdList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener{onClickItem?.invoke(std)}
        holder.btnDelete.setOnClickListener{onClickDeleteItem?.invoke(std)}
    }

    override fun getItemCount(): Int {
        return stdList.size
    }
}