package com.goals.ted.goals.Fragments.CreateGoal

import android.content.Context
import android.support.v4.app.ActivityCompat.startActivity
import com.goals.ted.goals.Dates
import org.jetbrains.anko.toast
import java.util.*

/**
 * Created by ted on 6/15/17.
 */
class CreateGoalPresenter(val context: Context, val view: CreateGoalMVP.View) : CreateGoalMVP.Presenter {
    val interactor = CreateGoalInteractor(context)

    private val HOUR_PM = "PM"
    private val HOUR_AM = "AM"

    override fun launchTimePicker() {
        view.launchTimePicker()
    }

    override fun launchDatePicker() {
        view.launchDatePicker()
    }

    override fun setDate(year: Int, monthOfYear: Int, dayOfMonth: Int): Calendar {
        val dueDate: Calendar = Calendar.getInstance()
        dueDate.set(Calendar.YEAR, year)
        dueDate.set(Calendar.MONTH, monthOfYear)
        dueDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        return dueDate
    }

    override fun setTime(hourOfDay: Int, minute: Int): Calendar {
        val dueDate = Calendar.getInstance()
        dueDate.set(Calendar.HOUR_OF_DAY, hourOfDay)
        dueDate.set(Calendar.MINUTE, minute)

        val formattedMinute = minuteWithLeadingZero(minute)
        val formattedHour = Dates().convert24To12Format(hourOfDay)
        view.setTimePickerText(formattedHour, formattedMinute, isAmOrPms(formattedHour))
        return dueDate
    }

    private fun isAmOrPms(formattedHour: Int): String {
        var isAmOrPm = HOUR_AM
        if (formattedHour > 12 || formattedHour == 0) {
            isAmOrPm = HOUR_PM
        }
        return isAmOrPm

    }

    private fun minuteWithLeadingZero(minute: Int): String {
        val formattedMinute = StringBuilder()
        formattedMinute.append(minute)
        if (minute == 0) {
            formattedMinute.append(0)
        }
        return formattedMinute.toString()

    }

    override fun saveGoal(text: String, currentTime: Long?, dueDate: Long) {
        if (!text.isEmpty()) {
            currentTime?.let { interactor.db.createRecord(text, it, dueDate) }
            view.displayListOfGoals()
        } else {
            view.displayEmptyTitleError()

        }
    }


}
