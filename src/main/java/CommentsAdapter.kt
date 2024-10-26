import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.amazon.Comment
import com.example.amazon.R

class CommentsAdapter(private val commentsList: List<Comment>) :
    RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {

    // ViewHolder class to hold the views for each comment item
    class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val commenterNameTextView: TextView = view.findViewById(R.id.commenterNameTextView)
        val commentTextView: TextView = view.findViewById(R.id.commentTextView)
        val commentDateTextView: TextView = view.findViewById(R.id.commentDateTextView)
    }

    // Inflate the item_comment.xml layout and return the ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    // Bind the Comment data to the views
    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = commentsList[position]
        holder.commenterNameTextView.text = comment.commenterName
        holder.commentTextView.text = comment.commentText
        holder.commentDateTextView.text = comment.commentDate
    }

    // Return the total number of items
    override fun getItemCount(): Int {
        return commentsList.size
    }
}