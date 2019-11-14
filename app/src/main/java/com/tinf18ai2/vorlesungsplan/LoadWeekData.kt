package com.tinf18ai2.vorlesungsplan

import android.os.AsyncTask

class LoadData(var weekDataCallback: WeekDataCallback) :
    AsyncTask<Void, Void, List<Vorlesungstag>>() {
    override fun doInBackground(vararg p0: Void?): List<Vorlesungstag> {
        return AsyncPlanAnalyser().analyse()
    }

    override fun onPostExecute(result: List<Vorlesungstag>?) {
        super.onPostExecute(result)
        if (result != null) {
            weekDataCallback.onDataRecieved(result)
        }
    }
}

interface WeekDataCallback {
    fun onDataRecieved(list: List<Vorlesungstag>)
}