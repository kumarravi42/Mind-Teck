package com.example.mindtech

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.mindtech.data.model.ListItem
import com.example.mindtech.databinding.ActivityMainBinding
import com.example.mindtech.ui.adapter.ItemAdapter
import com.example.mindtech.ui.adapter.ViewPagerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var bin: ActivityMainBinding
    private lateinit var viewPager: ViewPager
    private lateinit var itemRecyclerView: RecyclerView
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private lateinit var fab: com.google.android.material.floatingactionbutton.FloatingActionButton

    private lateinit var viewPagerAdapter: PagerAdapter
    private lateinit var itemAdapter: ItemAdapter
    private var list = mutableListOf<ListItem>()
    private lateinit var indicatorLayout: LinearLayout
    private lateinit var dots: Array<ImageView?>

    private val images = listOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3,
        R.drawable.image4,
        R.drawable.image5,
        R.drawable.image6
    )
    private val items = mutableListOf("apple", "banana", "orange", "blueberry")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bin.root)

        viewPager = findViewById(R.id.viewPager)
        indicatorLayout = findViewById(R.id.indicatorLayout)
        itemRecyclerView = findViewById(R.id.itemRecyclerView)
        searchView = findViewById(R.id.searchView)
        fab = findViewById(R.id.fab)

        customizeSearchView()
        setupViewPager()
        setupList()
        setupSearch()
        setupFab()
    }


    private fun setupViewPager() {
        viewPagerAdapter = ViewPagerAdapter(images)
        viewPager.adapter = viewPagerAdapter


        setupIndicators(images.size)
        setCurrentIndicator(0)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                setCurrentIndicator(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun setupIndicators(count: Int) {
        dots = arrayOfNulls(count)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            marginStart = 8
            marginEnd = 8
        }

        for (i in 0 until count) {
            dots[i] = ImageView(this).apply {
                setImageResource(R.drawable.indicator_inactive)
                layoutParams = params
            }
            indicatorLayout.addView(dots[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        for (i in dots.indices) {
            dots[i]?.setImageResource(
                if (i == index) R.drawable.indicator_active else R.drawable.indicator_inactive
            )
        }
    }


    private fun setupList() {
        list = mutableListOf(
            ListItem(R.drawable.image1, "Flower", "This is a red flower"),
            ListItem(R.drawable.image2, "Sun", "This is a Sun"),
            ListItem(R.drawable.image3, "River", "This is a River"),
            ListItem(R.drawable.image4, "Tree", "This is a tree"),
            ListItem(R.drawable.image5, "White Flower", "This is a White Flower"),
            ListItem(R.drawable.image6, "SunRise", "This is a SunRise"),
            ListItem(R.drawable.image1, "Flower", "This is a red flower"),
            ListItem(R.drawable.image2, "Sun", "This is a Sun"),
            ListItem(R.drawable.image3, "River", "This is a River"),
            ListItem(R.drawable.image4, "Tree", "This is a tree"),
            ListItem(R.drawable.image5, "White Flower", "This is a White Flower"),
            ListItem(R.drawable.image6, "SunRise", "This is a SunRise"),
            ListItem(R.drawable.image1, "Flower", "This is a red flower"),
            ListItem(R.drawable.image2, "Sun", "This is a Sun"),
            ListItem(R.drawable.image3, "River", "This is a River"),
            ListItem(R.drawable.image4, "Tree", "This is a tree"),
            ListItem(R.drawable.image5, "White Flower", "This is a White Flower"),
            ListItem(R.drawable.image6, "SunRise", "This is a SunRise"),
            ListItem(R.drawable.image1, "Flower", "This is a red flower"),
            ListItem(R.drawable.image2, "Sun", "This is a Sun"),
            ListItem(R.drawable.image3, "River", "This is a River"),
            ListItem(R.drawable.image4, "Tree", "This is a tree"),
            ListItem(R.drawable.image5, "White Flower", "This is a White Flower"),
            ListItem(R.drawable.image6, "SunRise", "This is a SunRise"),
            ListItem(R.drawable.image1, "Flower", "This is a red flower"),

            )

        bin.itemRecyclerView.layoutManager = LinearLayoutManager(this)
        itemAdapter = ItemAdapter(list)
        bin.itemRecyclerView.adapter = itemAdapter
    }

    private fun setupSearch() {
        // Set up the SearchView
        bin.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Filter the adapter when query is submitted
                query?.let { itemAdapter.filter.filter(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filter the adapter as the user types
                newText?.let { itemAdapter.filter.filter(it) }
                return false
            }
        })
        bin.itemRecyclerView.adapter = itemAdapter
    }

    private fun customizeSearchView() {
        val searchTextView =
            bin.searchView.findViewById<TextView>(androidx.appcompat.R.id.search_src_text)

        // Set text color and hint color
        searchTextView.setTextColor(resources.getColor(R.color.white)) // Set text color to white
        searchTextView.setHintTextColor(resources.getColor(R.color.black)) // Set hint color to light gray

        // Set hint programmatically if needed
        bin.searchView.queryHint = "Search items"
    }

    private fun setupFab() {
        bin.fab.setOnClickListener {
            showStatisticsBottomSheet()
        }
    }

    @SuppressLint("InflateParams", "SetTextI18n")
    private fun showStatisticsBottomSheet() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_statistics, null)
        dialog.setContentView(view)

        val totalItems = list.size
        val charFrequency = items.joinToString("").groupingBy { it }.eachCount()
        val topChars = charFrequency.entries.sortedByDescending { it.value }.take(3)

        view.findViewById<TextView>(R.id.tvTotalItems).text = "Total Items: $totalItems"
        view.findViewById<TextView>(R.id.tvTopChars).text =
            topChars.joinToString("\n") { "${it.key} = ${it.value}" }

        dialog.show()
    }
}
