import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.notbored.AdapterActivities
import com.example.notbored.databinding.ItemActivityBinding

class ActivityViewHolder(view: View , listener: AdapterActivities.onItemClickListener):RecyclerView.ViewHolder(view) {

    private val binding = ItemActivityBinding.bind(view)

    fun bind(activityPosition: String) {
        binding.nameActivity.setText(activityPosition)


    }

    init {

        view.setOnClickListener {


            listener.onItemClick(adapterPosition)
        }

    }

}



