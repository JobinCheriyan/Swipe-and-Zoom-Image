package experion.com.imagezoom

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView


class ImageRecyclerViewAdapter(val imagelist: ArrayList<ImageDataClass>, val context: Context) :
    androidx.recyclerview.widget.RecyclerView.Adapter<ImageRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.list_image, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return imagelist.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val imageDataClass: ImageDataClass = imagelist[p1]
        p0.mImageViewImage.setImageResource(imageDataClass.imageId)

    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        var mImageViewImage = itemView.findViewById(R.id.image_view_image) as ImageView


    }
}





