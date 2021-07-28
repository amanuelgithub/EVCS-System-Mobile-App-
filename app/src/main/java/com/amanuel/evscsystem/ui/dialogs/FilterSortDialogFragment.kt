package com.amanuel.evscsystem.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.databinding.FragmentDialogFilterSortBinding
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.MaterialDatePicker

/**
 * Created By: Amanuel Girma
 * Date:
 */

class FilterSortDialogFragment : DialogFragment(R.layout.fragment_dialog_filter_sort),
    CompoundButton.OnCheckedChangeListener {

    private lateinit var binding: FragmentDialogFilterSortBinding


    enum class Category {
        NOTIFICATION, VEHICLE, RECORD, ACCIDENT
    }

    private var orderBy = mutableMapOf("Ascending" to true, "Descending" to false)


    // stores the checkbox and its filtering text
    private lateinit var checkBoxFiltersHolder: MutableMap<MaterialCheckBox, String>


    // filters
    // consists of checkboxes that will dynamically be shown
    private lateinit var filterCheckBoxes: MutableList<MaterialCheckBox>

    private lateinit var filterChips: MutableList<Chip>

    // for notification = [created_at, vehicle_id, speed]
    private var notificationFilters = mutableListOf("created_at", "vehicle_id", "speed")

    // for vehicles = [plate_number(or vehicle_id), ]
    private var vehiclesFilters = mutableListOf("vehicle_id")

    // for record = [vehicle_id(or plate_number), created_at, status] (Note: most of this features are not included
    // in the functional require of our project)
    private var recordFilters = mutableListOf("created_at", "vehicle_id", "status")


    // initial category and filter
    private var category = Category.NOTIFICATION
    private var filters: MutableList<String> =
        mutableListOf(notificationFilters[1]) // init filter option is 'created_at'

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogFilterSortBinding.inflate(layoutInflater, container, false)

        // start date picker option
        binding.datePickerTextView.setOnClickListener {
            // Builds and shows the material date picker
            val materialDatePickerBuilder = MaterialDatePicker.Builder.datePicker()
            materialDatePickerBuilder.setTitleText("Select Date")

            val materialDatePicker = materialDatePickerBuilder.build()
            materialDatePicker.show(childFragmentManager, "DatePicker")
        }
        // end date picker option

        // a map to store the checkbox and filters dynamically
        checkBoxFiltersHolder = mutableMapOf()

        filterCheckBoxes = mutableListOf(
            binding.filterCheckBox1,
            binding.filterCheckBox2,
            binding.filterCheckBox3,
            binding.filterCheckBox4,
            binding.filterCheckBox5
        )
        filterChips = mutableListOf(
            binding.notificationCatgChip,
            binding.vehicleCatgChip,
            binding.recordsCatgChip,
            binding.accidentCatgChip
        )



        // close imageView on the toolbar
        binding.toolbarFilterSortInc.closeImageView.setOnClickListener {
            dismiss()
        }


        // Handles the click event for each of categories
        binding.apply {
            for (index in filterChips.indices) {
                if (filterChips[index].isChecked) {
                    when {
                        filterChips[index] == binding.notificationCatgChip -> {
                            setCategoryToNotification()
                            setupNotificationFilters()
                        }
                        filterChips[index] == binding.vehicleCatgChip -> {
                            setCategoryToVehicle()
                            setupVehicleFilters()
                        }
                        filterChips[index] == binding.recordsCatgChip -> {
                            setCategoryToRecord()
                            setupRecordFilters()
                        }
                        filterChips[index] == binding.accidentCatgChip -> {
                            setCategoryToAccident()
                            // todo: dynamically setup the filter option for accident
                        }
                        else -> {
                            // todo: handle this else statement
                        }

                    }
                } else {
                    // if none of the Category Chips are selected then
                    // setup Notification and created_at as the default
                    // Category and filtering option
                    when {
                        filterChips[index] == binding.notificationCatgChip -> {
                            setupDefaultCategoryAndFilters()
                        }
                        filterChips[index] == binding.vehicleCatgChip -> {
                            setupDefaultCategoryAndFilters()
                        }
                        filterChips[index] == binding.recordsCatgChip -> {
                            setupDefaultCategoryAndFilters()
                        }
                        filterChips[index] == binding.accidentCatgChip -> {
                            setupDefaultCategoryAndFilters()
                        }
                        else -> {
                            // todo: handle this else statement
                        }

                    }
                }
            }
        }

        binding.apply {
            notificationCatgChip.setOnClickListener {
                setCategoryToNotification()
                setupNotificationFilters()
            }
            vehicleCatgChip.setOnClickListener {
                setCategoryToVehicle()
                setupVehicleFilters()
            }
            recordsCatgChip.setOnClickListener {
                setCategoryToRecord()
                setupRecordFilters()
            }
            accidentCatgChip.setOnClickListener {
                setCategoryToAccident()
                // todo: dynamically setup the filter option for accident
            }
        }




        binding.clearFilterButton.setOnClickListener {
            clearFilters()
        }

        binding.applyFilterButton.setOnClickListener {
            applyFilters()
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            filterCheckBox1.setOnCheckedChangeListener(this@FilterSortDialogFragment)
            filterCheckBox2.setOnCheckedChangeListener(this@FilterSortDialogFragment)
            filterCheckBox3.setOnCheckedChangeListener(this@FilterSortDialogFragment)
            filterCheckBox4.setOnCheckedChangeListener(this@FilterSortDialogFragment)
            filterCheckBox5.setOnCheckedChangeListener(this@FilterSortDialogFragment)
        }
    }


    // The default category is Notification and the default filter for it is created_at(or date)
    private fun setupDefaultCategoryAndFilters() {
        uncheckAllCheckedBox()
        setCategoryToNotification()
        setupNotificationFilters()
    }

    private fun setupRecordFilters() {
        uncheckAllCheckedBox()
        hideAllMoreFilterViews()
        setAllCheckBoxInvisible()

        for (index in recordFilters.indices) {
            filterCheckBoxes[index].apply {
                visibility = View.VISIBLE
                text = recordFilters[index]
            }
        }


        // store the checkbox along with its filtering text if it is checked
        for (index in filterCheckBoxes.indices) {
            if (filterCheckBoxes[index].isChecked) {
                checkBoxFiltersHolder.put(filterCheckBoxes[index], recordFilters[index])
            }
        }

//        setupMoreRecordFilters()

    }

    private fun setupVehicleFilters() {
        uncheckAllCheckedBox()
        hideAllMoreFilterViews()
        setAllCheckBoxInvisible()
        for (index in vehiclesFilters.indices) {
            filterCheckBoxes[index].apply {
                visibility = View.VISIBLE
                text = vehiclesFilters[index]
            }
        }
    }

    private fun setupNotificationFilters() {
        uncheckAllCheckedBox()
        hideAllMoreFilterViews()
        setAllCheckBoxInvisible()
        for (index in notificationFilters.indices) {
            filterCheckBoxes[index].apply {
                visibility = View.VISIBLE
                text = notificationFilters[index]
            }
        }
    }

    // Checkboxes
    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when {
            buttonView?.id == binding.filterCheckBox1.id -> {
                if (isChecked) {
                    when {
                        category == Category.RECORD -> {
                            showMoreFilterViewForRecord()
                        }
                        category == Category.NOTIFICATION -> {
                            showMoreFilterViewForNotification()
                        }
                        category == Category.VEHICLE -> {
                            showMoreFilterViewForVehicle()
                        }
                        category == Category.ACCIDENT -> {

                        }
                    }
                } else {
                    when {
                        category == Category.RECORD -> {
                            hideMoreFilterViewForRecord()
                        }
                        category == Category.NOTIFICATION -> {
                            hideMoreFilterViewForNotification()
                        }
                        category == Category.VEHICLE -> {
                            hideMoreFilterViewForVehicle()
                        }
                        category == Category.ACCIDENT -> {

                        }
                    }

                    Toast.makeText(requireContext(), "no checked", Toast.LENGTH_SHORT).show()
                }
            }
            buttonView?.id == binding.filterCheckBox2.id -> {
                if (isChecked) {
                    when {
                        category == Category.RECORD -> {
                            showMoreFilterViewForRecord()
                        }
                        category == Category.NOTIFICATION -> {
                            showMoreFilterViewForNotification()
                        }
                        category == Category.VEHICLE -> {
                            showMoreFilterViewForVehicle()
                        }
                        category == Category.ACCIDENT -> {

                        }
                    }

                    Toast.makeText(
                        requireContext(),
                        "checkbox 2 and category: ${category}",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    when {
                        category == Category.RECORD -> {
                            hideMoreFilterViewForRecord()
                        }
                        category == Category.NOTIFICATION -> {
                            hideMoreFilterViewForNotification()
                        }
                        category == Category.VEHICLE -> {
                            hideMoreFilterViewForVehicle()
                        }
                        category == Category.ACCIDENT -> {

                        }
                    }
                    Toast.makeText(requireContext(), "no checked", Toast.LENGTH_SHORT).show()
                }
            }
            buttonView?.id == binding.filterCheckBox3.id -> {
                if (isChecked) {
                    when {
                        category == Category.RECORD -> {
                            showMoreFilterViewForRecord()
                        }
                        category == Category.NOTIFICATION -> {
                            showMoreFilterViewForNotification()
                        }
                        category == Category.VEHICLE -> {

                        }
                        category == Category.ACCIDENT -> {

                        }
                    }


                    Toast.makeText(
                        requireContext(),
                        "checkbox 3 and category: ${category}",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    when {
                        category == Category.RECORD -> {
                            hideMoreFilterViewForRecord()
                        }
                        category == Category.NOTIFICATION -> {
                            hideMoreFilterViewForNotification()
                        }
                        category == Category.VEHICLE -> {

                        }
                        category == Category.ACCIDENT -> {

                        }
                    }
                    Toast.makeText(requireContext(), "no checked", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }


    private fun hideMoreFilterViewForRecord() {
        binding.apply {
            for (index in filterCheckBoxes.indices) {
                if (!filterCheckBoxes[index].isChecked) {
                    when {
                        filterCheckBoxes[index] == filterCheckBox1 -> { // created_at -> so show day picker
                            datePickerTextView.visibility = View.GONE
                        }
                        filterCheckBoxes[index] == filterCheckBox2 -> {
                            vehiclePlateNoInputLayout.visibility = View.GONE
                        }
                        filterCheckBoxes[index] == filterCheckBox3 -> {
                            statusRadioGroup.visibility = View.GONE
                        }
                        filterCheckBoxes[index] == filterCheckBox4 -> {

                        }
                        filterCheckBoxes[index] == filterCheckBox5 -> {

                        }
                    }
                }
            }
        }
    }

    // private var recordFilters = mutableListOf("created_at", "vehicle_id", "status")
    private fun showMoreFilterViewForRecord() {
        binding.apply {
            for (index in filterCheckBoxes.indices) {
                if (filterCheckBoxes[index].isChecked) {
                    when {
                        filterCheckBoxes[index] == filterCheckBox1 -> { // created_at -> so show day picker
                            // show the more filter option view
                            datePickerTextView.visibility = View.VISIBLE
                        }
                        filterCheckBoxes[index] == filterCheckBox2 -> {
                            //
                            vehiclePlateNoInputLayout.visibility = View.VISIBLE
                        }
                        filterCheckBoxes[index] == filterCheckBox3 -> {
                            //
                            statusRadioGroup.visibility = View.VISIBLE
                        }
                        filterCheckBoxes[index] == filterCheckBox4 -> {

                        }
                        filterCheckBoxes[index] == filterCheckBox5 -> {

                        }
                    }
                }


            }
        }
    }


    private fun hideMoreFilterViewForNotification() {
        binding.apply {
            for (index in filterCheckBoxes.indices) {
                if (!filterCheckBoxes[index].isChecked) {
                    when {
                        filterCheckBoxes[index] == filterCheckBox1 -> { // created_at -> so show day picker
                            datePickerTextView.visibility = View.GONE
                        }
                        filterCheckBoxes[index] == filterCheckBox2 -> {
                            vehiclePlateNoInputLayout.visibility = View.GONE
                        }
                        filterCheckBoxes[index] == filterCheckBox3 -> {
                            speedRangeSlider.visibility = View.GONE
                        }
                        filterCheckBoxes[index] == filterCheckBox4 -> {

                        }
                        filterCheckBoxes[index] == filterCheckBox5 -> {

                        }
                    }
                }
            }
        }
    }


    //    private var notificationFilters = mutableListOf("created_at", "vehicle_id", "speed")
    private fun showMoreFilterViewForNotification() {
        binding.apply {
            for (index in filterCheckBoxes.indices) {
                if (filterCheckBoxes[index].isChecked) {
                    when {
                        filterCheckBoxes[index] == filterCheckBox1 -> { // created_at -> so show day picker
                            // show the more filter option view
                            datePickerTextView.visibility = View.VISIBLE
                        }
                        filterCheckBoxes[index] == filterCheckBox2 -> {
                            //
                            vehiclePlateNoInputLayout.visibility = View.VISIBLE
                        }
                        filterCheckBoxes[index] == filterCheckBox3 -> {
                            //
                            speedRangeSlider.visibility = View.VISIBLE
                        }
                        filterCheckBoxes[index] == filterCheckBox4 -> {

                        }
                        filterCheckBoxes[index] == filterCheckBox5 -> {

                        }
                    }
                }


            }
        }
    }

    private fun hideMoreFilterViewForVehicle() {
        binding.apply {
            for (index in filterCheckBoxes.indices) {
                if (!filterCheckBoxes[index].isChecked) {
                    when {
                        filterCheckBoxes[index] == filterCheckBox1 -> { // created_at -> so show day picker
                            vehiclePlateNoInputLayout1.visibility = View.GONE
                        }
                        filterCheckBoxes[index] == filterCheckBox2 -> {

                        }
                        filterCheckBoxes[index] == filterCheckBox3 -> {
                        }
                        filterCheckBoxes[index] == filterCheckBox4 -> {

                        }
                        filterCheckBoxes[index] == filterCheckBox5 -> {

                        }
                    }
                }
            }
        }
    }


    //    private var notificationFilters = mutableListOf("created_at", "vehicle_id", "speed")
    private fun showMoreFilterViewForVehicle() {
        binding.apply {
            for (index in filterCheckBoxes.indices) {
                if (filterCheckBoxes[index].isChecked) {
                    when {
                        filterCheckBoxes[index] == filterCheckBox1 -> { // created_at -> so show day picker
                            // show the more filter option view
                            vehiclePlateNoInputLayout1.visibility = View.VISIBLE
                        }
                        filterCheckBoxes[index] == filterCheckBox2 -> {
                        }
                        filterCheckBoxes[index] == filterCheckBox3 -> {
                        }
                        filterCheckBoxes[index] == filterCheckBox4 -> {

                        }
                        filterCheckBoxes[index] == filterCheckBox5 -> {

                        }
                    }
                }
            }
        }
    }


    private fun hideAllMoreFilterViews(){
        binding.apply {
            speedRangeSlider.visibility = View.GONE
            vehiclePlateNoInputLayout1.visibility = View.GONE
            vehiclePlateNoInputLayout.visibility = View.GONE
            statusRadioGroup.visibility = View.GONE
            datePickerTextView.visibility = View.GONE
        }
    }

    private fun uncheckAllCheckedBox() {
        binding.apply {
            filterCheckBox1.isChecked = false
            filterCheckBox2.isChecked = false
            filterCheckBox3.isChecked = false
            filterCheckBox4.isChecked = false
            filterCheckBox5.isChecked = false
        }
    }

    private fun setAllCheckBoxInvisible() {
        binding.apply {
            filterCheckBox1.visibility = View.GONE
            filterCheckBox2.visibility = View.GONE
            filterCheckBox3.visibility = View.GONE
            filterCheckBox4.visibility = View.GONE
            filterCheckBox5.visibility = View.GONE
        }
    }

    private fun setCategoryToAccident() {
        category = Category.ACCIDENT
    }

    private fun setCategoryToRecord() {
        category = Category.RECORD
    }

    private fun setCategoryToVehicle() {
        category = Category.VEHICLE
    }

    private fun setCategoryToNotification() {
        category = Category.NOTIFICATION
    }


    // first collect the category and the filters selected
    // second pass them back to the SearchFragment
    // finally dismiss the dialog
    private fun applyFilters() {
//        TODO("Not yet implemented")
        when (category) {
            Category.NOTIFICATION -> {
                for (index in notificationFilters.indices) {
                    if (filterCheckBoxes[index].isChecked) {
                        filters.clear()
                        filters.add(index, notificationFilters[index])
                    }
                }
            }
            Category.VEHICLE -> {
                for (index in vehiclesFilters.indices) {
                    if (filterCheckBoxes[index].isChecked) {
                        filters.clear()
                        filters.add(index, vehiclesFilters[index])
                    }
                }
            }
            Category.RECORD -> {
                for (index in recordFilters.indices) {
                    if (filterCheckBoxes[index].isChecked) {
                        filters.clear()
                        filters.add(index, recordFilters[index])
                    }
                }
            }
            Category.ACCIDENT -> {
                return
                // todo: not yet implemented
            }
        }


        Toast.makeText(
            requireActivity(),
            "category: $category and filters: $filters",
            Toast.LENGTH_SHORT
        ).show()

        // now we have both category and filters
        // todo: send them back

        dismiss()

    }

    /**
     * Clearing Filters is similar to setting up the default category and filter
     * and sending them to back to the SearchFragment
     */
    private fun clearFilters() {
        setupDefaultCategoryAndFilters()
        // todo: send them back
        dismiss()
    }

    companion object {
        const val TAG = "FragmentDialogFilterSortBinding"
    }


}