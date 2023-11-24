import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.corne.rainfall.R
import com.corne.rainfall.ui.home.LocationItem

class LocationListItemAdapter(private val context: Context, private var items: List<String>) : BaseAdapter() {

    override fun getCount(): Int {
        return items.size
    }

    fun setItems(items: List<String>) {
        this.items = items
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    
    fun getPosition(item: String): Int {
        return items.indexOf(item)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.fragment_location_list_item, parent, false)
        val locationName: TextView = view.findViewById(R.id.location_name)
        locationName.text = items[position]
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getView(position, convertView, parent)
    }
}
