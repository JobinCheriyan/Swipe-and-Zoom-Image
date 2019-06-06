package experion.com.imagezoom

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.viewpager.widget.PagerAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.example.extendedviewpager.ExtendedViewPager
import com.example.extendedviewpager.TouchImageZoom
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var mImageViewZoom: ImageView
    var mScale = 1f
    lateinit var mScaleGestureDetector: ScaleGestureDetector

    val imageDataClass = ArrayList<ImageDataClass>()
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mImageViewZoom = findViewById(R.id.image_view_zoom)
//        mScaleGestureDetector = ScaleGestureDetector(this, ScaleListener())
        recycler_view_image.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                this,
                LinearLayout.VERTICAL,
                false
            ) as RecyclerView.LayoutManager?


        imageDataClass.add(ImageDataClass(R.drawable.kalabhava))
        imageDataClass.add(ImageDataClass(R.drawable.hqdefault))
        imageDataClass.add(ImageDataClass(R.drawable.mani))
        val images = intArrayOf(
            R.drawable.kalabhava,
            R.drawable.hqdefault,
            R.drawable.mani
        )

        val mAdapterRecyclerView = ImageRecyclerViewAdapter(imageDataClass, this)
        recycler_view_image.adapter = mAdapterRecyclerView
        recycler_view_image.addOnItemTouchListener(
            RecyclerTouchListener(
                applicationContext,
                recycler_view_image!!,
                object : ClickListener {

                    override fun onClick(view: View, position: Int) {
                        Toast.makeText(this@MainActivity, "My postion" + position, Toast.LENGTH_SHORT).show()

                        val mProgressDialog = Dialog(this@MainActivity)
                        mProgressDialog.setContentView(R.layout.dialog_display_image)
                        mProgressDialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        var mViewPager: ExtendedViewPager = mProgressDialog.findViewById(R.id.view_pager_image)
                        mViewPager.setAdapter(TouchImageAdapter(images))
                        mViewPager?.setCurrentItem(position)
                        mProgressDialog.show()
                        mProgressDialog.setCancelable(true)
                    }

                    override fun onLongClick(view: View?, position: Int) {

                    }
                })
        )


    }


    interface ClickListener {
        fun onClick(view: View, position: Int)

        fun onLongClick(view: View?, position: Int)
    }

    internal class RecyclerTouchListener(context: Context, recyclerView: RecyclerView, private val clickListener: ClickListener?) : RecyclerView.OnItemTouchListener {

        private val gestureDetector: GestureDetector

        init {
            gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    return true
                }

                override fun onLongPress(e: MotionEvent) {
                    val child = recyclerView.findChildViewUnder(e.x, e.y)
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child))
                    }
                }
            })
        }

        override fun onInterceptTouchEvent(rv: androidx.recyclerview.widget.RecyclerView, e: MotionEvent): Boolean {

            val child = rv.findChildViewUnder(e.x, e.y)
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child))
            }
            return false
        }

        override fun onTouchEvent(rv: androidx.recyclerview.widget.RecyclerView, e: MotionEvent) {}

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

        }
    }


    internal class TouchImageAdapter(images: IntArray) : PagerAdapter() {
        val images = images


        override fun getCount(): Int {
            return images.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): View {
            val img = TouchImageZoom(container.context)

            img.setImageResource(images[position])
            container.addView(img, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

            return img
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

    }


}

