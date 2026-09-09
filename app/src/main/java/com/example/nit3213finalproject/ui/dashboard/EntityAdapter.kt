package com.example.nit3213finalproject.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nit3213finalproject.databinding.ItemEntityBinding

class EntityAdapter(
    private val onItemClick: (Map<String, Any>) -> Unit
) : RecyclerView.Adapter<EntityAdapter.EntityViewHolder>() {

    private var entities: List<Map<String, Any>> =
        emptyList()

    fun submitList(
        newEntities: List<Map<String, Any>>
    ) {
        entities = newEntities
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EntityViewHolder {

        val binding =
            ItemEntityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return EntityViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: EntityViewHolder,
        position: Int
    ) {
        holder.bind(entities[position])
    }

    override fun getItemCount(): Int {
        return entities.size
    }

    inner class EntityViewHolder(
        private val binding: ItemEntityBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            entity: Map<String, Any>
        ) {

            // Dashboard not show description
            val summaryEntries =
                entity.filterKeys {
                    !it.equals(
                        "description",
                        ignoreCase = true
                    )
                }

            val firstEntry =
                summaryEntries.entries
                    .firstOrNull()

            binding.tvEntityTitle.text =
                firstEntry?.value?.toString()
                    ?: "Entity"

            val summary =
                summaryEntries.entries
                    .drop(1)
                    .joinToString("\n") { entry ->

                        "${formatKey(entry.key)}: ${entry.value}"
                    }

            binding.tvEntitySummary.text =
                if (summary.isBlank()) {
                    "Tap to view details"
                } else {
                    summary
                }

            binding.root.setOnClickListener {
                onItemClick(entity)
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
    }
}