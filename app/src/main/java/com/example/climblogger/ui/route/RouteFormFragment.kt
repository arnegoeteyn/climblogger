package com.example.climblogger.ui.route

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.data.Route
import com.example.climblogger.data.Sector
import com.example.climblogger.util.ItemSpinner
import com.example.climblogger.util.afterTextChanged
import com.example.climblogger.util.setTextIfNotFocused
import kotlinx.android.synthetic.main.activity_ascent.*
import kotlinx.android.synthetic.main.fragment_route_form.*

private const val ARG_PARAM_ROUTE_ID = "route_ID_PARAM"
private const val ARG_PARAM_SECTOR_ID = "sector_id_param"

class RouteFormFragment : Fragment() {
    private var routeId: String = ""
//    private var sectorId: String? = null

    private var listener: OnFragmentInteractionListener? = null


    private lateinit var spinner: ItemSpinner<Sector> // pass generic type here for easier
    private lateinit var kindSpinner: ItemSpinner<CharSequence>
    private lateinit var addRouteViewModel: ModifyRouteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addRouteViewModel = ViewModelProviders.of(this).get(ModifyRouteViewModel::class.java)

        // get the routeId
        arguments?.let { bundle ->
            bundle.getString(ARG_PARAM_ROUTE_ID)?.let {
                routeId = it
            }

            bundle.getString(ARG_PARAM_SECTOR_ID)?.let {
                addRouteViewModel.sectorId = it
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_route_form, container, false)
        this.spinner = view.findViewById(R.id.sectorSpinner)
        this.kindSpinner = view.findViewById(R.id.kindSpinner)
        return view
    }

    /**
     * Load the form with data that is already in the route
     * Will only do something if the route is already in the db
     */
    private fun loadForm() {
        // if route already exists load it
        addRouteViewModel.getRoute(routeId)?.observe(this, Observer { route ->
            route?.let {

                addRouteViewModel.routeName ?: run {
                    nameTextInput.editText?.setText(it.name)
                }
                addRouteViewModel.routeGrade ?: run {
                    gradeTextInput.editText?.setText(it.grade)
                }
                addRouteViewModel.routeComment ?: run {
                    commentTextInput.editText?.setText(it.comment)
                }
                addRouteViewModel.routeLink ?: run {
                    linkTextInput.editText?.setText(it.link)
                }

                addRouteViewModel.sectorId ?: run {
                    addRouteViewModel.sectorId = it.sector_id
                }
                addRouteViewModel.routeKind ?: run {
                    addRouteViewModel.routeKind = it.kind
                }
            }

            addRouteViewModel.routeName?.let { routeName ->
                nameTextInput.editText?.setText(routeName)
            }
            addRouteViewModel.routeGrade?.let { routeGrade ->
                gradeTextInput.editText?.setText(routeGrade)
            }
            addRouteViewModel.routeComment?.let { routeComment ->
                commentTextInput.editText?.setText(routeComment)
            }
            addRouteViewModel.routeLink?.let { routeLink ->
                linkTextInput.editText?.setText(routeLink)
            }

            // load the spinners and update them with the already selected info
            initSectorSpinner(addRouteViewModel.sectorId)
            initKindSpinner(addRouteViewModel.routeKind)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadForm()

        setupViewListeners()
    }

    private fun setupViewListeners() {
        nameTextInput.editText!!.afterTextChanged {
            addRouteViewModel.routeName = it
        }
        gradeTextInput.editText!!.afterTextChanged {
            addRouteViewModel.routeGrade = it
        }
        commentTextInput.editText!!.afterTextChanged {
            addRouteViewModel.routeComment = it
        }
        linkTextInput.editText!!.afterTextChanged {
            addRouteViewModel.routeLink = it
        }

        sectorSpinner.onItemChosen {
            addRouteViewModel.sectorId = (sectorSpinner.selectedItem as Sector).sectorId
        }
        kindSpinner.onItemChosen {
            addRouteViewModel.routeKind = kindSpinner.selectedItem.toString()
        }
    }


    fun createRoute(): Route {
        val commentText = commentTextInput.editText!!.text.toString()
        val linkText = linkTextInput.editText!!.text.toString()

        Log.d("debug", nameTextInput.editText!!.text.toString())

        return Route(
            (sectorSpinner.selectedItem as Sector).sectorId,
            nameTextInput.editText!!.text.toString(),
            gradeTextInput.editText!!.text.toString(),
            kindSpinner.selectedItem as String,
            if (commentText.isEmpty()) null else commentText,
            if (linkText.isEmpty()) null else linkText,
            null, null,
            routeId
        )
    }

    private fun initKindSpinner(kind: String?) {
        kindSpinner.setData(resources.getStringArray(R.array.route_kind).toList())
        kind?.let {
            kindSpinner.selectItemInSpinner(it)
        }
    }


    private fun initSectorSpinner(selectedSector_id: String?) {
        addRouteViewModel.allSectors.observe(this, androidx.lifecycle.Observer { sectors ->
            this.spinner.setData(sectors)

            selectedSector_id?.let {
                addRouteViewModel.getSector(it).observe(this, Observer { sector: Sector? ->
                    spinner.selectItemInSpinner(sector)
                })
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener

    companion object {
        @JvmStatic
        fun newInstance(route_id: String, sector_id: String? = null) =
            RouteFormFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM_ROUTE_ID, route_id)
                    putString(ARG_PARAM_SECTOR_ID, sector_id)
                }
            }

        val TAG = this::class.qualifiedName!!
    }
}
