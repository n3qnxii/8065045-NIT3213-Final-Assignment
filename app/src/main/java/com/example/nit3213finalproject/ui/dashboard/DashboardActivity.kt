package com.example.nit3213finalproject.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nit3213finalproject.databinding.ActivityDashboardBinding
import com.example.nit3213finalproject.ui.details.DetailsActivity
import com.squareup.moshi.Moshi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding:
            ActivityDashboardBinding

    private val viewModel:
            DashboardViewModel by viewModels()

    private lateinit var entityAdapter:
            EntityAdapter

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityDashboardBinding.inflate(
                layoutInflater
            )

        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()

        // get keypass from Login
        val keypass =
            intent.getStringExtra(EXTRA_KEYPASS)

        if (keypass.isNullOrBlank()) {

            binding.tvError.text =
                "Keypass was not received."

            binding.tvError.visibility =
                View.VISIBLE

        } else {

            // if API Login become travel
            // here became loadDashboard("travel") automatic
            viewModel.loadDashboard(keypass)
        }
    }

    private fun setupRecyclerView() {

        entityAdapter =
            EntityAdapter { entity ->

                val moshi =
                    Moshi.Builder()
                        .build()

                val adapter =
                    moshi.adapter(
                        Map::class.java
                    )

                val entityJson =
                    adapter.toJson(entity)

                val intent =
                    Intent(
                        this,
                        DetailsActivity::class.java
                    )

                intent.putExtra(
                    DetailsActivity.EXTRA_ENTITY_JSON,
                    entityJson
                )

                startActivity(intent)
            }

        binding.recyclerView.apply {

            layoutManager =
                LinearLayoutManager(
                    this@DashboardActivity
                )

            adapter = entityAdapter
        }
    }

    private fun observeViewModel() {

        viewModel.entities.observe(this) {
                entities ->

            entityAdapter.submitList(
                entities
            )
        }

        viewModel.entityTotal.observe(this) {
                total ->

            binding.tvTotal.text =
                "Total entities: $total"
        }

        viewModel.loading.observe(this) {
                isLoading ->

            binding.progressBar.visibility =
                if (isLoading) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }

        viewModel.error.observe(this) {
                errorMessage ->

            if (errorMessage.isNullOrBlank()) {

                binding.tvError.visibility =
                    View.GONE

            } else {

                binding.tvError.text =
                    errorMessage

                binding.tvError.visibility =
                    View.VISIBLE
            }
        }
    }

    companion object {

        const val EXTRA_KEYPASS =
            "extra_keypass"
    }
}