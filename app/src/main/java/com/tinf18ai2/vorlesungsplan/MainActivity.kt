package com.tinf18ai2.vorlesungsplan

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Logger
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    var log: Logger = Logger.getGlobal()
    private lateinit var adapter: VorlesungsplanAdapter

    var woche: List<Vorlesungstag> = ArrayList<Vorlesungstag>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val linearLayoutManager = LinearLayoutManager(this)
        mainRecyclerView.layoutManager = linearLayoutManager
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            estimateTimeLeft().execute()
        }
        LoadData().execute()
    }

    override fun onResume() {
        super.onResume()
        LoadData().execute()
    }

    fun showPlan(week: List<Vorlesungstag>) {
        val allItems: ArrayList<VorlesungsplanItem> = ArrayList()

        for (day in week) {
            allItems.add(
                VorlesungsplanItem(
                    day.tag,
                    "",
                    "",
                    SimpleDateFormat("dd.MM").parse("00.00"),
                    SimpleDateFormat("dd.MM").parse("00.00")
                )
            )
            for (item in day.items) {
                allItems.add(item)
            }
        }
        log.info("wek: " + week.toString())
        log.info("allItems: " + allItems.toString())
        adapter = VorlesungsplanAdapter(items = allItems, context = applicationContext)
        mainRecyclerView.adapter = adapter
    }

    fun showTimeLeft(time: String) {
        Snackbar.make(mainView, time, Snackbar.LENGTH_LONG)
            .show()
    }

    private inner class LoadData : AsyncTask<Void, Void, List<Vorlesungstag>>() {
        override fun doInBackground(vararg p0: Void?): List<Vorlesungstag> {
            return AsyncPlanAnalyser().analyse()
        }

        override fun onPostExecute(result: List<Vorlesungstag>?) {
            super.onPostExecute(result)
            if (result != null) {
                woche = result
                showPlan(result)
            }
        }
    }

    private inner class estimateTimeLeft : AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg p0: Void?): String {
            return TimeUntilUniEnd().getTimeLeft(woche)
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result != null) {
                showTimeLeft(result)
            }
        }
    }
}