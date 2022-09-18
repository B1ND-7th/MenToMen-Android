package kr.hs.b1nd.intern.mentomen.util

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import kr.hs.b1nd.intern.mentomen.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("image")
    fun loadImage(view: ImageView, imageUrl: String?) {
        imageUrl?.let {
            Glide.with(view.context)
                .load(it)
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("date")
    fun translateDate(view: TextView, dateTime: String) {
        val now = LocalDateTime.now()
        val time = dateTime.split(".")[0]
        val convertTime = LocalDateTime.parse(time, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val compareSecondTime = ChronoUnit.SECONDS.between(convertTime, now)
        val compareMinuteTime = ChronoUnit.MINUTES.between(convertTime, now)
        val compareHourTime = ChronoUnit.HOURS.between(convertTime, now)
        val compareDayTime = ChronoUnit.DAYS.between(convertTime, now)
        val compareMonthTime = ChronoUnit.MONTHS.between(convertTime, now)
        when {
            compareSecondTime < 60 -> view.text= "${compareSecondTime}초전"
            compareMinuteTime < 60 -> view.text = "${compareMinuteTime}분전"
            compareHourTime < 24 -> view.text = "${compareHourTime}시간전"
            compareDayTime < 31 -> view.text = "${compareDayTime}일전"
            else -> view.text = "${compareMonthTime}달전"
        }
    }

    @JvmStatic
    @BindingAdapter("designButtonState")
    fun setDesignButtonState(view: Button, tagState: MutableLiveData<TagState>) {
        val parentActivity: AppCompatActivity = view.getParentActivity() ?: return

        tagState.observe(parentActivity) { tagState ->
            if (tagState.isDesignChecked) {
                view.setBackgroundResource(R.drawable.corner_radious)
                view.setTextColor(ContextCompat.getColor(parentActivity, R.color.white))
            }
            else {
                view.setBackgroundResource(R.drawable.unselected_design)
                view.setTextColor(ContextCompat.getColor(parentActivity, R.color.design))
            }
        }
    }

    @JvmStatic
    @BindingAdapter("webButtonState")
    fun setWebButtonState(view: Button, tagState: MutableLiveData<TagState>) {
        val parentActivity: AppCompatActivity = view.getParentActivity() ?: return

        tagState.observe(parentActivity) { tagState ->
            if (tagState.isWebChecked) {
                view.setBackgroundResource(R.drawable.corner_radious)
                view.setTextColor(ContextCompat.getColor(parentActivity, R.color.white))
            }
            else {
                view.setBackgroundResource(R.drawable.unselected_web)
                view.setTextColor(ContextCompat.getColor(parentActivity, R.color.web))
            }
        }
    }

    @JvmStatic
    @BindingAdapter("serverButtonState")
    fun setServerButtonState(view: Button, tagState: MutableLiveData<TagState>) {
        val parentActivity: AppCompatActivity = view.getParentActivity() ?: return

        tagState.observe(parentActivity) { tagState ->
            if (tagState.isServerChecked) {
                view.setBackgroundResource(R.drawable.corner_radious)
                view.setTextColor(ContextCompat.getColor(parentActivity, R.color.white))
            }
            else {
                view.setBackgroundResource(R.drawable.unselected_server)
                view.setTextColor(ContextCompat.getColor(parentActivity, R.color.server))
            }
        }
    }

    @JvmStatic
    @BindingAdapter("androidButtonState")
    fun setAndroidButtonState(view: Button, tagState: MutableLiveData<TagState>) {
        val parentActivity: AppCompatActivity = view.getParentActivity() ?: return

        tagState.observe(parentActivity) { tagState ->
            if (tagState.isAndroidChecked) {
                view.setBackgroundResource(R.drawable.corner_radious)
                view.setTextColor(ContextCompat.getColor(parentActivity, R.color.white))
            }
            else {
                view.setBackgroundResource(R.drawable.unselected_android)
                view.setTextColor(ContextCompat.getColor(parentActivity, R.color.android))
            }
        }
    }

    @JvmStatic
    @BindingAdapter("iosButtonState")
    fun setIosButtonState(view: Button, tagState: MutableLiveData<TagState>) {
        val parentActivity: AppCompatActivity = view.getParentActivity() ?: return

        tagState.observe(parentActivity) { tagState ->
            if (tagState.isiOSChecked) {
                view.setBackgroundResource(R.drawable.corner_radious)
                view.setTextColor(ContextCompat.getColor(parentActivity, R.color.white))
            }
            else {
                view.setBackgroundResource(R.drawable.unselected_ios)
                view.setTextColor(ContextCompat.getColor(parentActivity, R.color.iOS))
            }
        }
    }
}