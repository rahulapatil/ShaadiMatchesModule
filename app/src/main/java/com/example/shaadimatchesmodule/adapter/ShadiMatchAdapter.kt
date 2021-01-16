package com.example.shaadimatchesmodule.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.shaadimatchesmodule.R
import com.example.shaadimatchesmodule.database.ShadiMatchEntity
import com.example.shaadimatchesmodule.interactions.ItemInteractor

class ShadiMatchAdapter(
    private val itemList: ArrayList<ShadiMatchEntity>,
    private val interactorIntrface: ItemInteractor?, context:Context) : RecyclerView.Adapter<ShadiMatchAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_shaadi_match_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.txt_about_user.text = itemList[position].email
            holder.txt_user_name.text =  itemList[position].name
            holder.txt_user_address.text =itemList[position].country +","+itemList[position].state+" "+itemList[position].city
            if(itemList[position].status=="1")
            {
                holder.status.visibility= View.GONE
                holder.txt_status.visibility= View.VISIBLE
                holder.space.visibility= View.VISIBLE
                holder.txt_status.text="You have accepted request from "+itemList[position].name
                holder.txt_status.setTextColor(Color.parseColor("#41bba0"))
            }
            else if(itemList[position].status=="0")
            {
                holder.status.visibility= View.GONE
                holder.txt_status.visibility= View.VISIBLE
                holder.space.visibility= View.VISIBLE
                holder.txt_status.text="You have declined request from "+itemList[position].name
                holder.txt_status.setTextColor(Color.parseColor("#ff5a60"))
            }
            else
            {
                holder.status.visibility= View.VISIBLE
                holder.txt_status.visibility= View.GONE
                holder.space.visibility= View.GONE
            }
            val context = holder.itemView.context
            Glide
                .with(context)
                .load(itemList[position].imageUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher_round)
                .into(holder.profile_image)

            holder.img_decline.setOnClickListener {
                interactorIntrface?.onDeclineImageClick(itemList[position],position)
            }

            holder.img_accept.setOnClickListener {
                interactorIntrface?.onAcceptImageClick(itemList[position],position)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val txt_about_user: TextView = mView.findViewById<View>(R.id.txt_about_user) as TextView
        val txt_user_name: TextView = mView.findViewById<View>(R.id.txt_user_name) as TextView
        val txt_user_address: TextView = mView.findViewById<View>(R.id.txt_user_address) as TextView
        val img_decline: ImageView = mView.findViewById<View>(R.id.img_decline) as ImageView
        val img_accept: ImageView = mView.findViewById<View>(R.id.img_accept) as ImageView
        val profile_image: ImageView = mView.findViewById<View>(R.id.profile_image) as ImageView
        val status: LinearLayout = mView.findViewById<View>(R.id.status) as LinearLayout
        val txt_status: TextView = mView.findViewById<View>(R.id.txt_status) as TextView
        val space: Space = mView.findViewById<View>(R.id.space) as Space
        override fun toString(): String {
            return super.toString() + " '" + txt_about_user.text + "'" + " '" + txt_user_name.text + "'" + txt_user_address.text + ";"
        }

    }
}