package com.example.climblogger.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.climblogger.R
import com.example.climblogger.data.Route
import com.example.climblogger.ui.route.RouteActivity

class MainActivity : AppCompatActivity(),
    NoFileLoadedFragment.OnFragmentInteractionListener,
    RoutesFragment.OnFragmentInteractionListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUESTCODE_FILEPICKER && resultCode == RESULT_OK) {
            val selectedFile = data?.data //The uri with the location of the file
            Log.i(TAG, "selected the file $selectedFile")
        }
    }

    /**
     * What to do when the button is pressed when no file is selected
     */
    override fun onFragmentInteraction() {
        val intent = Intent()
            .setType("*/*")
            .setAction(Intent.ACTION_GET_CONTENT)

        startActivityForResult(
            Intent.createChooser(intent, "Select a file"),
            REQUESTCODE_FILEPICKER
        )
    }

    /**
     * Handle what happens when a route gets selected
     */
    override fun onRouteClicked(route: Route) {
        val intent: Intent = Intent(this, RouteActivity::class.java)
        intent.putExtra(EXTRA_ROUTE, route)
        startActivity(intent)
    }

    companion object {
        public const val EXTRA_ROUTE = "EXTRA_ROUTE"
        private val TAG = MainActivity::class.qualifiedName
        private const val REQUESTCODE_FILEPICKER = 111

    }
}
