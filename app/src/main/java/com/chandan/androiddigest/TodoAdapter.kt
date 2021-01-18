package com.chandan.androiddigest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private  var list: ArrayList<Todo>
    init{
        list = ArrayList()
        list.add(Todo("Hello",true))
        list.add(Todo("Hello1",false))
        list.add(Todo("Hello2",true))
        list.add(Todo("Hello3",false))
        list.add(Todo("Hello4",true))
        list.add(Todo("Hello5",false))
        list.add(Todo("Hello6",true))
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TodoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.todo_list_item,parent,false))
    }

    override fun getItemCount(): Int {
       return  list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TodoViewHolder).onBind(list[position])
    }

     fun addTodoItem(desc: String, check: Boolean){
        list.add(Todo(desc,check))
        notifyItemInserted(list.size -1)
    }



    inner class TodoViewHolder(view: View):RecyclerView.ViewHolder(view) {
        private val desc: TextView = view.findViewById(R.id.textView11)
        private val check: CheckBox = view.findViewById(R.id.checkBox)

      fun onBind(todo: Todo){
          desc.setText(todo.desc)
          check.isChecked = todo.isChecked
      }
    }
}