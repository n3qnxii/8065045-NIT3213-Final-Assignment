package com.example.nit3213finalproject.ui.details

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.nit3213finalproject.databinding.ActivityDetailsBinding
import com.squareup.moshi.Moshi

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding:
            ActivityDetailsBinding

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityDetailsBinding.inflate(
                layoutInflater
            )

        setContentView(binding.root)

        val entityJson =
            intent.getStringExtra(
                EXTRA_ENTITY_JSON
            )

        if (entityJson.isNullOrBlank()) {

            showError(
                "Entity information was not received."
            )

            return
        }

        try {

            val moshi =
                Moshi.Builder()
                    .build()

            val adapter =
                moshi.adapter(
                    Map::class.java
                )

            val entity =
                adapter.fromJson(entityJson)

            if (entity == null) {

                showError(
                    "Unable to read entity information."
                )

                return
            }

            displayEntity(entity)

        } catch (e: Exception) {

            showError(
                "Unable to display entity information."
            )
        }
    }

    private fun displayEntity(
        entity: Map<*, *>
    ) {
        binding.btnBackDashboard.setOnClickListener {
            finish()
        }
        binding.detailsContainer
            .removeAllViews()

        // Display all fields, including the description
        entity.forEach { entry ->

            val key =
                entry.key
                    ?.toString()
                    ?: return@forEach

            val value =
                entry.value
                    ?.toString()
                    ?: ""

            val titleTextView =
                TextView(this).apply {

                    text =
                        formatKey(key)

                    textSize = 15f

                    setTypeface(
                        typeface,
                        Typeface.BOLD
                    )

                    setTextColor(
                        android.graphics.Color.parseColor(
                            "#33333D"
                        )
                    )

                    setPadding(
                        0,
                        14,
                        0,
                        3
                    )
                }

            val valueTextView =
                TextView(this).apply {

                    text = value

                    textSize = 15f

                    setTextColor(
                        android.graphics.Color.parseColor(
                            "#535461"
                        )
                    )

                    setPadding(
                        0,
                        0,
                        0,
                        8
                    )
                }

            binding.detailsContainer
                .addView(titleTextView)

            binding.detailsContainer
                .addView(valueTextView)
        }
    }

    private fun formatKey(
        key: String
    ): String {

        return key
            .replace("_", " ")
            .replaceFirstChar {

                if (it.isLowerCase()) {
                    it.titlecase()
                } else {
                    it.toString()
                }
            }
    }

    private fun showError(
        message: String
    ) {

        binding.tvError.text =
            message

        binding.tvError.visibility =
            View.VISIBLE
    }

    companion object {

        const val EXTRA_ENTITY_JSON =
            "extra_entity_json"
    }
}